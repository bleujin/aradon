package net.ion.bleujin.asyncrestlet;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Writer;

import net.ion.framework.util.RandomUtil;

public class AsyncInputStream extends FilterInputStream implements Runnable {
	private Thread runner; // Async Reader Thread
	private byte result[]; // Buffer
	private int reslen; // Buffer Length
	private boolean EOF; // End-of-File Indicator
	private IOException IOError; // IOExceptions

	BusyFlag lock; // Data Lock
	CondVar empty, full; // Signal Variables

	protected AsyncInputStream(InputStream in, int bufsize) {
		super(in);

		lock = new BusyFlag(); // Allocate sync variables
		empty = new CondVar(lock);
		full = new CondVar(lock);

		result = new byte[bufsize]; // Allocate Storage Area
		reslen = 0; // and initialize variables
		EOF = false;
		IOError = null;
		runner = new Thread(this); // Start Reader Thread
		runner.start();
	}

	protected AsyncInputStream(InputStream in) {
		this(in, 1024);
	}

	public int read() throws IOException {
		try {
			lock.getBusyFlag();
			while (reslen == 0) {
				try {
					if (EOF)
						return (-1);
					if (IOError != null)
						throw IOError;
					empty.cvWait();
				} catch (InterruptedException e) {
				}
			}
			return (int) getChar();
		} finally {
			lock.freeBusyFlag();
		}
	}

	public int read(byte b[]) throws IOException {
		return read(b, 0, b.length);
	}

	public int read(byte b[], int off, int len) throws IOException {
		try {
			lock.getBusyFlag();
			while (reslen == 0) {
				try {
					if (EOF)
						return (-1);
					if (IOError != null)
						throw IOError;
					empty.cvWait();
				} catch (InterruptedException e) {
				}
			}

			int sizeread = Math.min(reslen, len);
			byte c[] = getChars(sizeread);
			System.arraycopy(c, 0, b, off, sizeread);
			return (sizeread);
		} finally {
			lock.freeBusyFlag();
		}
	}

	public long skip(long n) throws IOException {
		try {
			lock.getBusyFlag();
			int sizeskip = Math.min(reslen, (int) n);
			if (sizeskip > 0) {
				byte c[] = getChars(sizeskip);
			}
			return ((long) sizeskip);
		} finally {
			lock.freeBusyFlag();
		}
	}

	public int available() throws IOException {
		return reslen;
	}

	public void close() throws IOException {
		try {
			lock.getBusyFlag();
			reslen = 0; // Clear Buffer
			EOF = true; // Mark End Of File
			empty.cvBroadcast(); // Alert all Threads
			full.cvBroadcast();
		} finally {
			lock.freeBusyFlag();
		}
	}

	public void mark(int readlimit) {
	}

	public void reset() throws IOException {
	}

	public boolean markSupported() {
		return false;
	}

	public void run() {
		try {
			while (true) {
				int c = in.read();
				try {
					lock.getBusyFlag();
					if ((c == -1) || (EOF)) {
						EOF = true; // Mark End Of File
						in.close(); // Close Input Source
						return; // End IO Thread
					} else {
						putChar((byte) c); // Store the byte read
					}
					if (EOF) {
						in.close(); // Close Input Source
						return; // End IO Thread
					}
				} finally {
					lock.freeBusyFlag();
				}
			}

		} catch (IOException e) {
			IOError = e; // Store Exception
			return;
		} finally {
			try {
				lock.getBusyFlag();
				empty.cvBroadcast(); // Alert all Threads
			} finally {
				lock.freeBusyFlag();
			}
		}
	}

	private void putChar(byte c) {
		try {
			lock.getBusyFlag();
			while ((reslen == result.length) && (!EOF)) {
				try {
					full.cvWait();
				} catch (InterruptedException ie) {
				}
			}
			if (!EOF) {
				result[reslen++] = c;
				empty.cvSignal();
			}
		} finally {
			lock.freeBusyFlag();
		}
	}

	private byte getChar() {
		try {
			lock.getBusyFlag();
			byte c = result[0];
			System.arraycopy(result, 1, result, 0, --reslen);
			full.cvSignal();
			return c;
		} finally {
			lock.freeBusyFlag();
		}
	}

	private byte[] getChars(int chars) {
		try {
			lock.getBusyFlag();
			byte c[] = new byte[chars];
			System.arraycopy(result, 0, c, 0, chars);
			reslen -= chars;
			System.arraycopy(result, chars, result, 0, reslen);
			full.cvSignal();
			return c;
		} finally {
			lock.freeBusyFlag();
		}
	}
}

class MyThread extends Thread {

	private PipedOutputStream output;

	MyThread() {
		this.output = new PipedOutputStream();
	}

	public void run() {
		try {
			Writer writer = new OutputStreamWriter(output);
			for (int i = 0; i < 40; i++) {
				writer.write(RandomUtil.nextRandomString(150));
				writer.write("<br />");
				writer.flush();
				Thread.sleep(100);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public InputStream getInputStream() throws IOException {
		PipedInputStream pi = new PipedInputStream(output);
		return pi;
	}

}

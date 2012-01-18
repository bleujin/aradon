package net.ion.radon.core.service;

import groovy.lang.Binding;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import jline.Terminal;
import net.ion.framework.util.Debug;

import org.codehaus.groovy.tools.shell.Groovysh;
import org.codehaus.groovy.tools.shell.IO;

/**
 * 
 * @author Bruce Fancher
 * 
 */
public class GroovyShellThread extends Thread {
	public static final String OUT_KEY = "out:";

	private Socket socket;
	private Binding binding;

	public GroovyShellThread(Socket socket, Binding binding) {
		super();
		this.socket = socket;
		this.binding = binding;
	}

	@Override
	public void run() {
		IO io = null;
		try {

			PrintStream out = new PrintStream(socket.getOutputStream());
			InputStream in = socket.getInputStream();
			// binding.setVariable(OUT_KEY, out);
			io = new IO(in, wrap(out), wrap(out));

			final Terminal terminal = Terminal.getTerminal();
			Debug.debug(terminal.isEchoEnabled()) ;
			terminal.disableEcho() ;
			terminal.initializeTerminal() ;
			
			Groovysh shell = new Groovysh(getClass().getClassLoader(), binding, io);

			// InteractiveShell shell = new InteractiveShell(binding, in, out, out);
			try {
				shell.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (io != null)
					io.close();
			} catch (IOException ignore) {
			}
			try {
				socket.close();
			} catch (IOException ignore) {
			}
		}

	}

	private OutputStream wrap(OutputStream out) {
		FilterOutputStream filter = new FilterOutputStream(out) {
			@Override
			public void write(int b) throws IOException {
				super.write(b);
				// System.out.write((char)b) ;
				if (b == 0x0A){ // if we spot a CR, make sure to add a LF
					super.write(0x0D);
				}
			}
		};
		return filter;
	}


	public Socket getSocket() {
		return socket;
	}
}

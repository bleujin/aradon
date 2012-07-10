package net.ion.bleujin.study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class StreamGobbler extends Thread {
	InputStream is;
	String type;

	StreamGobbler(InputStream is, String type) {
		this.is = is;
		this.type = type;
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null)
				System.out.println(type + ">" + line);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}

public class GoodWindowExec {
	public static void main(String args[]) {
		if (args.length < 1) {
			System.out.println("USAGE: java GoodWindowsExec <cmd>");
		}

		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("./mongo/win32-x86_64/bin/mongod --rest");
			// any error message?
			StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "mongo error");

			// any output?
			StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "mongo");

			// kick them off
			errorGobbler.start();
			outputGobbler.start();

			// any error???
			int exitVal = proc.waitFor();
			System.out.println("ExitValue: " + exitVal);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}

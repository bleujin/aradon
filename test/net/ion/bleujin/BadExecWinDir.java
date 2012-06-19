package net.ion.bleujin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BadExecWinDir {
	public static void main(String args[]) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("dir");
			InputStream stdin = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(stdin);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<OUTPUT>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("</OUTPUT>");
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}

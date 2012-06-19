package net.ion.bleujin.asyncrestlet;

import java.io.IOException;

import net.ion.framework.util.Debug;

import org.restlet.representation.AppendableRepresentation;

public class AsyncThread extends Thread {

	private AppendableRepresentation r;

	public AsyncThread(AppendableRepresentation r) {
		this.r = r;
	}

	public void run() {
		try {
			for (int i = 0; i < 20; i++) {
				r.append(i + ".\n");
				Thread.sleep(500) ;
				Debug.debug(r) ;
			}
		} catch (InterruptedException e) {
		} catch (IOException ignore) {
			ignore.printStackTrace();
		}
	}

}

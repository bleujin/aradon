package net.ion.radon.client;

import junit.framework.TestCase;

import org.restlet.representation.Representation;

public class TestSpeed extends TestCase {

	String target = "http://61.250.201.157:9002/";

	public void setUp() throws Exception {
		super.setUp();
	}


	public void testCase2() throws Exception {

		final int clientCount = 100;
		final int loopCount = 5;

		ClientThread[] ct = new ClientThread[clientCount];
		for (int i = 0; i < clientCount; i++) {
			ct[i] = new ClientThread(loopCount, "/");
		}

		for (int i = 0; i < clientCount; i++) {
			ct[i].start();
		}

		for (int i = 0; i < clientCount; i++) {
			ct[i].join();
		}
	}
	

	public void testCase3() throws Exception {

		final int clientCount = 5;
		final int loopCount = 100;

		ClientThread[] ct = new ClientThread[clientCount];
		for (int i = 0; i < clientCount; i++) {
			ct[i] = new ClientThread(loopCount, "/");
		}

		for (int i = 0; i < clientCount; i++) {
			ct[i].start();
		}

		for (int i = 0; i < clientCount; i++) {
			ct[i].join();
		}
	}
}

class ClientThread extends Thread {
	private AradonClient client;
	private int loopCount;
	private String path;

	ClientThread(int loopCount, String path) {
		this.client = AradonClientFactory.create("http://61.250.201.157:9002");
		this.loopCount = loopCount;
		this.path = path;
	}

	public void run() {
		try {
			for (int i = 0; i < loopCount; i++) {
				Representation response = client.createRequest(path).get();
			}
			System.out.print(loopCount + " END");
			client.stop();
		} catch (Exception ignore) {
			ignore.printStackTrace();
		}
	}

}

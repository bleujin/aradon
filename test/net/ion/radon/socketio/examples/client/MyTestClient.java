package net.ion.radon.socketio.examples.client;

import net.ion.radon.socketio.client.common.SocketIOConnection;
import net.ion.radon.socketio.client.jre.SocketIOConnectionXHRBase;
import net.ion.radon.socketio.common.DisconnectReason;

public class MyTestClient {
	public static synchronized void print(String str) {
		System.out.println(str);
		System.out.flush();
	}

	// * websocket - Web Sockets
	// * flashsocket - Flash based Web Sockets
	// * htmlfile - A persistent connection transport.
	// * xhr-multipart - A persistent connection transport.
	// * xhr-polling - A long polling transport.
	// * jsonp-polling - A long polling transport.
	public static void main(String[] args) throws Exception {
		String host = "61.250.201.78";
		int port = 8080;
		String transport = "xhr-multipart";

		// if (args.length < 3) {
		// System.exit(-1);
		// }
		//
		// host = args[0];
		// port = Integer.parseInt(args[1]);
		// transport = args[2];

		SocketIOConnectionXHRBase client = new SocketIOConnectionXHRBase(new SocketIOConnection.SocketIOConnectionListener() {

			public void onConnect() {
				print("Connected");
			}

			public void onDisconnect(DisconnectReason reason, String errorMessage) {
				print("Disconnected: " + reason + ": " + errorMessage);
				System.exit(-1);
			}

			public void onMessage(int messageType, String message) {
				print((String) message);
			}

		}, host, (short) port, transport, false);
		client.connect();
		// BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		// String line;
		// while ((line = in.readLine()) != null) {
		// client.sendMessage(line);
		// }
		client.sendMessage(2, "message:[\"cli\",\"hello\"]}");

		Thread t = new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

		t.join();
	}
}

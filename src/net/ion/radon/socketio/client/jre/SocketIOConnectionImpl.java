package net.ion.radon.socketio.client.jre;

import net.ion.radon.socketio.client.common.SocketIOConnection;
import net.ion.radon.socketio.common.ConnectionState;
import net.ion.radon.socketio.common.DisconnectReason;
import net.ion.radon.socketio.common.SocketIOException;

import org.eclipse.jetty.client.HttpClient;


public class SocketIOConnectionImpl implements SocketIOConnection {
	private final SocketIOConnection.SocketIOConnectionListener listener;
	private final String host;
	private final short port;
	private HttpClient client;

	public SocketIOConnectionImpl(SocketIOConnection.SocketIOConnectionListener listener, String host, short port) {
		this.listener = listener;
		this.host = host;
		this.port = port;
	}

	public void connect() {
		if (client == null) {
			client = new HttpClient();
			client.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
			try {
				client.start();
			} catch (Exception e) {
				client = null;
				listener.onDisconnect(DisconnectReason.ERROR, "Failed to initialize");
			}
		}

	}

	public void close() {
		// TODO Auto-generated method stub

	}

	public void disconnect() {
		// TODO Auto-generated method stub

	}

	public ConnectionState getConnectionState() {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendMessage(String message) throws SocketIOException {
		sendMessage(0, message);
	}

	public void sendMessage(int messageType, String message) throws SocketIOException {
		// TODO Auto-generated method stub

	}
}

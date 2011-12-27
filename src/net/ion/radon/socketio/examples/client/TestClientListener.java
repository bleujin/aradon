package net.ion.radon.socketio.examples.client;

import net.ion.framework.util.Debug;
import net.ion.radon.socketio.client.common.SocketIOConnection;
import net.ion.radon.socketio.client.jre.SocketIOConnectionXHRBase;
import net.ion.radon.socketio.common.DisconnectReason;


public class TestClientListener implements SocketIOConnection.SocketIOConnectionListener{

	public void onConnect() {
		Debug.debug(this.getClass(), "CONNECT") ;
	}

	public void onDisconnect(DisconnectReason reason, String errorMessage) {
		Debug.debug(this.getClass(), "DISCONNECT") ;
	}

	public void onMessage(int messageType, String message) {
		Debug.debug(this.getClass(), "ONMESSAGE", messageType, message) ;
	}
	
	public static void main(String[] args) throws Exception{
		SocketIOConnectionXHRBase client = new SocketIOConnectionXHRBase(new TestClientListener(), "61.250.201.78", (short)8080, "xhr-polling", false) ;
		client.connect() ;
		Debug.debug(client.getConnectionState()) ;
		for (int i = 0; i < 5; i++) {
			client.sendMessage(2, "message:[\"cli\",\"hello\"]}") ;
			Thread.sleep(1000) ;
			Debug.debug("send") ;
		}
		
		client.disconnect() ;
		
	}

}

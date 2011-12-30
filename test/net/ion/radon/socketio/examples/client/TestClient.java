package net.ion.radon.socketio.examples.client;

import java.io.IOException;
import java.net.URI;

import net.ion.framework.util.Debug;
import net.ion.radon.socketio.examples.client.WebSocketListener.WebSocketDraft;

import org.eclipse.jetty.websocket.WebSocketFactory;


import junit.framework.TestCase;

public class TestClient extends TestCase {

	
	public void testNewClient() throws Exception {
		
		
		
	}
	
	public void testRun() throws Exception {
		
		WebSocketClient client = new WebSocketClient(new URI("http://61.250.201.78:8080/socket.io/websocket")) {
			@Override
			public void onOpen() {
				Debug.line("onOpen") ;
			}
			
			@Override
			public void onMessage(String message) {
				try {
					this.send(message) ;
					Debug.line("onMessage", message) ;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onClose() {
				Debug.line("onClose") ;
			}
		};
		client.connect() ;
		
		client.send("Hello Client") ;
		Debug.debug("END") ;
		
		Thread joiner = new Thread(){
			public void run(){
				while(true){
					try {
						Thread.sleep(1000) ;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} ;
		
		joiner.join() ;
		
	}
	
	public void testServer() throws Exception {
		new WebSocketServer(8090) {
			
			@Override
			public void onClientOpen(WebSocket conn) {
				Debug.line('S', conn, "OPEN") ;
			}
			
			@Override
			public void onClientMessage(WebSocket conn, String message) {
				Debug.line('S', conn, message) ;
			}
			
			@Override
			public void onClientClose(WebSocket conn) {
				Debug.line('S', conn, "CLOSE") ;
			}
		}.run() ;
		
		Thread joiner = new Thread(){
			public void run(){
				while(true){
					try {
						Thread.sleep(1000) ;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} ;
		
		joiner.join() ;
	}
}

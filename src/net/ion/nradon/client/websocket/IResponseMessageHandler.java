package net.ion.nradon.client.websocket;

import net.ion.framework.util.Debug;
import net.ion.nradon.netty.codec.http.websocketx.BinaryWebSocketFrame;
import net.ion.nradon.netty.codec.http.websocketx.TextWebSocketFrame;

import org.jboss.netty.buffer.ChannelBuffer;

public interface IResponseMessageHandler {
	public final static IResponseMessageHandler DEBUG = new DebugMessageHandler() ;

	void onOpen();

	void onMessage(TextWebSocketFrame tframe);

	void onBinMessage(BinaryWebSocketFrame bframe);

	void onPong();

	void onClosed();

	void onDisconnected();
	
	boolean isConnected() ;
}


class DebugMessageHandler implements IResponseMessageHandler {

	private boolean connected = false;
	public void onBinMessage(BinaryWebSocketFrame bframe) {
		Debug.debug("WebSocket Client received message: " + bframe.getBinaryData().array().length) ;
	}

	public void onClosed() {
		Debug.debug("WebSocket Client received closing");
	}

	public void onMessage(TextWebSocketFrame tframe) {
		Debug.debug("WebSocket Client received message: " + tframe.getText());
	}

	public void onOpen() {
		this.connected = true ;
		Debug.debug("WebSocket Client connected!");
	}

	public void onPong() {
		Debug.debug("WebSocket Client received pong");
	}
	
	public boolean isConnected(){
		return connected ;
	}

	public void onDisconnected() {
		Debug.debug("WebSocket Client disconnected");
		this.connected = false ;
	}
	
}
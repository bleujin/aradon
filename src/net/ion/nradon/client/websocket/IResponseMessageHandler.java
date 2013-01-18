package net.ion.nradon.client.websocket;

import net.ion.framework.util.Debug;
import net.ion.nradon.netty.codec.http.websocketx.BinaryWebSocketFrame;
import net.ion.nradon.netty.codec.http.websocketx.TextWebSocketFrame;

public interface IResponseMessageHandler {
	public final static IResponseMessageHandler DEBUG = new DebugMessageHandler() ;

	void onOpen();

	void onMessage(TextWebSocketFrame tframe);

	void onBinMessage(BinaryWebSocketFrame bframe);

	void onPong();

	void onClosed();

	void onDisconnected();
	
	
	public abstract class OnTextMessageHander implements IResponseMessageHandler{
		public void onBinMessage(BinaryWebSocketFrame bframe) {
		}

		public void onClosed() {
		}

		public void onOpen() {
		}

		public void onPong() {
		}

		public void onDisconnected() {
		}
	};
}


class DebugMessageHandler implements IResponseMessageHandler {

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
		Debug.debug("WebSocket Client connected!");
	}

	public void onPong() {
		Debug.debug("WebSocket Client received pong");
	}

	public void onDisconnected() {
		Debug.debug("WebSocket Client disconnected");
	}
	
}
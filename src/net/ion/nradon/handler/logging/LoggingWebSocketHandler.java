package net.ion.nradon.handler.logging;

import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;

class LoggingWebSocketHandler implements WebSocketHandler {

	private final LogSink logSink;
	private final WebSocketConnection loggingConnection;
	private final WebSocketHandler handler;

	LoggingWebSocketHandler(LogSink logSink, WebSocketConnection loggingConnection, WebSocketHandler handler) {
		this.logSink = logSink;
		this.loggingConnection = loggingConnection;
		this.handler = handler;
	}

	public void onOpen(WebSocketConnection connection) throws Throwable {
		logSink.webSocketConnectionOpen(connection);
		handler.onOpen(loggingConnection);
	}

	public void onClose(WebSocketConnection connection) throws Throwable {
		logSink.webSocketConnectionClose(connection);
		logSink.httpEnd(connection.httpRequest());
		handler.onClose(loggingConnection);
	}

	public void onMessage(WebSocketConnection connection, String message) throws Throwable {
		logSink.webSocketInboundData(connection, message);
		handler.onMessage(loggingConnection, message);
	}

	public void onMessage(WebSocketConnection connection, byte[] message) throws Throwable {
		logSink.webSocketInboundData(connection, message);
		handler.onMessage(loggingConnection, message);
	}

	public void onPing(WebSocketConnection connection, byte[] message) throws Throwable {
		logSink.webSocketInboundPing(connection, message);
		handler.onPing(loggingConnection, message);
	}

	public void onPong(WebSocketConnection connection, byte[] message) throws Throwable {
		logSink.webSocketInboundPong(connection, message);
		handler.onPong(loggingConnection, message);
	}
}

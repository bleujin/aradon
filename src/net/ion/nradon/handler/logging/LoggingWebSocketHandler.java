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

	@Override
	public void onOpen(WebSocketConnection connection) throws Exception {
		logSink.webSocketConnectionOpen(connection);
		handler.onOpen(loggingConnection);
	}

	@Override
	public void onClose(WebSocketConnection connection) throws Exception {
		logSink.webSocketConnectionClose(connection);
		logSink.httpEnd(connection.httpRequest());
		handler.onClose(loggingConnection);
	}

	@Override
	public void onMessage(WebSocketConnection connection, String message) throws Throwable {
		logSink.webSocketInboundData(connection, message);
		handler.onMessage(loggingConnection, message);
	}

	@Override
	public void onMessage(WebSocketConnection connection, byte[] message) throws Throwable {
		logSink.webSocketInboundData(connection, message);
		handler.onMessage(loggingConnection, message);
	}

	@Override
	public void onPong(WebSocketConnection connection, String message) throws Throwable {
		logSink.webSocketInboundPong(connection, message);
		handler.onPong(loggingConnection, message);
	}
}
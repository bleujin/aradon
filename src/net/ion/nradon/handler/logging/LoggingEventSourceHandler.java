package net.ion.nradon.handler.logging;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceHandler;

class LoggingEventSourceHandler implements EventSourceHandler {

	private final LogSink logSink;
	private final EventSourceConnection loggingConnection;
	private final EventSourceHandler handler;

	LoggingEventSourceHandler(LogSink logSink, EventSourceConnection loggingConnection, EventSourceHandler handler) {
		this.logSink = logSink;
		this.loggingConnection = loggingConnection;
		this.handler = handler;
	}

	public void onOpen(EventSourceConnection connection) throws Exception {
		logSink.eventSourceConnectionOpen(connection);
		handler.onOpen(loggingConnection);
	}

	public void onClose(EventSourceConnection connection) throws Exception {
		logSink.eventSourceConnectionClose(connection);
		logSink.httpEnd(connection.httpRequest());
		handler.onClose(loggingConnection);
	}
}

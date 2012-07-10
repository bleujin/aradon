package net.ion.nradon.handler.logging;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.netty.contrib.EventSourceMessage;
import net.ion.nradon.wrapper.EventSourceConnectionWrapper;

class LoggingEventSourceConnection extends EventSourceConnectionWrapper {

	private final LogSink logSink;

	LoggingEventSourceConnection(LogSink logSink, EventSourceConnection connection) {
		super(connection);
		this.logSink = logSink;
	}

	@Override
	public EventSourceConnectionWrapper send(String message) {
		logSink.eventSourceOutboundData(this, message);
		return super.send(message);
	}

	@Override
	public EventSourceConnectionWrapper send(EventSourceMessage message) {
		logSink.eventSourceOutboundData(this, message.build() + "\n");
		return super.send(message);
	}
}

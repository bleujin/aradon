package net.ion.nradon;

import java.util.concurrent.Executor;

import net.ion.nradon.netty.contrib.EventSourceMessage;

public interface EventSourceConnection extends Executor, DataHolder {
	HttpRequest httpRequest();

	EventSourceConnection send(EventSourceMessage message);

	/**
	 * @see #send(net.ion.nradon.netty.contrib.EventSourceMessage)
	 */
	@Deprecated
	EventSourceConnection send(String message);

	EventSourceConnection close();

	EventSourceConnection data(String key, Object value); // Override DataHolder to provide more specific return type.

	Executor handlerExecutor();
}

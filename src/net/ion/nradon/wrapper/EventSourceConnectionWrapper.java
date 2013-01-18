package net.ion.nradon.wrapper;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceMessage;
import net.ion.nradon.HttpRequest;

public class EventSourceConnectionWrapper implements EventSourceConnection {

	private EventSourceConnection connection;

	public EventSourceConnectionWrapper(EventSourceConnection connection) {
		this.connection = connection;
	}

	public EventSourceConnection underlyingControl() {
		return connection;
	}

	public EventSourceConnectionWrapper underlyingControl(EventSourceConnection control) {
		this.connection = control;
		return this;
	}

	public EventSourceConnection originalControl() {
		if (connection instanceof EventSourceConnectionWrapper) {
			EventSourceConnectionWrapper wrapper = (EventSourceConnectionWrapper) connection;
			return wrapper.originalControl();
		} else {
			return connection;
		}
	}

	public HttpRequest httpRequest() {
		return connection.httpRequest();
	}

	public EventSourceConnectionWrapper send(EventSourceMessage message) {
		connection.send(message);
		return this;
	}

	public EventSourceConnectionWrapper close() {
		connection.close();
		return this;
	}

	public Map<String, Object> data() {
		return connection.data();
	}

	public Object data(String key) {
		return connection.data(key);
	}

	public EventSourceConnectionWrapper data(String key, Object value) {
		connection.data(key, value);
		return this;
	}

	public Set<String> dataKeys() {
		return connection.dataKeys();
	}

	public Executor handlerExecutor() {
		return connection.handlerExecutor();
	}

	public void execute(Runnable command) {
		connection.execute(command);
	}

}

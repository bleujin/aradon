package net.ion.nradon.wrapper;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import net.ion.nradon.HttpRequest;
import net.ion.nradon.WebSocketConnection;

public class WebSocketConnectionWrapper implements WebSocketConnection {

	private WebSocketConnection connection;

	public WebSocketConnectionWrapper(WebSocketConnection connection) {
		this.connection = connection;
	}

	public WebSocketConnection underlyingControl() {
		return connection;
	}

	public WebSocketConnectionWrapper underlyingControl(WebSocketConnection control) {
		this.connection = control;
		return this;
	}

	public WebSocketConnection originalControl() {
		if (connection instanceof WebSocketConnectionWrapper) {
			WebSocketConnectionWrapper wrapper = (WebSocketConnectionWrapper) connection;
			return wrapper.originalControl();
		} else {
			return connection;
		}
	}

	public HttpRequest httpRequest() {
		return connection.httpRequest();
	}

	public WebSocketConnectionWrapper send(String message) {
		connection.send(message);
		return this;
	}

	public WebSocketConnectionWrapper send(byte[] message) {
		connection.send(message);
		return this;
	}

	public WebSocketConnectionWrapper ping(String message) {
		connection.ping(message);
		return this;
	}

	public WebSocketConnectionWrapper close() {
		connection.close();
		return this;
	}

	public Map<String, Object> data() {
		return connection.data();
	}

	public Object data(String key) {
		return connection.data(key);
	}

	public WebSocketConnectionWrapper data(String key, Object value) {
		connection.data(key, value);
		return this;
	}

	public Set<String> dataKeys() {
		return connection.dataKeys();
	}

	public Executor handlerExecutor() {
		return connection.handlerExecutor();
	}

	public String version() {
		return connection.version();
	}

	public void execute(Runnable command) {
		connection.execute(command);
	}
	public String getString(String key) {
		return connection.getString(key);
	}

}

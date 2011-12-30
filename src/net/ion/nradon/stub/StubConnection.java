package net.ion.nradon.stub;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.netty.contrib.EventSourceMessage;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Implementation of {@link EventSourceConnection} and {@link WebSocketConnection} that is easy to construct and makes it easy to inspect results. Useful for testing.
 */
public class StubConnection extends StubDataHolder implements EventSourceConnection, WebSocketConnection {

	private final List<String> sentMessages = new LinkedList<String>();
	private final List<byte[]> sentBinaryMessages = new LinkedList<byte[]>();
	private final List<String> sentPings = new LinkedList<String>();
	private boolean closed = false;
	private HttpRequest httpRequest;
	private String version = null;

	public StubConnection(HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public StubConnection() {
		this(new StubHttpRequest());
	}

	public HttpRequest httpRequest() {
		return httpRequest;
	}

	public StubConnection send(EventSourceMessage message) {
		return send(message.build()).send("\n");
	}

	public StubConnection httpRequest(HttpRequest httpRequest) {
		this.httpRequest = httpRequest;
		return this;
	}

	public StubConnection send(String message) {
		sentMessages.add(message);
		return this;
	}

	public StubConnection send(byte[] message) {
		sentBinaryMessages.add(message);
		return this;
	}

	public StubConnection ping(String message) {
		sentPings.add(message);
		return this;
	}

	public StubConnection close() {
		closed = true;
		return this;
	}

	public boolean closed() {
		return closed;
	}

	public List<String> sentMessages() {
		return sentMessages;
	}

	public List<byte[]> sentBinaryMessages() {
		return sentBinaryMessages;
	}

	public List<String> sentPings() {
		return sentPings;
	}

	@Override
	public StubConnection data(String key, Object value) {
		super.data(key, value);
		return this;
	}

	public Executor handlerExecutor() {
		return this;
	}

	public String version() {
		return version;
	}

	public StubConnection version(String version) {
		this.version = version;
		return this;
	}

	public void execute(Runnable command) {
		command.run();
	}
}

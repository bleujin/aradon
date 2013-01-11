package net.ion.nradon.stub;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceMessage;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.WebSocketConnection;

import org.jboss.netty.channel.ChannelFuture;

/**
 * Implementation of {@link EventSourceConnection} and {@link WebSocketConnection} that is easy to construct and makes it easy to inspect results. Useful for testing.
 */
public class StubConnection extends StubDataHolder implements EventSourceConnection, WebSocketConnection {

    private final List<String> sentMessages = new LinkedList<String>();
    private final List<byte[]> sentBinaryMessages = new LinkedList<byte[]>();
    private final List<byte[]> sentPings = new LinkedList<byte[]>();
    private final List<byte[]> sentPongs = new LinkedList<byte[]>();
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
        return send(message.build());
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
        return send(message, 0, message.length);
    }

    public StubConnection send(byte[] message, int offset, int length) {
        byte[] subMessage = new byte[length];
        System.arraycopy(message, offset, subMessage, 0, length);
        sentBinaryMessages.add(subMessage);
        return this;
    }

    public StubConnection ping(byte[] message) {
        sentPings.add(message);
        return this;
    }

    public StubConnection pong(byte[] message) {
        sentPongs.add(message);
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

    public List<byte[]> sentPings() {
        return sentPings;
    }

    public List<byte[]> sentPongs() {
        return sentPongs;
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

	public ChannelFuture sendFuture(String message) {
		throw new UnsupportedOperationException("exception.method.notsupported");
	}
}
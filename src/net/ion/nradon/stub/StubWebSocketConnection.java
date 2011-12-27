package net.ion.nradon.stub;

import net.ion.nradon.HttpRequest;
import net.ion.nradon.WebSocketConnection;

/**
 * Implementation of {@link WebSocketConnection} that is easy to construct, and inspect results. Useful for testing.
 * 
 * @deprecated use {@link StubConnection}
 */
@Deprecated
public class StubWebSocketConnection extends StubConnection {
	public StubWebSocketConnection(HttpRequest httpRequest) {
		super(httpRequest);
	}

	public StubWebSocketConnection() {
		super();
	}
}

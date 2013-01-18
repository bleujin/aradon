package net.ion.nradon;


public interface EventSourceConnection extends HttpConnection {
	EventSourceConnection send(EventSourceMessage message);

	EventSourceConnection close();

	EventSourceConnection data(String key, Object value); // Override DataHolder to provide more specific return type.
	
}

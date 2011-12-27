package net.ion.nradon;

public interface EventSourceHandler {
	void onOpen(EventSourceConnection connection) throws Exception;

	void onClose(EventSourceConnection connection) throws Exception;
}

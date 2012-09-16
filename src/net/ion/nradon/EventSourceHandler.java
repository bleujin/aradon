package net.ion.nradon;

public interface EventSourceHandler {
	void onOpen(EventSourceConnection conn) throws Exception;

	void onClose(EventSourceConnection conn) throws Exception;
}

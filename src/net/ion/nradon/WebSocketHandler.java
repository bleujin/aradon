package net.ion.nradon;

public interface WebSocketHandler {
	void onOpen(WebSocketConnection conn) throws Exception;

	void onClose(WebSocketConnection conn) throws Exception;

	void onMessage(WebSocketConnection conn, String msg) throws Throwable;

	void onMessage(WebSocketConnection conn, byte[] msg) throws Throwable;

	void onPong(WebSocketConnection conn, String msg) throws Throwable;
}

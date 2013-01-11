package net.ion.nradon;

public interface WebSocketHandler {
	void onOpen(WebSocketConnection conn) throws Throwable;

	/**
     * Called when a connection is closed.
     *
     * @param connection the connection that was closed. Beware that the connection will be null if this handler is used in a {@link WebSocket} that fails to connect.
     * @throws Exception
     */
	void onClose(WebSocketConnection conn) throws Throwable;

	void onMessage(WebSocketConnection conn, String msg) throws Throwable;

	void onMessage(WebSocketConnection conn, byte[] msg) throws Throwable;

	void onPing(WebSocketConnection conn, byte[] msg) throws Throwable;
	
	void onPong(WebSocketConnection conn, byte[] msg) throws Throwable;
}

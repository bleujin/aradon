package net.ion.nradon;

public class BaseWebSocketHandler implements WebSocketHandler {

    public void onOpen(WebSocketConnection connection) throws Exception {
    }

    public void onClose(WebSocketConnection connection) throws Exception {
    }

    public void onMessage(WebSocketConnection connection, String msg) throws Throwable {
    }

    public void onMessage(WebSocketConnection connection, byte[] msg) throws Throwable {
    }

    public void onPing(WebSocketConnection connection, byte[] msg) throws Throwable {
        connection.pong(msg);
    }

    public void onPong(WebSocketConnection connection, byte[] msg) throws Throwable {
    }
}


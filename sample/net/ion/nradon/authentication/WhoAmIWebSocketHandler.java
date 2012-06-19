package net.ion.nradon.authentication;

import java.util.HashSet;
import java.util.Set;

import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.handler.authentication.BasicAuthenticationHandler;

/**
 * WebSocket handler that keeps track of whos connected and broadcasts to other users.
 */
public class WhoAmIWebSocketHandler implements WebSocketHandler {

    private final Set<WebSocketConnection> connections = new HashSet<WebSocketConnection>();

    public void onOpen(WebSocketConnection connection) throws Exception {
        String username = (String) connection.data(BasicAuthenticationHandler.USERNAME);
        connection.send("Hello " + username);

        for (WebSocketConnection otherConnection : connections) {
            otherConnection.send("You have been joined by " + username);
        }
        connections.add(connection);
    }

    public void onClose(WebSocketConnection connection) throws Exception {
        String username = (String) connection.data(BasicAuthenticationHandler.USERNAME);
        connections.remove(connection);

        for (WebSocketConnection otherConnection : connections) {
            otherConnection.send(username + " has left");
        }
    }

    public void onMessage(WebSocketConnection connection, String msg) throws Exception {
        // Do nothing
    }

    public void onMessage(WebSocketConnection connection, byte[] msg) {
        // Do nothing
    }

    public void onPong(WebSocketConnection connection, String msg) {
        // Do nothing
    }
}

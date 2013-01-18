package net.ion.nradon.flashchatroom;

import java.util.HashSet;
import java.util.Set;

import net.ion.framework.parse.gson.Gson;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;


public class Chatroom implements WebSocketHandler {

    private final Gson json = new Gson();

    public static final String USERNAME_KEY = "username";

    static class Incoming {
        enum Action {LOGIN, SAY}

        Action action;
        String loginUsername;
        String message;
    }

    static class Outgoing {
        enum Action {JOIN, LEAVE, SAY}

        Action action;
        String username;
        String message;
    }

    private Set<WebSocketConnection> connections = new HashSet<WebSocketConnection>();

    public void onOpen(WebSocketConnection connection) throws Exception {
        connections.add(connection);
    }

    public void onMessage(WebSocketConnection connection, String msg) throws Exception {
        Incoming incoming = json.fromJson(msg, Incoming.class);
        switch (incoming.action) {
            case LOGIN:
                login(connection, incoming.loginUsername);
                break;
            case SAY:
                say(connection, incoming.message);
                break;
        }
    }

    public void onMessage(WebSocketConnection connection, byte[] msg) {
    }

    public void onPong(WebSocketConnection connection, byte[] msg) {
    }

    public void onPing(WebSocketConnection connection, byte[] msg) {
    	connection.pong(msg) ;
    }


    
    private void login(WebSocketConnection connection, String username) {
        connection.data(USERNAME_KEY, username); // associate username with connection

        Outgoing outgoing = new Outgoing();
        outgoing.action = Outgoing.Action.JOIN;
        outgoing.username = username;
        broadcast(outgoing);
    }

    private void say(WebSocketConnection connection, String message) {
        String username = (String) connection.data(USERNAME_KEY);
        if (username != null) {
            Outgoing outgoing = new Outgoing();
            outgoing.action = Outgoing.Action.SAY;
            outgoing.username = username;
            outgoing.message = message;
            broadcast(outgoing);
        }
    }

    private void broadcast(Outgoing outgoing) {
        String jsonStr = this.json.toJson(outgoing);
        for (WebSocketConnection connection : connections) {
            if (connection.data(USERNAME_KEY) != null) { // only broadcast to those who have completed login
                connection.send(jsonStr);
            }
        }
    }

    public void onClose(WebSocketConnection connection) throws Exception {
        String username = (String) connection.data(USERNAME_KEY);
        if (username != null) {
            Outgoing outgoing = new Outgoing();
            outgoing.action = Outgoing.Action.LEAVE;
            outgoing.username = username;
            broadcast(outgoing);
        }
        connections.remove(connection);
    }
}

package net.ion.nradon.sample.echo;

import net.ion.nradon.WebServer;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.handler.HttpToWebSocketHandler;
import net.ion.nradon.handler.exceptions.PrintStackTraceExceptionHandler;

import static net.ion.nradon.WebServers.createWebServer;

/**
 * Simple Echo server to be used with the Autobahn test suite.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        WebServer webServer = createWebServer(9001).add(new HttpToWebSocketHandler(new WebSocketHandler() {
            @Override
            public void onOpen(WebSocketConnection connection) throws Exception {
            }

            @Override
            public void onClose(WebSocketConnection connection) throws Exception {
            }

            @Override
            public void onMessage(WebSocketConnection connection, String msg) throws Exception {
                connection.send(msg);
            }

            @Override
            public void onMessage(WebSocketConnection connection, byte[] msg) {
                connection.send(msg);
            }

            @Override
            public void onPong(WebSocketConnection connection, String msg) {
                connection.ping(msg);
            }
        })).connectionExceptionHandler(new PrintStackTraceExceptionHandler()).start();

        System.out.println("Echo server running on: " + webServer.getUri());
    }

}

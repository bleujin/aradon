package net.ion.nradon.echo;

import static net.ion.nradon.config.RadonConfiguration.newBuilder;
import net.ion.nradon.Radon;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.handler.HttpToWebSocketHandler;
import net.ion.nradon.handler.exceptions.PrintStackTraceExceptionHandler;

/**
 * Simple Echo server to be used with the Autobahn test suite.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Radon webServer = newBuilder(9001).add(new HttpToWebSocketHandler(new WebSocketHandler() {
            public void onOpen(WebSocketConnection connection) throws Exception {
            }

            public void onClose(WebSocketConnection connection) throws Exception {
            }

            public void onMessage(WebSocketConnection connection, String msg) throws Exception {
                connection.send(msg);
            }

            public void onMessage(WebSocketConnection connection, byte[] msg) {
                connection.send(msg);
            }

            public void onPong(WebSocketConnection connection, byte[] msg) {
            }
            public void onPing(WebSocketConnection connection, byte[] msg) {
                connection.pong(msg);
            }
        })).connectionExceptionHandler(new PrintStackTraceExceptionHandler()).startRadon() ;

        System.out.println("Echo server running on: " + webServer.getConfig().publicUri());
    }

}

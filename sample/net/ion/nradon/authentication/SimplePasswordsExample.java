package net.ion.nradon.authentication;

import static net.ion.nradon.WebServers.createWebServer;

import java.io.IOException;

import net.ion.nradon.WebServer;
import net.ion.nradon.handler.StaticFileHandler;
import net.ion.nradon.handler.authentication.BasicAuthenticationHandler;
import net.ion.nradon.handler.authentication.InMemoryPasswords;

/**
 * This example demonstrates restricting access using HTTP BASIC authentication.
 *
 * Passwords are known in advance and stored in memory.
 */
public class SimplePasswordsExample {

    public static void main(String[] args) throws IOException {
        InMemoryPasswords passwords = new InMemoryPasswords()
                .add("joe", "secret")
                .add("jeff", "somepassword");

        WebServer webServer = createWebServer(45453)
                .add(new BasicAuthenticationHandler(passwords))
                .add("/whoami", new WhoAmIHttpHandler())
                .add("/whoami-ws", new WhoAmIWebSocketHandler())
                .add(new StaticFileHandler("src/test/java/samples/authentication/content"))
                .start();

        System.out.println("Running on " + webServer.getUri());
    }

}

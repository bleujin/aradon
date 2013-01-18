package net.ion.nradon.authentication;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.SimpleStaticFileHandler;
import net.ion.nradon.handler.authentication.BasicAuthenticationHandler;
import net.ion.nradon.handler.authentication.InMemoryPasswords;

/**
 * This example demonstrates restricting access using HTTP BASIC authentication.
 *
 * Passwords are known in advance and stored in memory.
 */
public class SimplePasswordsExample {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        InMemoryPasswords passwords = new InMemoryPasswords()
                .add("joe", "secret")
                .add("jeff", "somepassword");

        Radon webServer = RadonConfiguration.newBuilder(45453)
                .add(new BasicAuthenticationHandler(passwords))
                .add("/whoami", new WhoAmIHttpHandler())
                .add("/whoami-ws", new WhoAmIWebSocketHandler())
                .add(new SimpleStaticFileHandler("src/test/java/samples/authentication/content")).startRadon();

        System.out.println("Running on " + webServer.getConfig().publicUri());
    }

}

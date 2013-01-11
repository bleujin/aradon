package net.ion.nradon.authentication;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import net.ion.nradon.HttpRequest;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.StaticFileHandler;
import net.ion.nradon.handler.authentication.BasicAuthenticationHandler;
import net.ion.nradon.handler.authentication.PasswordAuthenticator;

/**
 * This example how to verify username/passwords in the background without blocking the
 * main Webbit thread.
 */
public class AsyncPasswordsExample {

    static Executor backgroundAuthenticatorThread = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Radon webServer = RadonConfiguration.newBuilder(45454)
                .add(new BasicAuthenticationHandler(new SlowPasswordAuthenticator()))
                .add("/whoami", new WhoAmIHttpHandler())
                .add("/whoami-ws", new WhoAmIWebSocketHandler())
                .add(new StaticFileHandler("test/net/ion/nradon/sample/authentication/content")).startRadon();

        System.out.println("Running on " + webServer.getConfig().publicUri());
    }

    /**
     * Custom password authenticator. This runs on the main Webbit handler thread.
     */
    private static class SlowPasswordAuthenticator implements PasswordAuthenticator {
        public void authenticate(HttpRequest request, final String username, final String password, final ResultCallback callback, final Executor handlerExecutor) {
            // Submit some slow work to a background thread, so we don't block the main Webbit thread.
            backgroundAuthenticatorThread.execute(new BackgroundWorker(username, password, callback, handlerExecutor));
        }
    }

    /**
     * This runs on the background thread.
     */
    static class BackgroundWorker implements Runnable {

        private final String username;
        private final String password;
        private final PasswordAuthenticator.ResultCallback callback;
        private final Executor handlerExecutor;

        public BackgroundWorker(String username, String password, PasswordAuthenticator.ResultCallback callback, Executor handlerExecutor) {
            this.username = username;
            this.password = password;
            this.callback = callback;
            this.handlerExecutor = handlerExecutor;
        }

        public void run() {
            // Do something slowly in the background
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            boolean authenticated = username.equals("admin") && password.endsWith("secret");

            // Ok, we have a result.. back to the main Webbit thread.
            handlerExecutor.execute(new BackgroundWorkerResult(authenticated, callback));
        }
    }

    /**
     * This runs back on the main Webbit thread.
     */
    static class BackgroundWorkerResult implements Runnable {

        private final boolean authenticated;
        private final PasswordAuthenticator.ResultCallback callback;

        BackgroundWorkerResult(boolean authenticated, PasswordAuthenticator.ResultCallback callback) {
            this.authenticated = authenticated;
            this.callback = callback;
        }

        public void run() {
            if (authenticated) {
                callback.success();
            } else {
                callback.failure();
            }
        }
    }
}

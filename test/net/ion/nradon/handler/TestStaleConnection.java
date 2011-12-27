package net.ion.nradon.handler;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.WebServer;

import org.junit.After;
import org.junit.Test;

import java.io.IOException;

import static net.ion.nradon.WebServers.createWebServer;
import static net.ion.nradon.testutil.HttpClient.contents;
import static net.ion.nradon.testutil.HttpClient.httpPost;
import static org.junit.Assert.assertEquals;

public class TestStaleConnection {

    private WebServer webServer = createWebServer(59504);

    @After
    public void die() throws IOException, InterruptedException {
        webServer.stop().join();
    }

    @Test
    public void closesConnectionAfterTimeoutIfClientKeepsConnectioOpen() throws IOException, InterruptedException {
        webServer
                .staleConnectionTimeout(100)
                .add(new HttpHandler() {
                    @Override
                    public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control)
                            throws Exception
                    {
                        response.content("Body = {" + request.body() + "}");
                        response.header("Content-Length",
                                        (String) null); // This makes the client hang until the server closes the connection.
                        response.end();
                    }
                }).start();
        String result = contents(httpPost(webServer, "/", "hello\n world"));
        assertEquals("Body = {hello\n world}", result);
    }
}

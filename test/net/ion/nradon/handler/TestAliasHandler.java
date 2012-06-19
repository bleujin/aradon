package net.ion.nradon.handler;

import static net.ion.nradon.WebServers.createWebServer;
import static net.ion.nradon.testutil.HttpClient.contents;
import static net.ion.nradon.testutil.HttpClient.httpGet;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import net.ion.nradon.WebServer;

import org.junit.After;
import org.junit.Test;

public class TestAliasHandler {
    private WebServer webServer = createWebServer(59504);

    @After
    public void die() throws IOException, InterruptedException {
        webServer.stop().join();
    }

    @Test
    public void forwardsAliasedPath() throws Exception {
        webServer
                .add("/tomayto", new AliasHandler("/tomato"))
                .add("/tomato", new StringHttpHandler("text/plain", "body"))
                .start();
        assertEquals("body", contents(httpGet(webServer, "/tomayto")));
    }
}

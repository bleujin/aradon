package net.ion.nradon.handler;

import static net.ion.nradon.WebServers.createWebServer;
import static net.ion.nradon.testutil.HttpClient.contents;
import static net.ion.nradon.testutil.HttpClient.httpGet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.concurrent.Executor;

import net.ion.nradon.HttpHandler;
import net.ion.nradon.WebServer;
import net.ion.nradon.stub.StubHttpControl;
import net.ion.nradon.stub.StubHttpRequest;
import net.ion.nradon.stub.StubHttpResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestEmbeddedResourceHandler {
    private WebServer webServer = createWebServer(59504);
    private HttpHandler handler;

    @Before
    public void createHandler() {
        Executor immediateExecutor = new Executor() {
            public void execute(Runnable command) {
                command.run();
            }
        };
        handler = new StaticFileHandler("./resource/web", immediateExecutor);
    }

    @After
    public void stop() throws IOException, InterruptedException {
        webServer.stop().join();
    }

    @Test
    public void should404ForMissingFiles() throws Exception {
        assertReturnedWithStatus(200, handle(request("/index.html")));
        assertReturnedWithStatus(200, handle(request("/index.html?x=y")));
        assertReturnedWithStatus(404, handle(request("/notfound.html")));
        assertReturnedWithStatus(404, handle(request("/foo/bar")));
    }

    @Test
    public void shouldFindWelcomeFile() throws Exception {
        assertReturnedWithStatus(200, handle(request("/")));
    }

    @Test
    public void shouldWorkInRealServer() throws IOException, InterruptedException {
        webServer.add(handler).start();
        assertEquals("Hello world", contents(httpGet(webServer, "/index.html")));
        assertEquals("Hello world", contents(httpGet(webServer, "/index.html?x=y")));
    }

    @Test
    public void shouldWorkWithBiggerFilesUsingEmbedded() throws IOException, InterruptedException {
        webServer.add(handler).start();
        String jquery = contents(httpGet(webServer, "/jquery-1.5.2.js"));
        if (!jquery.endsWith("})(window);\n")) {
            fail("Ended with:[" + jquery.substring(jquery.length() - 200, jquery.length()) + "]");
        }
    }

    @Test
    public void shouldWorkWithBiggerFilesUsingFileHandler() throws IOException, InterruptedException {
        handler = new StaticFileHandler("./resource/web");
        webServer.add(handler).start();

        String jquery = contents(httpGet(webServer, "/jquery-1.5.2.js"));
        if (!jquery.endsWith("})(window);\n")) {
            fail("Ended with:[" + jquery.substring(jquery.length() - 200, jquery.length()) + "]");
        }
    }

    @Test
    public void shouldFindWelcomeFileInRealServer() throws IOException, InterruptedException {
        webServer.add(handler).start();
        assertEquals("Hello world", contents(httpGet(webServer, "/")));
    }

    // --- Test helpers

    /**
     * Create stubbed request.
     */
    private StubHttpRequest request(String uri) {
        return new StubHttpRequest(uri);
    }

    /**
     * Send stub request to handler, and return a stubbed response for inspection.
     */
    private StubHttpResponse handle(StubHttpRequest request) throws Exception {
        StubHttpResponse response = new StubHttpResponse();
        handler.handleHttpRequest(request, response, new StubHttpControl(request, response));
        return response;
    }

    private void assertReturnedWithStatus(int expectedStatus, StubHttpResponse response) {
        assertEquals(expectedStatus, response.status());
        assertTrue(response.ended());
        assertNull(response.error());
    }

}

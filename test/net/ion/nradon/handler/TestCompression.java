package net.ion.nradon.handler;

import static net.ion.nradon.WebServers.createWebServer;
import static net.ion.nradon.testutil.HttpClient.contents;
import static net.ion.nradon.testutil.HttpClient.decompressContents;
import static net.ion.nradon.testutil.HttpClient.httpGetAcceptCompressed;
import static net.ion.nradon.testutil.HttpClient.httpPostCompressed;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.WebServer;

import org.junit.After;
import org.junit.Test;

public class TestCompression {

	private final WebServer webServer = createWebServer(59504);

	private final String content = "Very short string for which there is no real point in compressing, but we're going to do it anyway.";

	@After
	public void die() throws IOException, InterruptedException {
		webServer.stop().join();
	}

	@Test
	public void compressedPostIsUncompressedProperly() throws IOException {
		webServer.add(new AbstractHttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				response.content(request.body()).end();
			}
		}).start();
		String result = contents(httpPostCompressed(webServer, "/", content));
		assertEquals(content, result);
	}

	@Test
	public void compressedResponseIsSentProperly() throws IOException {
		webServer.add(new AbstractHttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				response.content(content).end();
			}
		}).start();
		HttpURLConnection urlConnection = (HttpURLConnection) httpGetAcceptCompressed(webServer, "/");
		String result = decompressContents(urlConnection);
		assertEquals(content, result);
		assertEquals("gzip", urlConnection.getContentEncoding());
	}

}

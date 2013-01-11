package net.ion.nradon.handler;

import static net.ion.nradon.testutil.HttpClient.contents;
import static net.ion.nradon.testutil.HttpClient.httpPost;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;

import org.junit.Test;

public class TestStaleConnection {

	@Test
	public void closesConnectionAfterTimeoutIfClientKeepsConnectioOpen() throws IOException, InterruptedException, ExecutionException {
		Radon radon = RadonConfiguration.newBuilder(59504).staleConnectionTimeout(100)
			.add(new AbstractHttpHandler() {
			public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
				response.content("Body = {" + request.body() + "}");
				response.header("Content-Length", (String) null); // This makes the client hang until the server closes the connection.
				response.end();
			}
		}).startRadon();
		String result = contents(httpPost(radon, "/", "hello\n world"));
		assertEquals("Body = {hello\n world}", result);

		radon.stop().get();
	}
}

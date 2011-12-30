package net.ion.nradon.handler;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;

public class AliasHandler implements HttpHandler {
	private final String uri;

	public AliasHandler(String uri) {
		this.uri = uri;
	}

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		request.uri(uri);
		control.nextHandler(request, response);
	}
}

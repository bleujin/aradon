package net.ion.nradon.handler;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.WebSocketHandler;

public class HttpToWebSocketHandler implements HttpHandler {
	private final WebSocketHandler handler;

	public HttpToWebSocketHandler(WebSocketHandler handler) {
		this.handler = handler;
	}

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		control.upgradeToWebSocketConnection(handler);
	}
}

package net.ion.nradon.handler;

import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;

public class HttpToEventSourceHandler extends AbstractHttpHandler {
	private final EventSourceHandler handler;

	public HttpToEventSourceHandler(EventSourceHandler handler) {
		this.handler = handler;
	}

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		control.upgradeToEventSourceConnection(handler);
	}
}

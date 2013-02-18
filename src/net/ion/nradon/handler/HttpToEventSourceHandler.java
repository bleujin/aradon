package net.ion.nradon.handler;

import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.handler.event.ServerEventHandler;
import net.ion.nradon.handler.event.ServerEvent.EventType;

public class HttpToEventSourceHandler implements HttpHandler {
	private final EventSourceHandler handler;

	public HttpToEventSourceHandler(EventSourceHandler handler) {
		this.handler = handler;
	}

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		control.upgradeToEventSourceConnection(handler);
	}
	
	public void onEvent(EventType etype, Radon webserver) {
		if (handler instanceof ServerEventHandler){
			((ServerEventHandler)handler).onEvent(etype, webserver) ;
		}
	}

	public int order() {
		return 7;
	}
}

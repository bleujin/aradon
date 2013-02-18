package net.ion.nradon.handler;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.handler.event.ServerEventHandler;
import net.ion.nradon.handler.event.ServerEvent.EventType;

public class HttpToWebSocketHandler implements HttpHandler {
	private final WebSocketHandler handler;

	public HttpToWebSocketHandler(WebSocketHandler handler) {
		this.handler = handler;
	}

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		control.upgradeToWebSocketConnection(handler);
	}

	public void onEvent(EventType etype, Radon webserver) {
		if (handler instanceof ServerEventHandler){
			((ServerEventHandler)handler).onEvent(etype, webserver) ;
		}
	}
	
	public int order() {
		return 8;
	}

}

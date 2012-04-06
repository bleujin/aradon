package net.ion.nradon;

import net.ion.nradon.handler.event.ServerEventHandler;

public interface HttpHandler extends ServerEventHandler{
	void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception;
}

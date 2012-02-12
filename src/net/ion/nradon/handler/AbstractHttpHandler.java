package net.ion.nradon.handler;

import net.ion.nradon.HttpHandler;
import net.ion.nradon.WebServer;
import net.ion.nradon.handler.event.ServerEvent.EventType;

public abstract class AbstractHttpHandler implements HttpHandler {
	public void onEvent(EventType event, WebServer wserver) {
		
	}
}

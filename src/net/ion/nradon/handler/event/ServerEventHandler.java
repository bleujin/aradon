package net.ion.nradon.handler.event;

import net.ion.nradon.WebServer;
import net.ion.nradon.handler.event.ServerEvent.EventType;


public interface ServerEventHandler {

	public void onEvent(EventType event, WebServer wserver) ;

}


package net.ion.nradon.handler.event;

import net.ion.nradon.Radon;
import net.ion.nradon.handler.event.ServerEvent.EventType;


public interface ServerEventHandler {

	public void onEvent(EventType event, Radon radon) ;

}


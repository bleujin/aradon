package net.ion.nradon.handler.event;

import net.ion.nradon.WebServer;

public class ServerEvent{
	
	public enum EventType {
		START, STOP
	}
	
	public ServerEvent(EventType etype, WebServer wserver){
		
	}
	
	ServerEvent startEvent(WebServer wserver){
		return new ServerEvent(EventType.START, wserver) ;
	}
}

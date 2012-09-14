package net.ion.nradon.handler.event;

import net.ion.nradon.Radon;

public class ServerEvent{
	
	public enum EventType {
		START, STOP
	}
	
	public ServerEvent(EventType etype, Radon wserver){
		
	}
	
	ServerEvent startEvent(Radon wserver){
		return new ServerEvent(EventType.START, wserver) ;
	}
}

package net.ion.radon.core.context;

import net.ion.radon.core.IService;

public interface OnEventObject {
	
	public enum AradonEvent {
		START, STOP, RELOAD
	}
	
	public void onEvent(AradonEvent event, IService service) ;
}

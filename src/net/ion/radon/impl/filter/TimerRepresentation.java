package net.ion.radon.impl.filter;

import net.ion.framework.rest.CloneableRepresentation;

import org.restlet.representation.Representation;

public class TimerRepresentation {
	
	private long created = System.currentTimeMillis() ;
	private CloneableRepresentation rep ;
	TimerRepresentation(CloneableRepresentation rep) {
		this.rep = rep ;
	}

	public final static TimerRepresentation create(CloneableRepresentation orignal) {
		return new TimerRepresentation(orignal) ;
	}

	public boolean invalidTime(long maxSecond) {
		return (System.currentTimeMillis() - created) / 1000 > maxSecond;
	}

	public Representation getClone() {
		return rep.cloneRepresentation();
	}

}

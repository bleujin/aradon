package net.ion.radon.impl.filter;

import net.ion.framework.rest.CloneableRepresentation;

public class CountDownRepresentation {

	private CloneableRepresentation rep;
	private int requestCount = 1;
	
	private CountDownRepresentation(CloneableRepresentation orignal) {
		this.rep = orignal;
	}

	public final static CountDownRepresentation create(CloneableRepresentation orignal){
		return new CountDownRepresentation(orignal);
	}

	public boolean isRangeCount(int countdown) {
		if(requestCount++ % countdown == 0){
			return false;
		}
		return true;
	}

	public Object getClone() {
		return rep.cloneRepresentation();
	}
}

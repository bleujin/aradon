package net.ion.radon.core.let;

import org.restlet.resource.Get;

public class GetResourceLet extends AbstractServerResource{
	
	@Get
	public String myGet() throws Exception {
		return GetResourceLet.class.getCanonicalName();
	}

}

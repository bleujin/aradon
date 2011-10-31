package net.ion.radon.core.let;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

public class GetResourceLet extends AbstractServerResource{
	
	@Get
	public String myGet() throws Exception {
		return GetLet.class.getCanonicalName();
	}

}

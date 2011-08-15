package net.ion.radon.core.let;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class GreenLet extends AbstractLet{

	@Override
	public Representation myDelete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Representation myGet() throws Exception {
		return new StringRepresentation(GreenLet.class.getCanonicalName());
	}

	@Override
	public Representation myPost(Representation entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Representation myPut(Representation entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

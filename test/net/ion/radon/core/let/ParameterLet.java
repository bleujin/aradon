package net.ion.radon.core.let;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class ParameterLet extends AbstractLet{

	@Override
	protected Representation myDelete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Representation myGet() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Representation myPost(Representation entity) throws Exception {
		
		if (!  (getInnerRequest().getGeneralParameter().get("num") instanceof String)) throw new IllegalArgumentException() ;
		if (!  (getInnerRequest().getFormParameter().get("num") instanceof String)) throw new IllegalArgumentException() ;
		if (!  (getInnerRequest().getParameter("num") instanceof String)) throw new IllegalArgumentException() ;
		return new StringRepresentation("hello");
	}

	@Override
	protected Representation myPut(Representation entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

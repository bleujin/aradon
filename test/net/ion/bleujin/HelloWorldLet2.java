package net.ion.bleujin;

import net.ion.radon.core.let.AbstractLet;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class HelloWorldLet2 extends AbstractLet{

	@Override
	protected Representation myDelete() throws Exception {
		return new StringRepresentation("Hello World DELETE");
	}

	@Override
	protected Representation myGet() throws Exception {
		return new StringRepresentation("Hello World2 GET ");
	}

	@Override
	protected Representation myPost(Representation entity) throws Exception {
		return new StringRepresentation("Hello World2 POST "  + getInnerRequest().getFormParameter());
	}

	@Override
	protected Representation myPut(Representation entity) throws Exception {
		getInnerRequest().getContext().putAttribute("test",getInnerRequest().getEntityAsText() );
		return new StringRepresentation("Hello World2 PUT");
	}

}

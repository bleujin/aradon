package net.ion.radon.core.let;

import org.restlet.representation.Representation;

public class TestCrossLet extends AbstractLet{

	private String targetSection = "test2";
	private String path = "/hello";
	
	
	@Override
	protected Representation myDelete() throws Exception {
		return null;
	}

	@Override
	protected Representation myGet() throws Exception {
		final LetResponse letResponse = lookupLet(targetSection, path).get();
		Representation result = letResponse.getRepresentation() ;
		return result ;
	}

	@Override
	protected Representation myPost(Representation entity) throws Exception {
		return null;
	}

	@Override
	protected Representation myPut(Representation entity) throws Exception {
		return null;
	}

}

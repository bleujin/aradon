package net.ion.radon.core.let;

import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class TestParameter {

	
	@Test
	public void testNumber() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", ParameterLet.class).getAradon() ;
		
		IAradonRequest request = AradonClientFactory.create(aradon).createRequest("/test") ;
		request.addParameter("num", "3") ;
		request.post() ;
		
	}
	
}

class ParameterLet extends AbstractLet{

	@Override
	protected Representation myDelete() throws Exception {
		return null;
	}

	@Override
	protected Representation myGet() throws Exception {
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
		return null;
	}

}


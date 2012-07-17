package net.ion.radon.core.let;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.EnumClass.IZone;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

public class TestServerResource {

	@Test
	public void contextLevelAtResource() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ConfirmContextLet.class);

		AradonClient ac = AradonClientFactory.create(at.getAradon());
		assertEquals("Request", ac.createRequest("/test?level=2").get().getText());

	}

	@Test
	public void contextLevelAtLet() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ConfirmContextLet2.class);

		AradonClient ac = AradonClientFactory.create(at.getAradon());
		assertEquals("Request", ac.createRequest("/test").get().getText());

	}

}

class ConfirmContextLet extends AbstractServerResource{

	
	@Get("?level=1")
	public String hello(){
		Debug.line(1, getInnerRequest().getParameterValues("level")) ;
		return getContext().getAttributeObject(IZone.class.getCanonicalName(), IZone.class).toString() ; 
	}

	@Get("?level=2")
	public String hello2(){
		Debug.line(2, getInnerRequest().getParameterValues("level")) ;
		return getContext().getAttributeObject(IZone.class.getCanonicalName(), IZone.class).toString() ; 
	}
	

}

class ConfirmContextLet2  extends DefaultLet{

	
	@Override
	public Representation myGet(){
		return new StringRepresentation(getContext().getAttributeObject(IZone.class.getCanonicalName(), IZone.class).toString())  ; 
	}
}
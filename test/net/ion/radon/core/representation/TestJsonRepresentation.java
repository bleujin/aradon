package net.ion.radon.core.representation;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.util.AradonTester;

import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.representation.Representation;

public class TestJsonRepresentation extends TestCase {
	
	public void testCreate() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", JsonLet.class).getAradon() ;
		
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		Representation rep = ac.createRequest("/test").get() ;
		
		Debug.line(rep.getText()); 
	}
	
	public void testToObject() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", JsonLet.class).getAradon() ;
		aradon.getEngine().getRegisteredConverters().add(new JsonObjectConverter()) ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		Representation rep = ac.createRequest("/test").post() ;
		Response res = ac.createRequest("/test").handle(Method.POST) ;
		assertEquals(true, rep != null); 
	}

}

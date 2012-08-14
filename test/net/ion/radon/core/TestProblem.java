package net.ion.radon.core;

import java.io.Serializable;

import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.ISerialRequest;
import net.ion.radon.core.config.ConnectorConfiguration;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Assert;
import org.junit.Test;
import org.restlet.data.Cookie;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class TestProblem {

	public void testSerialRequest() throws Exception {
		Aradon aradon = AradonTester.create()
			.register("", "/test", MySerialLet.class)
			.register("", "/hello", HelloWorldLet.class)
			.getAradon() ;
		
		aradon.startServer(ConnectorConfiguration.makeJettyHTTPConfig(9000)) ;
//		aradon.startServer(9000) ;

		for (int i = 0; i < 10; i++) {
			
			AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
			
			ISerialRequest req1 = ac.createSerialRequest("/test");
			Assert.assertEquals("hello", req1.get(String.class)) ;

			ac.stop() ;
		}
		
		aradon.destorySelf() ;
	}
	
	
	@Test
	public void loop() throws Exception {
		for (int i = 0; i < 20; i++) {
			new TestProblem().testSerialRequest() ;
		}
	}

}

class MySerialLet extends AbstractServerResource {
	@Get
	public Representation hello(){
		getInnerResponse().getCookieSettings().add("name", "bleujin") ;
		return new ObjectRepresentation<Serializable>("hello") ;
	}
	
	@Post
	public Representation sayName(){
		Cookie c = getInnerRequest().getCookies().getFirst("name") ;
		return new ObjectRepresentation<Serializable>(c.getValue()) ; 
	}
}

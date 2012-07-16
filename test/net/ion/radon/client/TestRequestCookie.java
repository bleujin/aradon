package net.ion.radon.client;

import java.io.Serializable;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.junit.Assert;
import org.junit.Test;
import org.restlet.data.Cookie;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class TestRequestCookie {

	@Test
	public void settingCookie() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", CookieLet.class).getAradon() ;
		aradon.startServer(9000) ;
		
		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
		IAradonRequest req1 = ac.createRequest("/test");
		
		Representation rep = req1.get() ;
		Assert.assertEquals("hello", rep.getText()) ;
		
		IAradonRequest req2 = ac.createRequest("/test");
		Assert.assertEquals("bleujin", req2.post().getText()) ;
		
		aradon.stop() ;
	}
	
	@Test
	public void testWhenAradon() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", CookieLet.class).getAradon() ;
		
		AradonClient ac = AradonClientFactory.create(aradon);
		IAradonRequest req1 = ac.createRequest("/test");
		
		Representation rep = req1.get() ;
		Assert.assertEquals("hello", rep.getText()) ;
		
		IAradonRequest req2 = ac.createRequest("/test");
		Assert.assertEquals("bleujin", req2.post().getText()) ;
		
		aradon.stop() ;
	}
	
	@Test
	public void testSerialRequest() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", MySerialLet.class).getAradon() ;
		aradon.startServer(9000) ;
		
		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
		ISerialRequest req1 = ac.createSerialRequest("/test");
		Assert.assertEquals("hello", req1.get(String.class)) ;
		
		ISerialRequest req2 = ac.createSerialRequest("/test");
		Assert.assertEquals("bleujin", req2.post(null, String.class)) ;
		
		aradon.stop() ;
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

class CookieLet extends AbstractServerResource {
	
	@Get
	public String hello(){
		getInnerResponse().getCookieSettings().add("name", "bleujin") ;
		return "hello" ;
	}
	
	@Post
	public String sayName(){
		Cookie c = getInnerRequest().getCookies().getFirst("name") ;
		return c.getValue() ; 
	}
	
}

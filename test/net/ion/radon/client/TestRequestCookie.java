package net.ion.radon.client;

import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.data.Cookie;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class TestRequestCookie {

	@Test
	public void settingCookie() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", CookieLet.class).getAradon() ;
		aradon.startServer(9000) ;
		
		IAradonRequest request = AradonClientFactory.create("http://127.0.0.1:9000").createRequest("/test");
		Representation rep = request.get() ;
		
		
		aradon.stop() ;
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
		Debug.line(c) ;
		return "hi" ; 
	}
	
}

package net.ion.radon.client;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.Configuration;
import net.ion.radon.core.config.ConfigurationBuilder;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.core.security.ChallengeAuthenticator;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.data.Method;
import org.restlet.resource.Get;

public class TestRequestHeader {
	
	@Test
	public void header() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HeaderLet.class) ;
		Aradon aradon = at.getAradon() ;
		
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		IAradonRequest req = ac.createRequest("/hello") ;
		req.addHeader("name", "bleujin") ;
		
		assertEquals("1", req.get().getText()) ;
		at.getAradon().stop() ;
	}
	
	
	@Test
	public void auth() throws Exception {
		Configuration config = ConfigurationBuilder.newBuilder().aradon().sections().restSection("")
			.path("hello").addUrlPattern("/hello")
			.handler(HelloWorldLet.class)
			.addPreFilter(new ChallengeAuthenticator("test", "bleu", "bleu")).build() ;
		Aradon aradon = Aradon.create(config) ;
		
//		aradon.startServer(9000) ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		IAradonRequest req = ac.createRequest("/hello", "bleu", "bleu") ;
		
		assertEquals(200, req.handle(Method.GET).getStatus().getCode()) ;
		
//		new InfinityThread().startNJoin() ;
	}
	
	
}

class HeaderLet extends AbstractServerResource {
	
	@Get
	public String confirmHeader(){
		
		Debug.line(getInnerRequest().getHeaders()) ;
		
		String value = getInnerRequest().getHeader("name") ;
		return "bleujin".equals(value) ? "1" : "0" ; 
	}
}

package net.ion.radon.client;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
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
	
}

class HeaderLet extends AbstractServerResource {
	
	@Get
	public String confirmHeader(){
		
		Debug.line(getInnerRequest().getHeaders()) ;
		
		String value = getInnerRequest().getHeader("name") ;
		return "bleujin".equals(value) ? "1" : "0" ; 
	}
}

package net.ion.radon.client;

import static org.junit.Assert.assertEquals;
import net.ion.radon.core.Aradon;
import net.ion.radon.util.AradonTester;

import org.junit.Test;

public class TestRequestHeader {
	
	@Test
	public void header() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", TestHeaderLet.class) ;
		Aradon aradon = at.getAradon() ;
		
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		IAradonRequest req = ac.createRequest("/hello") ;
		req.addHeader("name", "bleujin") ;
		
		assertEquals("1", req.get().getText()) ;
		at.getAradon().stop() ;
	}
	
}

package net.ion.radon.client;

import junit.framework.TestCase;
import net.ion.radon.util.AradonTester;

public class TestRequestHeader extends TestCase{
	
	public void testHeader() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", TestHeaderLet.class) ;
		at.startServer(9000) ;
		
		
		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000") ;
		IAradonRequest req = ac.createRequest("/hello") ;
		req.addHeader("name", "bleujin") ;
		
		assertEquals("1", req.get().getText()) ;
		at.getAradon().stop() ;
	}
	
}

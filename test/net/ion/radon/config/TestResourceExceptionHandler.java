package net.ion.radon.config;

import static org.junit.Assert.assertEquals;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;

public class TestResourceExceptionHandler {
	
	@Test
	public void testGetHandler() throws Exception {
		AradonTester at = AradonTester.create().register("abc", "/test", ExceptionLet.class) ;
		
		Response res = at.get("/abcdefg/test2") ;
		
		assertEquals(404, res.getStatus().getCode()) ;
		at.getAradon().stop() ;
	}
}

package net.ion.radon.config;

import static org.junit.Assert.assertEquals;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestResourceExceptionHandler {
	
	@Test
	public void testGetHandler() throws Exception {
		AradonTester at = AradonTester.create().register("abc", "/test", ExceptionLet.class) ;
		at.startServer(9000) ;
		
		Request request = new Request(Method.GET, "http://localhost:9000/abcdefg/test2") ;
		Response res = at.handle(request) ;
		
		assertEquals(404, res.getStatus().getCode()) ;
	}
	
	
	
	

}

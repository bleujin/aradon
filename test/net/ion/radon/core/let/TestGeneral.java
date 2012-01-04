package net.ion.radon.core.let;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestGeneral extends TestAradon {
	
	final String configPath = "resource/config/dev-config.xml";
	
	@Test
	public void testBeforeHandle() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/hello?param=abcd") ;
		
		Response response = handle(configPath, request) ;
		assertEquals(200, response.getStatus().getCode()) ;
	}
	
	@Test
	public void testSectionFilter() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/hello") ;
		
		aradon.init(configPath) ;
		aradon.start() ;
		
		Response response = new Response(request) ;
		aradon.handle(request, response) ;
		
		assertEquals(200, response.getStatus().getCode()) ;
		assertEquals(true, request == response.getRequest()) ;
	}

	@Test
	public void testNotFound() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/no_path") ;
		
		Response response = handle(configPath, request) ;
		assertEquals(404, response.getStatus().getCode()) ;
	}
	
	@Test
	public void testChain() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/chain") ;
		Response response = handle(configPath, request) ;
		assertEquals(200, response.getStatus().getCode()) ;
		Debug.debug(response.getEntityAsText()) ;
	}
	
	@Test
	public void testSameResponse() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/hello") ;
		
		aradon.init(configPath) ;
		aradon.start() ;
		
		Response response = new Response(request) ;
		aradon.handle(request, response) ;
		
		assertEquals(200, response.getStatus().getCode()) ;
		assertEquals(true, request == response.getRequest()) ;
	}

	@Test
	public void testSameRequest() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/hello") ;
		
		aradon.init(configPath) ;
		aradon.start() ;
		
		Response response = aradon.handle(request) ;
		
		assertEquals(200, response.getStatus().getCode()) ;
		assertEquals(true, request == response.getRequest()) ;
	}

	
}

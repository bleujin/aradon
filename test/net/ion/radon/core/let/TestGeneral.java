package net.ion.radon.core.let;

import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.ResourceException;

public class TestGeneral extends TestAradon {
	
	final String configPath = "resource/config/dev-config.xml";
	public void testBeforeHandle() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/hello?param=abcd") ;
		
		Response response = handle(configPath, request) ;
		assertEquals(200, response.getStatus().getCode()) ;
	}
	
	public void testSectionFilter() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/hello") ;
		
		aradon.init(configPath) ;
		aradon.start() ;
		
		Response response = new Response(request) ;
		aradon.handle(request, response) ;
		
		assertEquals(200, response.getStatus().getCode()) ;
		assertEquals(true, request == response.getRequest()) ;
	}

	
	public void testNotFound() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/no_path") ;
		
		Response response = handle(configPath, request) ;
		assertEquals(404, response.getStatus().getCode()) ;
	}
	
	
	public void testChain() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/chain") ;
		Response response = handle(configPath, request) ;
		assertEquals(200, response.getStatus().getCode()) ;
		Debug.debug(response.getEntityAsText()) ;
	}
	
	public void testSameResponse() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/hello") ;
		
		aradon.init(configPath) ;
		aradon.start() ;
		
		Response response = new Response(request) ;
		aradon.handle(request, response) ;
		
		assertEquals(200, response.getStatus().getCode()) ;
		assertEquals(true, request == response.getRequest()) ;
	}

	public void testSameRequest() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/hello") ;
		
		aradon.init(configPath) ;
		aradon.start() ;
		
		Response response = aradon.handle(request) ;
		
		assertEquals(200, response.getStatus().getCode()) ;
		assertEquals(true, request == response.getRequest()) ;
	}

	
}

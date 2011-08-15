package net.ion.radon;

import net.ion.framework.util.Debug;
import net.ion.radon.core.RadonLogService;
import net.ion.radon.core.SectionService;
import net.ion.radon.impl.section.PathInfo;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.ResourceException;
import org.restlet.service.LogService;

public class TestARadonMethod extends TestAradon {
	
	public void testReload() throws Exception {
		initAradon() ;
		Request request = new Request(Method.GET, "riap://component/another/test") ;
		
		SectionService another = aradon.getChildService("another") ;
		assertEquals(4, another.getChildren().size()) ;
		
		try {
			Response response = aradon.handle(request) ;
			assertEquals(404, response.getStatus().getCode()) ;
		} catch(ResourceException ignore) {
			
		}

		another.attach(PathInfo.HELLO) ;
		Response response = aradon.handle(request) ;
		assertEquals(200, response.getStatus().getCode()) ;

		assertEquals(5, another.getChildren().size()) ;
	}
	
	
	public void testDetach() throws Exception {
		initAradon() ;
		
		Request request = new Request(Method.GET, "riap://component/another/hello") ;
		
		SectionService another = aradon.getChildService("another") ;
		assertEquals(4, another.getChildren().size()) ;
		Response response = aradon.handle(request) ;
		assertEquals(200, response.getStatus().getCode()) ;
		
		aradon.detach("another") ;
		
		try {
			SectionService removed = aradon.getChildService("another") ;
			fail() ;
		} catch(IllegalArgumentException ignore){
		}
	}
	
	public void testSectionDetach() throws Exception {
		initAradon() ;
		SectionService another = aradon.getChildService("another") ;
		
		assertEquals(4, another.getChildren().size()) ;
		another.detach("hello") ;
		assertEquals(3, another.getChildren().size()) ;
		
		Request request = new Request(Method.GET, "riap://component/another/hello") ;
		Response response = new Response(request) ;
		aradon.handle(request, response) ;
		assertEquals(404, response.getStatus().getCode()) ;

		request = new Request(Method.GET, "riap://component/another/ghello") ;
		response = aradon.handle(request) ;
		assertEquals(200, response.getStatus().getCode()) ;
	}
	
	
	public void testGetName() throws Exception {
		initAradon() ;
		
		assertEquals("aradon", aradon.getName()) ;
		assertEquals("another", aradon.getChildService("another").getName()) ;
	}
	
	public void testGetPath() throws Exception {
		initAradon() ;
		
		assertEquals("/", aradon.getNamePath()) ;
		assertEquals("/another/", aradon.getChildService("another").getNamePath()) ;
		
	}
	
	public void testLoggerService() throws Exception {
		initAradon() ;
		final LogService los = aradon.getLogService();
		los.start() ;
		
		Request request = new Request(Method.GET, "riap://component/another/hello") ;
		Response response = aradon.handle(request) ;
		
		assertTrue(los instanceof RadonLogService) ;
		
		Debug.debug(((RadonLogService)los).recentLog(aradon)) ;
	}
}

package net.ion.radon.impl.let;

import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.section.PathInfo;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.representation.StringRepresentation;

public class TestHelloLet extends TestAradon {

	public void testHello() throws Exception {
		Request request = new Request(Method.GET, "riap://component/hello") ;
		Response response = handle(request) ;
		
		assertEquals(Status.SUCCESS_OK, response.getStatus());
	}
	
	public void testGet() throws Exception {
		Request request = new Request(Method.GET, "riap://component/hello?abcd=test") ;
		Response response = handle(request) ;
		
		assertEquals("test", getInnerRequest().getFormParameter().get("abcd"));
		
	}

	public void testPut() throws Exception {
		Request request = new Request(Method.PUT, "riap://component/hello") ;
		request.setEntity(new StringRepresentation("Hello")) ;
		Response response = handle(request) ;
		
		assertEquals("Hello", getInnerRequest().getContext().getSelfAttributeObject("test", String.class));
	}

	
	public void testHello2() throws Exception {
		initAradon() ;
		SectionService section = aradon.attach("test", XMLConfig.BLANK) ;
		section.attach(PathInfo.create("hello", "/hello", "", "", HelloWorldLet.class)) ;
		
		
		
		Request request = new Request(Method.GET, "riap://component/test/hello") ;
		Response response = aradon.handle(request) ;
		
		Debug.debug(response.getEntityAsText()) ;
		
		
	}
	

}

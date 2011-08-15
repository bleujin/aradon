package net.ion.radon.core.config;

import net.ion.radon.TestAradon;
import net.ion.radon.core.PathService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

public class TestContextAttribute extends TestAradon {

	public void testAradonContext() throws Exception {
		initAradon() ;
		
		TreeContext context = aradon.getServiceContext() ;
		
		Object value = context.getAttributeObject("let.contact.email") ;
		assertEquals("bleujin@i-on.net", value) ;
	}
	
	public void testSectionContext() throws Exception {
		initAradon() ;
		
		SectionService section = aradon.getChildService("");
		TreeContext scontext = section.getServiceContext();
		Object value = scontext.getAttributeObject("section.contact.email") ;
		
		PathService pservice = section.getChildService("favicon.ico") ;
		assertEquals("./resource/favicon.ico", pservice.getAttributes().get("base.dir")) ;
	}
	

	public void testRootContext() throws Exception {
		initAradon() ;
		
		Request request = new Request(Method.GET, "riap://component/") ;
		Response response = aradon.handle(request) ;
		assertEquals(200, response.getStatus().getCode()) ;
		
		InnerRequest ireq = ((InnerResponse)Response.getCurrent()).getInnerRequest() ;
		
		assertEquals("default", ireq.getPathInfo(aradon).getName()) ;
	}
	
	public void testRootContext2() throws Exception {
		initAradon() ;
		
		Request request = new Request(Method.GET, "riap://component/hello") ;
		Response response = aradon.handle(request) ;
		
		InnerRequest ireq = ((InnerResponse)Response.getCurrent()).getInnerRequest() ;
		assertEquals("hello", ireq.getPathInfo(aradon).getName()) ;
	}
	
	public void testHttpClient() throws Exception {
		Client c = new Client(Protocol.HTTP) ;
	}
	
}

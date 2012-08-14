package net.ion.radon.core.config;

import static org.junit.Assert.assertEquals;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.core.let.PathService;

import org.junit.Test;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

public class TestContextAttribute extends TestBaseAradon {

	@Test
	public void testAradonContext() throws Exception {
		Aradon aradon =  testAradon() ;
		
		TreeContext context = aradon.getServiceContext() ;
		Object value = context.getAttributeObject("let.contact.email") ;
		assertEquals("bleujin@i-on.net", value) ;
	}
	
	@Test
	public void testSectionContext() throws Exception {
		Aradon aradon = testAradon() ;
		
		SectionService section = aradon.getChildService("");
		TreeContext scontext = section.getServiceContext();
		Object value = scontext.getAttributeObject("section.contact.email") ;
		
		PathService pservice = section.getChildService("favicon.ico") ;
		
		assertEquals("./resource/favicon.ico", pservice.getContext().getAttributeObject("base.dir")) ;
	}
	

	@Test
	public void testRootContext() throws Exception {
		Aradon aradon =  testAradon() ;
		
		Request req = new Request(Method.GET, "riap://component/") ;
		Response response = aradon.handle(req) ;
		assertEquals(200, response.getStatus().getCode()) ;
		
		InnerRequest ireq = ((InnerResponse)Response.getCurrent()).getInnerRequest() ;
		assertEquals("default", ireq.getPathConfiguration().name()) ;
	}
	
	@Test
	public void testRootContext2() throws Exception {
		Aradon aradon =  testAradon() ;
		
		Response response = super.handle(aradon, "/hello", Method.GET) ;
		
		InnerRequest ireq = ((InnerResponse)Response.getCurrent()).getInnerRequest() ;
		assertEquals("hello", ireq.getPathConfiguration().name()) ;
	}
	
	@Test
	public void createHttpClient() throws Exception {
		Client c = new Client(Protocol.HTTP) ;
	}
	
}

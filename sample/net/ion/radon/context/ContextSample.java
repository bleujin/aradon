package net.ion.radon.context;

import org.restlet.resource.Get;

import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.TestAradon;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;
import junit.framework.TestCase;

public class ContextSample extends TestCase {

	public void testContextScope() throws Exception {
		
		Aradon aradon = AradonTester.create()
			.register("s1", "/test1", "c1", IMatchMode.EQUALS, ConfirmLet.class)
			.register("s1", "/test2", "c2", IMatchMode.EQUALS, ConfirmLet.class)
			.register("s2", "/test1", "c3", IMatchMode.EQUALS, ConfirmLet.class)
			.getAradon() ;
		
		aradon.getChildService("s1").getServiceContext().putAttribute("ctxkey", "in s1 section") ;
		aradon.getChildService("s1").getChildService("c2").getServiceContext().putAttribute("ctxkey", "in c2 path") ;
		aradon.getChildService("s2").getServiceContext().putAttribute("ctxkey", new StringBuilder("greeting"));
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		assertEquals("in s1 section", ac.createRequest("/s1/test1").get().getText()) ;
		assertEquals("in c2 path", ac.createRequest("/s1/test2").get().getText()) ;
		assertEquals(null, ac.createRequest("/s2/test1").get()) ;
		
		
	}
}

class ConfirmLet extends AbstractServerResource {
	
	@Get
	public String getContextValue(){
		return getInnerRequest().getContext().getAttributeObject("ctxkey", String.class) ;
	}
}

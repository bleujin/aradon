package net.ion.radon.core;

import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.let.ContextLet;
import net.ion.radon.util.AradonTester;
import junit.framework.TestCase;

public class TestContext extends TestCase{

	public void testLoad() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ContextLet.class) ;
		at.getAradon().getServiceContext().putAttribute("context.id", "Hello") ;
		
		
		String resText = AradonClientFactory.create(at.getAradon()).createRequest("/test").get().getText() ;
		assertEquals("Hello", resText) ;
	}
	
	
	
	
	
}

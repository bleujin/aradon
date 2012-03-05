package net.ion.radon.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.ion.framework.util.Debug;
import net.ion.radon.core.path.URLPattern;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestAradonInit extends TestBaseAradon{
	

	@Test
	public void testLoadAttribute() throws Exception {
		Aradon aradon = testAradon() ;
		
		assertEquals("bleujin@i-on.net", aradon.getServiceContext().getAttributeObject("let.contact.email")) ;
		assertEquals("/help/doc", aradon.getServiceContext().getAttributeObject("let.contact.help.doc")) ;
	}
	
	
	@Test
	public void testLoadGroovyScriptFilter() throws Exception {
		Aradon aradon = testAradon() ;
		
		Request request = new Request(Method.GET, "riap://component/another/hello") ;
		Response response = aradon.handle(request) ;
		
		Debug.debug(response.getEntityAsText()) ;
		
	}
	
	
	@Test
	public void testPatternMatch() throws Exception {
		String url = "/system/lore/test/ion.dev.floor3" ;
		String ptn1 = "/system/lore/{workspace}/{groupid}" ;
		String ptn2 = "/system/lore/{workspace}/{groupid}/{uid}" ;
		
		assertTrue(URLPattern.isMatch(url, ptn1));
		assertFalse(URLPattern.isMatch(url, ptn2));
		
	}
	
	
}

package net.ion.radon.core;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;

import junit.framework.TestCase;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.Rows;
import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.core.config.ConfigCreator;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.path.URLPattern;
import net.ion.radon.impl.section.RDBConnection;
import net.sf.ezmorph.array.IntArrayMorpher;

public class TestAradonInit extends TestAradon{
	

	@Test
	public void testLoadAttribute() throws Exception {
		initAradon() ;
		
		assertEquals("bleujin@i-on.net", aradon.getServiceContext().getAttributeObject("let.contact.email")) ;
		assertEquals("/help/doc", aradon.getServiceContext().getAttributeObject("let.contact.help.doc")) ;
	}
	
	
	@Test
	public void testLoadGroovyScriptFilter() throws Exception {
		initAradon() ;
		
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

package net.ion.radon.core.let;

import junit.framework.TestCase;
import net.ion.framework.util.IOUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.util.AradonTester;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;

public class TestResponse extends TestCase {
	
	final String configPath = "resource/config/dev-config.xml";
	
	public void testCall() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", GetLet.class) ;
		Aradon aradon = at.getAradon() ;
		
		
		Request request = new Request(Method.GET, "riap://component/test") ;
		final Response response = new Response(request);
		
		aradon.handle(request, response) ;
		
		assertEquals(200, response.getStatus().getCode()) ;
		assertEquals(GetLet.class.getCanonicalName(), response.getEntityAsText()) ;
		assertEquals(GetLet.class.getCanonicalName(), IOUtil.toString(response.getEntity().getStream())) ;
	}
	
	
	public void testResponseHeader() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", GetLet.class) ;
		Aradon aradon = at.getAradon() ;
		
		
		Request request = new Request(Method.GET, "riap://component/test") ;
		Response response = aradon.handle(request) ;
		
		assertEquals(200, response.getStatus().getCode()) ;
		Form form = (Form) response.getAttributes().get(RadonAttributeKey.ATTRIBUTE_HEADERS) ;
		
		assertEquals("0.5", form.getFirstValue(RadonAttributeKey.ARADON_VERSION_KEY)) ;
		
	}
	
}

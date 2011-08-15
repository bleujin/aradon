package net.ion.radon.core.let;

import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.core.RadonAttributeKey;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestResponse extends TestAradon {
	
	final String configPath = "resource/config/dev-config.xml";
	public void testBeforeHandle() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/hello?param=abcd&aradon.result.method=POST") ;
		
		final Response response = new Response(request);
		handle(configPath, request, response) ;
		
		assertEquals(200, response.getStatus().getCode()) ;
		Debug.debug(response.getEntityAsText()) ;
		Debug.debug(response.getAttributes().get(RadonAttributeKey.ATTRIBUTE_HEADERS)) ;
	}
}

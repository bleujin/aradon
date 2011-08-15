package net.ion.radon.core.let;

import net.ion.radon.TestAradon;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestInboundLet extends TestAradon{
	
	
	public void testInboundCall() throws Exception {
		initAradon() ;
		
		Request request = new Request(Method.GET, "riap://component/another/chain?myparam=value") ;
		Response res = aradon.handle(request) ;

		assertEquals(200, res.getStatus().getCode()) ;
	}

}

package net.ion.radon.core.let;

import junit.framework.TestCase;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.util.AradonTester;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestLet extends TestCase {

	public void testHttp() throws Exception {
		AradonTester at = AradonTester.create().register("another", "/test", GetLet.class) ;
		at.getAradon().startServer(9080) ;
		
		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1:9080").createRequest("/another/test") ;
		assertEquals(GetLet.class.getCanonicalName(), req.get().getText());
		
		at.getAradon().stop() ;
	}
	
	public void testUseClient() throws Exception {
		AradonTester at = AradonTester.create().register("another", "/test", GetLet.class) ;

		IAradonRequest req = AradonClientFactory.create(at.getAradon()).createRequest("/another/test") ;
		assertEquals(GetLet.class.getCanonicalName(), req.get().getText());
	}

	public void testRiapRequest() throws Exception {
		AradonTester at = AradonTester.create().register("another", "/test", GetLet.class) ;
		Aradon aradon = at.getAradon() ;

		Request request = new Request(Method.GET, "riap://component/another/test");
		Response response = aradon.handle(request);

		assertEquals(GetLet.class.getCanonicalName(), response.getEntityAsText());
	}
	

}

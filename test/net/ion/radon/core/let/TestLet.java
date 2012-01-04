package net.ion.radon.core.let;

import static org.junit.Assert.assertEquals;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestLet {

	@Test
	public void http() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", GetLet.class).register("", "/", GetLet.class) ;
		at.getAradon().startServer(9070) ;
		
		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1:9070").createRequest("/") ;
		assertEquals(GetLet.class.getCanonicalName(), req.get().getText());
		
		at.getAradon().stop() ;
	}
	
	@Test
	public void useClient() throws Exception {
		AradonTester at = AradonTester.create().register("another", "/test", GetLet.class) ;

		IAradonRequest req = AradonClientFactory.create(at.getAradon()).createRequest("/another/test") ;
		assertEquals(GetLet.class.getCanonicalName(), req.get().getText());
	}

	@Test
	public void riapRequest() throws Exception {
		AradonTester at = AradonTester.create().register("another", "/test", GetLet.class) ;
		Aradon aradon = at.getAradon() ;

		Request request = new Request(Method.GET, "riap://component/another/test");
		Response response = aradon.handle(request);

		assertEquals(GetLet.class.getCanonicalName(), response.getEntityAsText());
	}
	

}

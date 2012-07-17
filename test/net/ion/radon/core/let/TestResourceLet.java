package net.ion.radon.core.let;

import static org.junit.Assert.assertEquals;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.resource.Get;

public class TestResourceLet {

	@Test
	public void useClient() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", GetResourceLet.class).getAradon();

		IAradonRequest req = AradonClientFactory.create(aradon).createRequest("/test");
		assertEquals(GetResourceLet.class.getCanonicalName(), req.get().getText());
		aradon.stop() ;
	}

	@Test
	public void http() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", GetResourceLet.class).getAradon();
		aradon.startServer(9060);

		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1:9060").createRequest("/test");
		assertEquals(GetResourceLet.class.getCanonicalName(), req.get().getText());

		aradon.stop();
	}

}

class GetResourceLet extends AbstractServerResource{
	
	@Get
	public String myGet() throws Exception {
		return GetResourceLet.class.getCanonicalName();
	}

}

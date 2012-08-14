package net.ion.radon.core.let;

import static net.ion.nradon.WebServers.createWebServer;
import static org.junit.Assert.assertEquals;
import net.ion.nradon.WebServer;
import net.ion.nradon.handler.AliasHandler;
import net.ion.nradon.handler.StringHttpHandler;
import net.ion.radon.client.AradonClient;
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

		AradonClient ac = AradonClientFactory.create(aradon);
		IAradonRequest req = ac.createRequest("/test");
		assertEquals(GetResourceLet.class.getCanonicalName(), req.get().getText());
		ac.stop() ;
		aradon.stop() ;
	}

	@Test
	public void http() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", GetResourceLet.class).getAradon();
		aradon.startServer(9060);

		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9060");
		IAradonRequest req = ac.createRequest("/test");
		assertEquals(GetResourceLet.class.getCanonicalName(), req.get().getText());
		ac.stop() ;
		
		aradon.stop();
	}
	
	@Test
	public void newradon() throws Exception {
		WebServer webServer = createWebServer(59504);
		webServer.add("/tomayto", new AliasHandler("/tomato"))
	        .add("/tomato", new StringHttpHandler("text/plain", "body"))
	        .start();
		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:59504");
		IAradonRequest req = ac.createRequest("/tomayto");
		assertEquals("body", req.get().getText());
		ac.stop() ;
		webServer.stop().join() ;
	}
	

}

class GetResourceLet extends AbstractServerResource{
	
	@Get
	public String myGet() throws Exception {
		return GetResourceLet.class.getCanonicalName();
	}

}

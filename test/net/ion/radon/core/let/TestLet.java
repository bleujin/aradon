package net.ion.radon.core.let;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.ion.framework.util.ListUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.AradonHttpClient;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.ConnectorConfig;
import net.ion.radon.util.AradonTester;

import org.jboss.netty.util.internal.ExecutorUtil;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestLet {

	@Test
	public void httpConnect() throws Exception {
		Aradon aradon = AradonTester.create().register("test", "/hello", GetLet.class).getAradon();
		aradon.startServer(9070);

		AradonClient ac = AradonClientFactory.create("http://localhost:9070");
		// AradonClient ac = AradonHttpClient.create("http://localhost:9070") ;
		IAradonRequest req = ac.createRequest("/test/hello");
		Response response = req.handle(Method.GET);
		assertEquals(200, response.getStatus().getCode());

		aradon.stop();
	}

	@Test
	public void httpCopy() throws Exception {
		Aradon aradon = AradonTester.create().register("test", "/test", GetResourceLet.class).getAradon();
		aradon.startServer(9080);

		IAradonRequest req = AradonClientFactory.create("http://61.250.201.157:9080").createRequest("/test/test");
		assertEquals(GetResourceLet.class.getCanonicalName(), req.handle(Method.GET).getEntityAsText());

		aradon.stop();
	}
	
	@Test
	public void useClient() throws Exception {
		Aradon aradon = AradonTester.create().register("another", "/test", GetLet.class).getAradon();

		IAradonRequest req = AradonClientFactory.create(aradon).createRequest("/another/test");
		assertEquals(GetLet.class.getCanonicalName(), req.get().getText());
		aradon.stop() ;
	}

	@Test
	public void riapRequest() throws Exception {
		AradonTester at = AradonTester.create().register("another", "/test", GetLet.class);
		Aradon aradon = at.getAradon();

		Request request = new Request(Method.GET, "riap://component/another/test");
		Response response = aradon.handle(request);

		assertEquals(GetLet.class.getCanonicalName(), response.getEntityAsText());
		aradon.stop() ;
	}


}

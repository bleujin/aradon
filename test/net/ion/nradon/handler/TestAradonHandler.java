package net.ion.nradon.handler;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.Debug;
import net.ion.nradon.WebServer;
import net.ion.nradon.WebServers;
import net.ion.nradon.handler.aradon.AradonHandler;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.client.ISerialRequest;
import net.ion.radon.client.MyUser;
import net.ion.radon.client.ParameterTestLet;
import net.ion.radon.core.Aradon;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.security.User;

public class TestAradonHandler {

	@Test
	public void testGet() throws Exception {
		WebServer webServer = WebServers.createWebServer(8080);
		Aradon aradon = AradonTester.create().register("", "/test", HelloWorldLet.class).getAradon();

		webServer.add(new AradonHandler(aradon)).start();

		IAradonRequest request = AradonClientFactory.create(aradon).createRequest("/test");
		Response response = request.handle(Method.GET);
		assertEquals(200, response.getStatus().getCode());
		webServer.stop().join();
	}

	@Test
	public void testBinaryGet() throws Exception {
		WebServer webServer = WebServers.createWebServer(8080);
		Aradon aradon = AradonTester.create().register("", "/param", ParameterTestLet.class).getAradon();

		webServer.add(new AradonHandler(aradon)).start();

		IAradonRequest request = AradonClientFactory.create(aradon).createRequest("/param");

		Response response = request.handle(Method.GET);
		assertEquals(200, response.getStatus().getCode());

		ISerialRequest srequest = AradonClientFactory.create(aradon).createSerialRequest("/param?name=bleujin");

		MyUser myuser = srequest.get(MyUser.class);
		assertEquals("bleujin", myuser.getName());
		webServer.stop().join();
	}

	@Test
	public void clientInfo() throws Exception {
		WebServer webServer = WebServers.createWebServer(8080);
		Aradon aradon = AradonTester.create().register("", "/client", ClientTestLet.class).getAradon();
		webServer.add(new AradonHandler(aradon)).start();

		ISerialRequest request = AradonClientFactory.create("http://61.250.201.157:8080").createSerialRequest("/client?name=bleujin", "bleujin", "redf");
		User user = request.get(User.class);

		Debug.line(user);

		Response response = Response.getCurrent();
		Request req = response.getRequest();
		Debug.line('r', req.getClientInfo().getPort(), req.getClientInfo().getAgent(), req.getClientInfo().getAgent(), req.getCacheDirectives());

		// new InfinityThread().startNJoin() ;
		webServer.stop().join();
	}

}

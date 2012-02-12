package net.ion.radon.client;

import junit.framework.TestCase;
import net.ion.nradon.WebServer;
import net.ion.nradon.WebServers;
import net.ion.nradon.handler.aradon.AradonHandler;
import net.ion.radon.core.Aradon;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.restlet.Response;
import org.restlet.data.Method;

public class TestAradonBehavior extends TestCase {

	public void testGet() throws Exception {
		WebServer webServer = WebServers.createWebServer(8080);
		Aradon aradon = AradonTester.create()
			.register("", "/test", HelloWorldLet.class).getAradon();
		
		webServer.add(AradonHandler.create(aradon)).start();

		IAradonRequest request = AradonClientFactory.create(aradon).createRequest("/test");
		Response response = request.handle(Method.GET);
		assertEquals(200, response.getStatus().getCode());
	}
	
	public void testBinaryGet() throws Exception {
		WebServer webServer = WebServers.createWebServer(8080);
		Aradon aradon = AradonTester.create()
			.register("", "/param", ParameterTestLet.class).getAradon();
		
		webServer.add(AradonHandler.create(aradon)).start();

		IAradonRequest request = AradonClientFactory.create(aradon).createRequest("/param");
		
		Response response = request.handle(Method.GET);
		assertEquals(200, response.getStatus().getCode());
		
		ISerialRequest srequest =  AradonClientFactory.create(aradon).createSerialRequest("/param?name=bleujin") ;

		MyUser myuser = srequest.get(MyUser.class) ;
		assertEquals("bleujin", myuser.getName()) ;
	}

}

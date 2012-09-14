package net.ion.radon.client;

import junit.framework.TestCase;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.radon.core.Aradon;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.restlet.Response;
import org.restlet.data.Method;

public class TestAradonBehavior extends TestCase {

	public void testGet() throws Exception {
		Aradon aradon = AradonTester.create()
			.register("", "/test", HelloWorldLet.class).getAradon();
		
		Radon webServer = RadonConfiguration.newBuilder(8080)
			.add(aradon).startRadon();

		IAradonRequest request = AradonClientFactory.create(aradon).createRequest("/test");
		Response response = request.handle(Method.GET);
		assertEquals(200, response.getStatus().getCode());
	}
	
	public void testBinaryGet() throws Exception {
		Aradon aradon = AradonTester.create()
		.register("", "/param", ParameterTestLet.class).getAradon();

		Radon webServer = RadonConfiguration.newBuilder(8080).add(aradon).startRadon();

		IAradonRequest request = AradonClientFactory.create(aradon).createRequest("/param");
		
		Response response = request.handle(Method.GET);
		assertEquals(200, response.getStatus().getCode());
		
		ISerialRequest srequest =  AradonClientFactory.create(aradon).createSerialRequest("/param?name=bleujin") ;

		MyUser myuser = srequest.get(MyUser.class) ;
		assertEquals("bleujin", myuser.getName()) ;
	}

}

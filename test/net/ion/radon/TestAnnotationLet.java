package net.ion.radon;

import junit.framework.TestCase;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.config.RadonConfigurationBuilder;
import net.ion.nradon.let.IServiceLet;
import net.ion.nradon.netty.NettyWebServer;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.annotation.FormParam;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.restlet.data.Method;
import org.restlet.resource.Get;

public class TestAnnotationLet extends TestCase {

	public void testHello() throws Exception {
		Aradon aradon = AradonTester.create()
			.register("", "/hello", HelloWorldLet.class)
			.register("", "/hi", MyHello.class)
			.getAradon() ;
		
		final RadonConfigurationBuilder rc = RadonConfiguration.newBuilder(9000).add(aradon);
		
		NettyWebServer radon = rc.startRadon();
		
		final AradonClient ac = AradonClientFactory.create("http://localhost:9000");
		assertEquals(200, ac.createRequest("/hi").handle(Method.GET).getStatus().getCode()) ;
		final IAradonRequest request = ac.createRequest("/hi?name=bleujin");
		assertEquals("hi bleujin", request.handle(Method.GET).getEntity().getText()) ;
		
		radon.stop() ;
//		new InfinityThread().startNJoin() ;
	}
}


class MyHello implements IServiceLet {
	
	
	@Get
	public String hi(@FormParam("name") String name){
		return "hi " + name ;
	}
	
	
}
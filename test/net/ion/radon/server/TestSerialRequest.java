package net.ion.radon.server;

import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.ISerialRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.Configuration;
import net.ion.radon.core.config.ConnectorConfig.EngineType;

import org.junit.Assert;
import org.junit.Test;

public class TestSerialRequest {

	
	@Test
	public void onNetty() throws Exception {
		for (int i = 0; i < 1; i++) {
			Aradon aradon = Aradon.create(createConfig(EngineType.Netty)).startServer(9000) ;
			for (int loop = 0; loop < 1; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				ISerialRequest req1 = ac.createSerialRequest("/hello");
				Debug.line(req1.get(MyUser.class)) ;
				
				Assert.assertEquals(true, req1.get(MyUser.class) != null);

//				IAradonRequest req1 = ac.createRequest("/text");
//				Assert.assertEquals("hello", req1.get().getText());
				ac.stop();
			}
			aradon.destorySelf();
		}
	}

	@Test
	public void onJetty() throws Exception {
		for (int i = 0; i < 10; i++) {
			Aradon aradon = Aradon.create(createConfig(EngineType.Jetty)).startServer(9000) ;
			for (int loop = 0; loop < 10; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				ISerialRequest req1 = ac.createSerialRequest("/hello");
				Assert.assertEquals(true, req1.get(MyUser.class) != null);
				ac.stop();
			}
			aradon.destorySelf();
		}
	}
	

	@Test
	public void onSimple() throws Exception {
		for (int i = 0; i < 10; i++) {
			Aradon aradon = Aradon.create(createConfig(EngineType.Simple)).startServer(9000) ;
			for (int loop = 0; loop < 10; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				ISerialRequest req1 = ac.createSerialRequest("/hello");
				Assert.assertEquals(true, req1.get(MyUser.class) != null);
				ac.stop();
			}
			aradon.destorySelf();
		}
	}
	
	

	@Test
	public void onRestlet() throws Exception {
		for (int i = 0; i < 10; i++) {
			Aradon aradon = Aradon.create(createConfig(EngineType.Unknown)).startServer(9000) ;
			for (int loop = 0; loop < 10; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				ISerialRequest req1 = ac.createSerialRequest("/hello");
				Assert.assertEquals(true, req1.get(MyUser.class) != null);
				ac.stop();
			}
			aradon.destorySelf();
		}
	}
	
	
	
	private Configuration createConfig(EngineType engine){
		return Configuration.newBuilder()
		.server().connector().engine(engine).port(9000)
		.aradon().sections().restSection("")
			.path("hello").addUrlPattern("/hello").handler(MySerialLet.class)
			.path("text").addUrlPattern("/text").handler(MyTextLet.class)
		.build() ;
	}
}

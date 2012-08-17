package net.ion.radon.server;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.Configuration;
import net.ion.radon.core.config.ConnectorConfiguration;
import net.ion.radon.core.config.ConnectorConfigurationBuilder;
import net.ion.radon.core.config.ConnectorConfig.EngineType;
import net.ion.radon.util.AradonTester;

import org.junit.Assert;
import org.junit.Test;
import org.restlet.data.Protocol;

public class TestSSLProblem {

	@Test
	public void onNetty() throws Exception {
		for (int i = 0; i < 3; i++) {
			Aradon aradon = Aradon.create(createConfig(EngineType.Netty)).startServer(9000);
			AradonClient ac = AradonClientFactory.create("https://127.0.0.1:9000", true);
			for (int loop = 0; loop < 10; loop++) {
				Debug.line(ac.createRequest("/hello").get().getText());
			}
			ac.stop();
			aradon.destorySelf();
		}
	}

	@Test
	public void onSimple() throws Exception {
		for (int i = 0; i < 10; i++) {
			Aradon aradon = Aradon.create(createConfig(EngineType.Simple)).startServer(9000);
			for (int loop = 0; loop < 10; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				Assert.assertEquals("hello", ac.createRequest("/hello").get().getText());
				;
				ac.stop();
			}
			aradon.destorySelf();
		}
	}
	

	@Test
	public void onJetty() throws Exception {
		for (int i = 0; i < 3; i++) {
			Aradon aradon = Aradon.create(createConfig(EngineType.Jetty)).startServer(9000);
			AradonClient ac = AradonClientFactory.create("https://127.0.0.1:9000", true);
			for (int loop = 0; loop < 10; loop++) {
				Debug.line(ac.createRequest("/hello").get().getText());
			}
			ac.stop();
			aradon.destorySelf();
		}
	}

	@Test
	public void onRestlet() throws Exception {
		for (int i = 0; i < 10; i++) {
			Aradon aradon = Aradon.create(createConfig(EngineType.Unknown)).startServer(9000);
			for (int loop = 0; loop < 10; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				Assert.assertEquals("hello", ac.createRequest("/hello").get().getText());
				ac.stop();
			}
			aradon.destorySelf();
		}
	}
	

	private Configuration createConfig(EngineType engine){
		return Configuration.newBuilder()
			.server().connector().engine(engine).protocol(Protocol.HTTPS).port(9000)
				.addParameter("keystorePath", "./resource/keystore/aradontest.keystore")
				.addParameter("keystorePassword", "emanon")
				.addParameter("keystoreType", "JKS")
				.addParameter("keyPassword", "emanon")
			.aradon().sections().restSection("").path("hello").addUrlPattern("/hello").handler(MyTextLet.class).build() ;
	}
	
	

	@Test
	public void testRun() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/hello", MyTextLet.class).getAradon();
		ConnectorConfiguration conf = ConnectorConfigurationBuilder.newBuilder(null).engine(EngineType.Jetty)
				.protocol(Protocol.HTTPS).port(9000)
				.addParameter("keystorePath", "./resource/keystore/aradontest.keystore").addParameter("keystorePassword", "emanon")
				.addParameter("keystoreType", "JKS")
				.addParameter("type", "1")
				.addParameter("keyPassword", "emanon").create();
		aradon.startServer(conf);
		new InfinityThread().startNJoin();
	}

	@Test
	public void httpsClient() throws Exception {
		for (int count = 0; count < 10; count++) {
			AradonClient ac = AradonClientFactory.create("https://127.0.0.1:9000", true);
			for (int i = 0; i < 10; i++) {
				Debug.line(ac.createRequest("/hello").get().getText());
			}
			ac.stop() ;
		}
	}

}

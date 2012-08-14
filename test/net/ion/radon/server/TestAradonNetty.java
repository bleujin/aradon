package net.ion.radon.server;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.config.ConnectorConfiguration;
import net.ion.radon.core.config.ConnectorConfig.EngineType;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

public class TestAradonNetty {
	
	@Test
	public void useJetty() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class) ;
		at.getAradon().startServer(ConnectorConfiguration.makeJettyHTTPConfig(9005)) ;
		
		AradonClient client = AradonClientFactory.create("http://localhost:9005");
		
		IAradonRequest request = client.createRequest("/hello");
		Response res = request.handle(Method.GET) ;
		assertEquals(200, res.getStatus().getCode()) ;
		Debug.line(res.getEntityAsText()) ;
		client.stop() ;
		at.getAradon().stop() ;
		// new InfinityThread().startNJoin() ;
	}

	@Test
	public void useNetty() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class) ;
		at.getAradon().startServer(ConnectorConfiguration.makeSimpleHTTPConfig(9005)) ;
		
		AradonClient client = AradonClientFactory.create("http://localhost:9005");
		
		for (int i : ListUtil.rangeNum(1)) {
			IAradonRequest request = client.createRequest("/hello");
			Response res = request.handle(Method.GET) ;
			assertEquals(200, res.getStatus().getCode()) ;
			// res.release() ;
		}
		at.getAradon().stop() ;
		// new InfinityThread().startNJoin() ;
	}
	

	@Test
	public void testHttps() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class) ;
		Map<String, String> properties = MapUtil.<String>chainKeyMap()
			.put("keystorePath", "./resource/keystore/keystore")
			.put("keystorePassword", "password")
			.put("keystoreType", "JKS")
			.put("keyPassword", "password")
			.toMap();
		at.getAradon().startServer(ConnectorConfiguration.create(EngineType.Unknown, Protocol.HTTPS, 9000, properties)) ;
		
		
		new InfinityThread().startNJoin() ;
	}

	@Test
	public void testHttp() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class) ;
		at.getAradon().startServer(ConnectorConfiguration.create(EngineType.Jetty, Protocol.HTTP, 9000, MapUtil.<String, String>newMap())) ;
		new InfinityThread().startNJoin() ;
	}


}

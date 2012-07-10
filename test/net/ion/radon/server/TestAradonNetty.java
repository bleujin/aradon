package net.ion.radon.server;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.framework.util.ListUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.config.ConnectorConfig;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestAradonNetty {
	
	@Test
	public void useJetty() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class) ;
		at.getAradon().startServer(ConnectorConfig.makeJettyHTTPConfig(9005)) ;
		
		AradonClient client = AradonClientFactory.create("http://localhost:9005");
		
		IAradonRequest request = client.createRequest("/hello");
		Response res = request.handle(Method.GET) ;
		assertEquals(200, res.getStatus().getCode()) ;
		Debug.line(res.getEntityAsText()) ;
		at.getAradon().stop() ;
		// new InfinityThread().startNJoin() ;
	}

	@Test
	public void useNetty() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class) ;
		at.getAradon().startServer(ConnectorConfig.makeSimpleHTTPConfig(9005)) ;
		
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
		String configStr = "<connector-config port='9000' protocol='https'>" 
			+ "<parameter name='keystorePath' description=''>./resource/keystore/keystore</parameter>\n" 
			+ "<parameter name='keystorePassword' description=''>password</parameter>\n"
		 	+ "<parameter name='keystoreType' description=''>JKS</parameter>\n"
		 	+ "<parameter name='keyPassword' description=''>password</parameter>\n"
			+ "</connector-config>" ;
		
		XMLConfig config = XMLConfig.load(configStr) ;
		
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class) ;
		at.getAradon().startServer(ConnectorConfig.create(config, 9000)) ;
		
		
		new InfinityThread().startNJoin() ;
	}

	@Test
	public void testHttp() throws Exception {
		String configStr = "<connector-config engine='jetty' port='9000' protocol='http'>" 
			+ "</connector-config>" ;
		
		XMLConfig config = XMLConfig.load(configStr) ;
		
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class) ;
		at.getAradon().startServer(ConnectorConfig.create(config, 9000)) ;
		new InfinityThread().startNJoin() ;
	}


}

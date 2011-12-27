package net.ion.radon.server;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.radon.InfinityThread;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.config.ConnectorConfig;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.eclipse.jetty.xml.XmlConfiguration;
import org.junit.Ignore;
import org.junit.Test;
import org.restlet.data.MediaType;
import org.restlet.engine.http.header.CookieReader;
import org.restlet.engine.util.CookieSeries;
import org.restlet.representation.Representation;

public class TestAradonNetty {
	
	@Test
	public void useNetty() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class) ;
		at.getAradon().startServer(ConnectorConfig.makeNettyHTTPConfig(9005)) ;
		
		AradonClient client = AradonClientFactory.create("http://localhost:9005");
		
		IAradonRequest request = client.createRequest("/hello");
		Representation representation = request.get();
		assertEquals(MediaType.APPLICATION_XML, representation.getMediaType()) ;
		at.getAradon().stop() ;
		// new InfinityThread().startNJoin() ;
	}
	
	@Test
	public void testHttps() throws Exception {
		String configStr = "<connector-config engine='netty' port='9000' protocol='https'>" 
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

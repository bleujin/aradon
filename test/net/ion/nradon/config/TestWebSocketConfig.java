package net.ion.nradon.config;

import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.Configuration;
import net.ion.radon.core.config.ConfigurationBuilder;
import net.ion.radon.impl.let.HelloWorldLet;

import org.junit.Before;
import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestWebSocketConfig {

	private ConfigurationBuilder cbuilder ; 
	@Before
	public void setUp() {
		this.cbuilder = Configuration.newBuilder() ;
		cbuilder.aradon().sections().restSection("").path("hello").addUrlPattern("/hello").addUrlPattern("/hello2").handler(HelloWorldLet.class);
	}
	
	
	@Test
	public void createAradonConfig() throws Exception {
		Aradon aradon = Aradon.create(cbuilder.build()) ;
		AradonClient ac = AradonClientFactory.create(aradon) ;
		
		Response response = ac.createRequest("/hello").handle(Method.GET) ;
		Debug.line(response.getEntityAsText()) ;
	}
	
	
	@Test
	public void createNradonConfig() throws Exception {

		// nradon.getConfig() ;
	}
	
}

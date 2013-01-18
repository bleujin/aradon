package net.ion.nradon.config;

import static org.junit.Assert.assertEquals;

import javax.net.ssl.HttpsURLConnection;

import net.ion.framework.util.Debug;
import net.ion.nradon.Radon;
import net.ion.nradon.handler.TestSsl;
import net.ion.nradon.testutil.HttpClient;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.Configuration;
import net.ion.radon.core.config.ConfigurationBuilder;
import net.ion.radon.core.config.XMLConfig;
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
		cbuilder.aradon().sections()
			.restSection("").path("hello").addUrlPattern("/hello").addUrlPattern("/hello2").handler(HelloWorldLet.class)
			.restSection("test").path("hello").addUrlPattern("/hello").handler(HelloWorldLet.class);
	}
	
	
	@Test
	public void createAradonConfig() throws Exception {
		Aradon aradon = Aradon.create(cbuilder.build()) ;
		AradonClient ac = AradonClientFactory.create(aradon) ;
		
		Response response = ac.createRequest("/hello").handle(Method.GET) ;
		Debug.line(response.getEntityAsText()) ;
	}
	
	
	@Test
	public void loadAradonConfig() throws Exception {
		Radon radon = Aradon.create(cbuilder.build()).toRadon(9000).start().get() ;
		
		AradonClient ac = AradonClientFactory.create("http://localhost:9000") ;
		assertEquals(200, ac.createRequest("/hello2").handle(Method.GET).getStatus().getCode()) ;
		assertEquals(200, ac.createRequest("/test/hello").handle(Method.GET).getStatus().getCode()) ;
		radon.stop() ;
	}
	
	@Test
	public void loadFromFile() throws Exception {
		Radon radon = Aradon.create(XMLConfig.create("./resource/config/readonly-config.xml")).toRadon().start().get();
		
		AradonClient ac = AradonClientFactory.create("http://localhost:9000") ;
		assertEquals(200, ac.createRequest("/").handle(Method.GET).getStatus().getCode()) ;
		assertEquals(200, ac.createRequest("/hello").handle(Method.GET).getStatus().getCode()) ;
		assertEquals(200, ac.createRequest("/another/hello").handle(Method.GET).getStatus().getCode()) ;
		radon.stop() ;
	}
	
	
	@Test
	public void loadPlugIn() throws Exception {
		Radon radon = Aradon.create(XMLConfig.create("./resource/config/readonly-config.xml")).toRadon().start().get() ;
		
		AradonClient ac = AradonClientFactory.create("http://localhost:9000") ;
		assertEquals(200, ac.createRequest("/plugin.hello/hello").handle(Method.GET).getStatus().getCode()) ;
		assertEquals(200, ac.createRequest("/plugin.hello/view/logo.png").handle(Method.GET).getStatus().getCode()) ;
		radon.stop() ;
	}
	
	@Test
	public void testWsection() throws Exception {
		Radon radon = Aradon.create(XMLConfig.create("./resource/config/section-config.xml")).toRadon().start().get() ;
		
		radon.stop() ;
	}
	
	@Test
	public void testSSlConfig() throws Exception {
		Radon radon = Aradon.create(XMLConfig.create("./resource/config/ssl-readonly-config.xml")).toRadon().start().get();
		
		TestSsl.disableCertValidationSetUp() ;
		HttpsURLConnection urlConnection = HttpClient.httpsGet(radon, "/hello");
        assertEquals(true, HttpClient.contents(urlConnection).startsWith("Hello World"));
		
//		AradonClient ac = AradonClientFactory.create("https://127.0.0.1:8888", true) ;
//		String text = ac.createRequest("/hello").get().getText();
//		assertEquals(true, text.startsWith("Hello World")) ;
		
		radon.stop() ;
	}
	
	
	
}

package net.ion.radon;

import junit.framework.Assert;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.Configuration;
import net.ion.radon.core.config.ConfigurationBuilder;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.core.security.ChallengeAuthenticator;
import net.ion.radon.core.security.SimpleVerifier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class TestFirst {
	
	private Aradon aradon ;
	@Before
	public void setUp() throws Exception {
		Configuration conf = ConfigurationBuilder.newBuilder().aradon().sections()
			.restSection("sname").path("hello").addUrlPattern("/hello/").addUrlPattern("/hello/{name}").handler(HelloLet.class)
			.build() ;
		aradon = Aradon.create(conf) ;
	}
	
	@After
	public void tearDown() throws Exception {
		aradon.stop() ;
	}
	
	@Test
	public void hello() throws Exception {
		AradonClient ac = AradonClientFactory.create(aradon) ;
		Response response = ac.createRequest("/sname/hello/bleujin").handle(Method.GET) ;
		Assert.assertEquals(200, response.getStatus().getCode()) ;
		Assert.assertEquals("Hello bleujin", response.getEntity().getText()) ;
		ac.stop() ;
	}
	
	@Test
	public void http() throws Exception {
		aradon.startServer(9000) ;
		
		AradonClient ac = AradonClientFactory.create("http://localhost:9000") ;
		Response response = ac.createRequest("/sname/hello/bleujin").handle(Method.GET) ;
		Assert.assertEquals(200, response.getStatus().getCode()) ;
		Assert.assertEquals("Hello bleujin", response.getEntity().getText()) ;
		ac.stop() ;
	}

	
	@Test
	public void post() throws Exception {
		AradonClient ac = AradonClientFactory.create(aradon) ;
		Response response = ac.createRequest("/sname/hello/").addParameter("p1", "bleujin").handle(Method.POST) ;
		Assert.assertEquals(200, response.getStatus().getCode()) ;
		Assert.assertEquals("Hi bleujin", response.getEntity().getText()) ;
		ac.stop() ;
	}
	
	
	@Test
	public void filter() throws Exception {
		IRadonFilter filter = new ChallengeAuthenticator("sec", new SimpleVerifier("bleujin", "1234"));
		aradon.getChildService("sname").getConfig().addPreFilter(filter) ;
		
		
		aradon.startServer(9000) ;
		
		AradonClient ac = AradonClientFactory.create("http://localhost:9000") ;
		Response response = ac.createRequest("/sname/hello/bleujin").handle(Method.GET) ;
		Assert.assertEquals(401, response.getStatus().getCode()) ;

		response = ac.createRequest("/sname/hello/bleujin", "bleujin", "1234").handle(Method.GET) ;
		Assert.assertEquals(200, response.getStatus().getCode()) ;
		ac.stop() ;
		// new InfinityThread().startNJoin() ;
	}
	
	@Test
	public void context() throws Exception {
		
	}
	
}

class HelloLet extends AbstractServerResource{
	
	@Get
	public String hello(){
		return "Hello " + getInnerRequest().getAttribute("name") ; 
	}

	@Post
	public String postHello(){
		return "Hi " + getInnerRequest().getParameter("p1") ; 
	}
	

}






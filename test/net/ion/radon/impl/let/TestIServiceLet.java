package net.ion.radon.impl.let;

import static org.junit.Assert.assertEquals;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.config.RadonConfigurationBuilder;
import net.ion.nradon.let.IServiceLet;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.annotation.ContextParam;
import net.ion.radon.core.annotation.FormParam;
import net.ion.radon.util.AradonTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.data.Method;
import org.restlet.resource.Get;

public class TestIServiceLet {

	private Aradon aradon;
	
	@Before
	public void setUp() throws Exception {
		this.aradon = AradonTester.create()
			.register("", "/hello", HelloWorldLet.class)
			.register("", "/hi", MyHello.class)
			.getAradon() ;
		
		aradon.getServiceContext().putAttribute("ckey", new StringBuffer("cvalue")) ;
	}
	
	@After
	public void tearDown() throws Exception {
		aradon.stop() ;
	}
	
	
	@Test
	public void whenAradon() throws Exception {
		AradonClient ac = AradonClientFactory.create(aradon);
		assertEquals("hi bleujin", ac.createRequest("/hi?name=bleujin&m=form").handle(Method.GET).getEntityAsText()) ;
	}
	
	
	@Test
	public void whenRadon() throws Exception {
		final RadonConfigurationBuilder rc = RadonConfiguration.newBuilder(9000).add(aradon);
		
		Radon radon = rc.startRadon();
		
		final AradonClient ac = AradonClientFactory.create("http://localhost:9000");
		final IAradonRequest request = ac.createRequest("/hi?name=bleujin&m=form");
		assertEquals("hi bleujin", request.handle(Method.GET).getEntity().getText()) ;
		
		radon.stop() ;
	}
	
	@Test
	public void context() throws Exception {
		AradonClient ac = AradonClientFactory.create(aradon);
		assertEquals("cvalue", ac.createRequest("/hi?name=bleujin&m=context").handle(Method.GET).getEntityAsText()) ;
		assertEquals("cvalue", ac.createRequest("/hi?name=bleujin&m=context").handle(Method.GET).getEntityAsText()) ;
	}
	
	
}


class MyHello implements IServiceLet {
	
	@Get("?m=form")
	public String form(@FormParam("name") String name){
		return "hi " + name ;
	}
	
	@Get("?m=context")
	public String context(@ContextParam("ckey") StringBuffer cvalue){
		return cvalue.toString() ;
	}
	
	
}
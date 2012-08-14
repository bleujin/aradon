package net.ion.radon.core.config;

import static org.junit.Assert.assertEquals;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.EnumClass.Scope;
import net.ion.radon.core.filter.HiFilter;
import net.ion.radon.core.let.AbstractServerResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.Get;



public class TestConfigBuilderInterface {

	
	private ConfigurationBuilder builder ;
	@Before
	public void setUp() {
		this.builder = ConfigurationBuilder.newBuilder().aradon()
		.sections()
			.restSection("")
				.path("hello").handler(MyLet.class).scope(Scope.Request).addUrlPattern("/hello").description("First Let")
				.path("hi").handler(MyLet.class).scope(Scope.Application).addUrlPattern("/hi").description("Application Let").addPreFilter(new HiFilter())
			.restSection("greeting")
				.path("bye").handler(MyLet.class).scope(Scope.Request).addUrlPattern("/bye").description("Bye").toBuilder() 
		;
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testBuilder() throws Exception {
		assertEquals(2, builder.aradon().sections().restSection("").path("bye").handler(MyLet.class).toBuilder().aradon().sections().size()) ;
	}
	
	@Test
	public void handle() throws Exception {
		Aradon aradon = Aradon.create(builder.build()) ;
		Request request = new Request(Method.GET, "riap://component/hello") ;
		Response response = aradon.handle(request) ;
		
		assertEquals(200, response.getStatus().getCode()) ;
		assertEquals("hello", response.getEntityAsText()) ;
	}
	
	@Test
	public void addSection() throws Exception {
		Aradon aradon = Aradon.create(builder.build()) ;
		aradon.startServer(9000) ;
		Request request = new Request(Method.GET, "http://localhost:9000/hello") ;

		Response response = aradon.handle(request) ;
		assertEquals(200, response.getStatus().getCode()) ;
		assertEquals("hello", response.getEntityAsText()) ;

		aradon.stop() ;
	}
	
	@Test
	public void filter() throws Exception {
		builder.aradon().sections().restSection("").addPreFilter(new HiFilter()) ;
		builder.aradon().addPreFilter(new HiFilter()) ;
		
		Aradon aradon = Aradon.create(builder.build()) ;
		Request request = new Request(Method.GET, "riap://component/hello") ;
		Response response = aradon.handle(request) ;
		
	}
	 
}

class MyLet extends AbstractServerResource {

	@Get
	public String hello() {
		return "hello";
	}
}
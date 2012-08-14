package net.ion.radon.context;

import static org.junit.Assert.assertEquals;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.Configuration;
import net.ion.radon.core.config.ConfigurationBuilder;
import net.ion.radon.core.let.AbstractServerResource;

import org.junit.Test;
import org.restlet.resource.Get;

public class ContextSample  {

	@Test
	public void testContextScope() throws Exception {
		Configuration conf = ConfigurationBuilder.newBuilder().aradon().sections()
			.restSection("s1").addAttribute("ctxkey", "in s1 section")
				.path("c1").addUrlPattern("/test1").handler(ConfirmLet.class)
				.path("c2").addUrlPattern("/test2").handler(ConfirmLet.class).addAttribute("ctxkey", "in c2 path")
			.restSection("s2").addAttribute("ctxkey", new StringBuilder("greeting")) 
				.path("c3").addUrlPattern("/test1").handler(ConfirmLet.class).build() ;
		
		Aradon aradon = Aradon.create(conf) ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		assertEquals("in s1 section", ac.createRequest("/s1/test1").get().getText()) ;
		assertEquals("in c2 path", ac.createRequest("/s1/test2").get().getText()) ;
		assertEquals(null, ac.createRequest("/s2/test1").get()) ;
	}
}

class ConfirmLet extends AbstractServerResource {
	
	@Get
	public String getContextValue(){
		return getContext().getAttributeObject("ctxkey", String.class) ;
	}
}

package net.ion.radon.core.annotation;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.nradon.let.IServiceLet;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.util.AradonTester;

import org.restlet.data.Method;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

public class TestHello extends TestCase {

	public void testRunAradon() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/outer/{num}", OuterLet.class).getAradon() ;
		aradon.start();
		
		AradonClient ac = AradonClientFactory.create(aradon);
		assertEquals(200, ac.createRequest("/outer/0").handle(Method.GET).getStatus().getCode()) ;
		assertEquals(200, ac.createRequest("/outer/0").handle(Method.POST).getStatus().getCode()) ;

		assertEquals(200, ac.createRequest("/outer/0").handle(Method.POST).getStatus().getCode()) ;
		assertEquals(200, ac.createRequest("/outer/0").handle(Method.POST).getStatus().getCode()) ;

		assertEquals("0", ac.createRequest("/outer/0").handle(Method.PUT).getEntityAsText()) ;
		assertEquals("4", ac.createRequest("/outer/4").handle(Method.PUT).getEntityAsText()) ;
	}
}

class OuterLet implements IServiceLet {
	
	@Get 
	@Post
	public String viewText() {
		return "Hello" ;
	}
	
	@Put
	public String toInt(@DefaultValue("0") @PathParam("num") int num, @AnRequest InnerRequest request){
		Debug.line(request.getAttribute("num")) ;
		return "" + num ;
	}
}

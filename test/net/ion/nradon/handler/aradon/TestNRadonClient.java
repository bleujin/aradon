package net.ion.nradon.handler.aradon;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.nradon.Radon;
import net.ion.nradon.let.IServiceLet;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.annotation.AnRequest;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.util.AradonTester;

import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.Post;

public class TestNRadonClient extends TestCase implements IServiceLet{

	@Post
	public String printArray(@AnRequest InnerRequest request){
		Debug.line(request.getParameterValues("name")) ;
		return "hello" ;
	}
	
	public void testArrayParam() throws Exception {
		Radon radon = AradonTester.create().register("", "/test", this.getClass()).getAradon().toRadon(9000).start().get() ;
		
		AradonClient clet = AradonClientFactory.create("http://localhost:9000") ;
		Response response = clet.createRequest("/test").addParameter("name", "bleujin").addParameter("name", "hero").handle(Method.POST) ;
		response.getEntity().release(); 
//		Debug.line(response.getEntityAsText()) ;
		
		radon.stop().get() ;
	}
}

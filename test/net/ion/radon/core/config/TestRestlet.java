package net.ion.radon.core.config;

import net.ion.framework.util.Debug;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.routing.SectionRouter;
import net.ion.radon.impl.let.HelloWorldLet;

import org.junit.Test;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Server;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public class TestRestlet {



	@Test
	public void router() throws Exception {
		Router router = new Router() ;
		router.attach("/hello", MyLet.class, IMatchMode.EQUALS.toRouterMode()) ;
		router.start() ;
		
		Request request = new Request(Method.GET, "/hello") ;
		Response res = router.handle(request) ;
		Debug.line(res); 
	}
	
	
	@Test
	public void sectionRouter() throws Exception {
		SectionRouter router = new SectionRouter() ;
		router.attach("/hello", MyLet.class, IMatchMode.EQUALS.toRouterMode()) ;
		router.start() ;
		
		Request request = new Request(Method.GET, "/hello") ;
		Response res = router.handle(request) ;
		Debug.line(res); 
	}
	
	
	@Test
	public void component() throws Exception {
		Component component = new Component();  
	    component.getServers().add(new Server(Protocol.RIAP));  
	  
	    // Then attach it to the local host  
	    component.getDefaultHost().attach("/hello", HelloWorldLet.class);  
	    component.start();

	    Request request = new Request(Method.GET, "riap://component/hello") ;
	    Response res = component.handle(request) ;
		Debug.line(res); 
	    
	    component.stop() ;
	}
	
	
}

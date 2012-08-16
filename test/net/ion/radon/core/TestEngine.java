package net.ion.radon.core;

import net.ion.framework.util.Debug;
import net.ion.radon.core.server.jetty.HttpServerHelper;

import org.junit.Test;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Server;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class TestEngine extends ServerResource{

	@Test
	public void registerServer() throws Exception {
		Component component = new Component();
	    component.getDefaultHost().attach("/trace", TestEngine.class);  
	    
	    
	    
//	    Restlet restlet = new Restlet() {
//	        @Override
//	        public void handle(Request request, Response response) {
//	            response.setEntity("Hello World!", MediaType.TEXT_PLAIN);
//	        }
//	    };
	    
	    	Engine.getInstance().getRegisteredServers().clear() ;
	    	HttpServerHelper helper = new HttpServerHelper(new Server(Protocol.HTTPS, 9999, component));
	    	helper.start();
	    	
	    	Request request = new Request(Method.GET, "riap://component/trace") ;
	    	Response response = component.handle(request) ;
	    	Debug.line(response.getEntityAsText()) ;
	    	
	    	helper.stop() ;
	    
//	    new InfinityThread().startNJoin() ;
	}
	
	
	@Test
	public void testEngine() throws Exception {
		Debug.line(Engine.getInstance().getRegisteredServers()) ;
	}
	
	@Get
	public String hello(){
		return "hello world" ;
	}
}


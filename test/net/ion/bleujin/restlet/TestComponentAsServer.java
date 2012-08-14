package net.ion.bleujin.restlet;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.engine.ConnectorHelper;
import org.restlet.engine.Engine;
import org.restlet.engine.connector.HttpServerHelper;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;
import org.restlet.routing.Template;

public class TestComponentAsServer  {
	
	@Test
	public void engine() throws Exception {
		
		Engine engine = Engine.getInstance() ;
		ConnectorHelper<Server> restHelper = new org.restlet.engine.connector.HttpServerHelper(new Server(Protocol.HTTP, 9000)) ;
		ConnectorHelper<Server> jettyHelper = new net.ion.radon.core.server.jetty.HttpServerHelper(new Server(Protocol.HTTP, 9000)) ;
		ConnectorHelper<Server> nettyHelper = new net.ion.radon.core.server.netty.HttpServerHelper(new Server(Protocol.HTTP, 9000)) ;

		ConnectorHelper<Server> restsHelper = new org.restlet.ext.ssl.HttpsServerHelper(new Server(Protocol.HTTPS, 443)) ;
		ConnectorHelper<Server> jettysHelper = new net.ion.radon.core.server.jetty.HttpsServerHelper(new Server(Protocol.HTTPS, 443)) ;
		ConnectorHelper<Server> nettysHelper = new net.ion.radon.core.server.netty.HttpsServerHelper(new Server(Protocol.HTTPS, 443)) ;

		Debug.line(engine.getRegisteredServers()) ;
		engine.setRegisteredServers(ListUtil.toList(nettyHelper, nettysHelper)) ;
		Debug.line(engine.getRegisteredServers()) ;

		Component com = new Component() ;
		com.getServers().add(Protocol.HTTP, 9000) ;
		com.getServers().add(Protocol.HTTPS, 443) ;
//		com.getClients().add(Protocol.HTTP) ;
		com.getDefaultHost().attach("/", FirstServerResource.class) ;
		com.start() ;

		AradonClient ac = AradonClientFactory.create("http://localhost:9000") ;
		String text = ac.createRequest("/").handle(Method.GET).getEntityAsText() ;
		assertEquals("hello", text) ;
		
		com.stop() ;
	}

	
	@Test
	public void server() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/", HelloWorldLet.class).getAradon() ;
		aradon.start() ;
		Debug.line(aradon.getServers(), Engine.getInstance().getRegisteredServers()) ;

		for (Server server : aradon.getServers()) {
			Debug.line(server.getProtocols(), server.getPort(), server.getApplication(), server) ;
		}
		
		AradonClient iac = AradonClientFactory.create(aradon) ;
		Response iresponse = iac.createRequest("/").handle(Method.GET);
		Debug.line(iresponse, iresponse.getEntityAsText()) ;
	}

	@Test
	public void serverPort() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/", HelloWorldLet.class).getAradon() ;
		// aradon.start() ;

		aradon.startServer(9000) ;
		
		AradonClient iac = AradonClientFactory.create("http://localhost:9000") ;
		Response iresponse = iac.createRequest("/").handle(Method.GET);
		Debug.line(iresponse, iresponse.getEntityAsText()) ;
		aradon.stop() ;
	}

	@Test
	public void cserver() throws Exception {
		Engine.getInstance().getRegisteredServers().clear() ;
		Server server = new Server(Protocol.HTTP, 9000);
		Engine.getInstance().getRegisteredServers().add(new HttpServerHelper(server)) ;
		
		Component com = new Component() ;

		com.getServers().add(new Server(Protocol.HTTP, 9000)) ;
 		com.getDefaultHost().attach("/", FirstServerResource.class) ;
		com.start() ;

//		com.getServices().stop() ;
		com.stop() ;
		com.getServices().clear() ;
		
//		Debug.line("started", com.isStarted()) ;
//		com.getServices().add(new StatusService()) ;
//		com.getServices().add(new LogService()) ;
		Debug.line(com.getServices()) ;
		
		com.start() ;

		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000") ;
		ac.createRequest("/").get();
		
		com.stop() ;
		
		
	}
	
	
	@Test
	public void component() throws Exception {
		Engine engine = Engine.getInstance() ;
		Server server = new Server(Protocol.HTTP, 9000);
		ConnectorHelper<Server> restHelper = new org.restlet.engine.connector.HttpServerHelper(server) ;
		ConnectorHelper<Server> jettyHelper = new net.ion.radon.core.server.jetty.HttpServerHelper(server) ;
		ConnectorHelper<Server> nettyHelper = new net.ion.radon.core.server.netty.HttpServerHelper(server) ;
		ConnectorHelper<Server> riapHelper = new org.restlet.engine.local.RiapServerHelper(server) ;
		Debug.line(engine.getRegisteredServers()) ;
		engine.setRegisteredServers(ListUtil.toList(restHelper)) ;
		Debug.line(engine.getRegisteredServers()) ;
		
		
		Aradon aradon = AradonTester.create().register("", "/", HelloWorldLet.class).getAradon() ;
		Debug.line(aradon.getServers()) ;
		
		
		aradon.start() ;

		AradonClient iac = AradonClientFactory.create(aradon) ;
		Response iresponse = iac.createRequest("/").handle(Method.GET);
		Debug.line(iresponse, iresponse.getEntityAsText()) ;

		
		Component com = new Component() ;
		com.getServers().add(Protocol.HTTP, 9000) ;
		com.getServers().add(Protocol.HTTPS, 9000) ;
 		com.getDefaultHost().attach("/", FirstServerResource.class) ;
		com.getDefaultHost().attach(aradon) ;
		com.start() ;
		
//		aradon.startServer(9000) ;
		AradonClient ac = AradonClientFactory.create("http://localhost:9000") ;
		Response response = ac.createRequest("/").handle(Method.GET) ;
		Debug.line(response, response.getEntityAsText()) ;

		ac.stop() ;
		aradon.stop() ;
	}
	
	
	@Test
	public void application() throws Exception {
		SectionApp sa1 = new SectionApp() ;
		sa1.attach("/", FirstServerResource.class) ;

		SectionApp sa2 = new SectionApp() ;
		sa2.attach("/{name}", FirstServerResource.class) ;

		AradonApp ra = new AradonApp() ;
		ra.attach("", sa2) ;
		ra.attach("/abc", sa1) ;
		
		Component com = new Component() ;
		com.getDefaultHost().attach(ra) ;
//		com.getDefaultHost().attach("/", FirstServerResource.class) ;
//		ra.attachTo(com) ;
		com.getServers().add(Protocol.RIAP) ;
//		com.getServers().add(Protocol.HTTP, 9000) ;
//		com.start() ;
		
		Request request = new Request(Method.GET, "riap://component/abc") ;
		Response response = new Response(request) ;
		
		com.handle(request, response) ;
		Debug.line(response, response.getEntityAsText()) ;
		
		assertEquals(200, response.getStatus().getCode()) ;
		assertEquals("hello", response.getEntityAsText()) ;
	}
}


class AradonApp extends Application {
	
	private Router router = new Router(new Context()) ;
	
	AradonApp(){
		router.setDefaultMatchingMode(Router.MODE_BEST_MATCH) ;
	}

	public void attach(String pattern, Class<? extends ServerResource> clz){
		router.attach(pattern, clz) ;
	}

	public void attach(String pattern, Restlet restlet){
		router.attach(pattern, restlet) ;
	}
	
	public void handle(Request request, Response response){
		InnerRequest irequest = InnerRequest.create(request) ;
		InnerResponse iresponse = InnerResponse.create(response, irequest);
		router.handle(irequest, iresponse) ;
	}
}

class SectionApp extends Application {
	private Router router = new Router(new Context()) ;
	
	SectionApp(){
		router.setDefaultMatchingMode(Template.MODE_STARTS_WITH) ;
	}

	public void attach(String pattern, Class<? extends ServerResource> clz){
		router.attach(pattern, clz) ;
	}
	
	public void handle(Request request, Response response){
		InnerRequest irequest = InnerRequest.create(request) ;
		InnerResponse iresponse = InnerResponse.create(response, irequest);
		router.handle(irequest, iresponse) ;
	}
}


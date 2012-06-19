package net.ion.bleujin.restlet;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.ion.bleujin.restlet.let.HelloPath;
import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.classloading.PathFinder;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.core.server.jetty.JettyServerHelper;

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
import org.restlet.resource.Directory;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Router;

public class TestAradonRouter {
	
	@Test
	public void runComponent() throws Exception{
		Component component = new Component();
		component.getClients().add(Protocol.FILE) ;
		component.getServers().add(Protocol.FILE) ;
		component.getServers().add(Protocol.HTTP) ;
		
		MyAradon maradon = new MyAradon(component.getContext().createChildContext()) ;
		MySection testSection = new MySection("test", maradon, maradon.getContext().createChildContext()) ;
		testSection.attachPath("hello/{name}", HelloPath.class) ;
		
		maradon.attachSection(testSection) ;
		
		component.getDefaultHost().attach(maradon);

		// create embedding jetty server
		Server embedingJettyServer = new Server(component.getContext().createChildContext(), Protocol.HTTP, 8182, component);

		// construct and start JettyServerHelper
		
		JettyServerHelper jettyServerHelper = new net.ion.radon.core.server.jetty.HttpServerHelper(embedingJettyServer);
		jettyServerHelper.start();

//		NettyServerHelper nettyServerHelper = new net.ion.radon.core.server.netty.HttpServerHelper(embedingJettyServer) ;
//		nettyServerHelper.start();

		
			
		
		
		Thread.sleep(200) ;

		
		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:8182") ;
		
		// assertEquals(200, ac.createRequest("/www/err.txt").handle(Method.GET).getStatus().getCode()) ;
		Response response = ac.createRequest("/test/hello/bleujin").handle(Method.GET);
		assertEquals(200, response.getStatus().getCode()) ;
		Debug.line(response.getEntityAsText()) ;
		
		embedingJettyServer.stop() ;
	}
}


class MyAradon extends Application {
	
	private List<MySection> sections = ListUtil.newList() ;
	private Router router ;
	
	MyAradon(Context context){
		super(context) ;
		this.router = new MyRouter(context);
	}
	
	@Override
	public Restlet createInboundRoot() {
		final String DIR_ROOT_URI = "file:///c:/";

		Directory dir = new Directory(getContext(), DIR_ROOT_URI);
		dir.setListingAllowed(true);
		dir.setDeeplyAccessible(true);
		// dir.setNegotiateContent(true);
		router.attach("/www/", dir);
		
		for (MySection section : sections) {
			router.attach("/" + section.getSectionName() + "/", section, Router.MODE_FIRST_MATCH) ;
		}
		
		return router;
	}
	
	@Override
	public void handle(Request request, Response response){
		InnerRequest req = InnerRequest.create(request) ;
		InnerResponse res = InnerResponse.create(response, req) ; 
		Debug.line(req, res) ;
		
		sections.get(0).handle(req, res) ;
	}
	
	public void attachSection(MySection section){
		sections.add(section) ;
	}
}


class MySection extends Application {
	private String name ;
	private Router router ;
	private PathFinder finder ;
	MySection(String name, MyAradon aradon, Context scontext){
		super(scontext) ;
		this.name = name ;
		this.router = new Router(scontext) ;
		this.finder = new PathFinder(scontext) ;
	}
	
	@Override
	public Restlet createInboundRoot() {
		return router ;
	}
	
	public String getSectionName(){
		return name ;
	}
	
	public void handle(Request request, Response response){
		
		router.handle(request, response) ;
	}
	
	public void attachPath(String pathTemplate, Class<? extends ServerResource> targetClass){
		router.attach("/" + name + "/" + pathTemplate, targetClass) ;
	}
}



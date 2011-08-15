package net.ion.radon.client;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.routing.Redirector;
import org.restlet.routing.Route;

public class TestHttpServer {

	protected static final String ROOT_URI = "file:///c:/temp/";

	public static void main(String[] args) throws Exception {
		// Create a new Restlet component and add a HTTP server connector to it
		Component component = new Component();
		component.getServers().add(Protocol.HTTP, 9100);
		component.getClients().add(Protocol.FILE);

		
		// Create a new tracing Restlet
		Restlet restlet = new Restlet() {
			@Override
			public void handle(Request request, Response response) {
				// Print the requested URI path
				String message = "Resource URI  : " + request.getResourceRef() + '\n' + 
								 "Root URI      : " + request.getRootRef() + '\n' + 
								 "Routed part   : " + request.getResourceRef().getBaseRef() + '\n' +
								 
								 "Remaining part: " + request.getResourceRef().getRemainingPart() +
								 "Attribute Name: " + request.getAttributes().get("name");
				response.setEntity(message, MediaType.TEXT_PLAIN);
			}
		};

		// Then attach it to the local host
		component.getDefaultHost().attach("/trace/{name}", restlet);
		
		Application application = new Application(component.getContext().createChildContext()) {
		    @Override
		    public Restlet createInboundRoot() {
//				ConcurrentMap<String, char[]> users = new ConcurrentHashMap() ;
//				users.put("bleujin", "redf".toCharArray()) ;
//				MapVerifier mv = new MapVerifier(users) ;
//				
//				ChallengeAuthenticator guard = new ChallengeAuthenticator(getContext(), ChallengeScheme.HTTP_BASIC, "Restlet tutorial");
//				guard.setVerifier(mv) ;
//		    	
//		        Directory dir = new Directory(getContext(), ROOT_URI);
//		        guard.setNext(dir) ;
//		        return guard ;
		    	
		    	return new Directory(getContext(), ROOT_URI);
		    }
		};
		component.getDefaultHost().attach("/file", application);
		
		
		// 
		Application google = new Application(component.getContext().createChildContext()) {
		    @Override
		    public Restlet createInboundRoot() {
		        // Create a Redirector to Google search service
		        String target = "http://www.google.com/search?q=site:tistory.com+{keywords}";
		        return new Redirector(getContext(), target, Redirector.MODE_SERVER_OUTBOUND);
		    }
		};
		
		// Attach the application to the component's default host
		Route route = component.getDefaultHost().attach("/search", google);

		// While routing requests to the application, extract a query parameter
		// For instance :
		// http://localhost:8182/search?kwd=myKeyword1+myKeyword2
		// will be routed to
		// http://www.google.com/search?q=site:mysite.org+myKeyword1%20myKeyword2
		
		// route.extractQuery("keywords", "kwd", true);
		
		
		
		
		
		
		
		// Now, let's start the component!
		// Note that the HTTP server connector is also automatically started.
		component.start();
	}
}

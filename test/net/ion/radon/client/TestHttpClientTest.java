package net.ion.radon.client;

import junit.framework.TestCase;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;

public class TestHttpClientTest extends TestCase{

	public void testStart() throws Exception {
		Request request = new Request(Method.GET, "http://www.restlet.org") ;
		// request.setReferrerRef("http://www.mysite.org") ;
		
		Client client = new Client(Protocol.HTTP) ;
		Response response = client.handle(request) ;
		
		Representation output = response.getEntity() ;
		output.write(System.out) ;
	}
	
	public static void main(String[] args) throws Exception {
		Restlet restlet = new Restlet() {
		    @Override
		    public void handle(Request request, Response response) {
		        response.setEntity("Hello World!", MediaType.TEXT_PLAIN);
		    }
		};
		new Server(Protocol.HTTP, 8182, restlet).start();
		
	}
	
	public void testServer() throws Exception {
	}
}

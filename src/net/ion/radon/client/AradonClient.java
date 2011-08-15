package net.ion.radon.client;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;

public interface AradonClient {

	public IAradonRequest createRequest(String path) ;

	public IAradonRequest createRequest(String path, String id, String pwd) ;
	

	public void stop() throws Exception  ;
	
	public String getHostAddress() ;
//
//	Representation handle(Request request) ;

}

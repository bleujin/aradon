package net.ion.radon.core;

import org.restlet.Server;
import org.restlet.ext.jetty.HttpServerHelper;

public class AradonServerHelper extends HttpServerHelper{

	public AradonServerHelper(Server server) {
		super(server);
	}
	
//	protected AbstractConnector createConnector(){
//		AbstractConnector result = super.createConnector() ;
//		
//		return result;
//	}


}

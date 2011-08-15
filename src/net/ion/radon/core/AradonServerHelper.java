package net.ion.radon.core;

import net.ion.framework.util.Debug;

import org.eclipse.jetty.server.AbstractConnector;
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

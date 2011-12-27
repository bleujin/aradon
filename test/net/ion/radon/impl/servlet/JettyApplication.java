package net.ion.radon.impl.servlet;


import javax.servlet.http.HttpServletRequest;

import net.ion.framework.util.Debug;

import org.eclipse.jetty.server.Server;
import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.ext.servlet.ServletUtils;

public class JettyApplication extends Application {

	private Server server ;
	public JettyApplication(Server server) throws Exception{
		this.server = server ;
	}
	
	public void handle(Request request, Response response) {
			try {
				
				HttpServletRequest req = ServletUtils.getRequest(request) ;

				Debug.debug(req) ;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
	
	
}

package net.ion.radon.impl.servlet;


import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.let.AbstractLet;

import org.apache.commons.cli.ParseException;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpConnection;
import org.eclipse.jetty.server.Server;
import org.restlet.Application;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.adapter.HttpRequest;
import org.restlet.engine.connector.HttpClientHelper;
import org.restlet.engine.connector.HttpServerHelper;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.ext.servlet.internal.ServletWarClient;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.testatoo.container.ContainerRunner;

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

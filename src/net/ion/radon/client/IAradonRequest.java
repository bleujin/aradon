package net.ion.radon.client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;


import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.security.User;

public interface IAradonRequest {

	public Response handle(Method method) ;
	public <T> Future<T> handle(Method get, AsyncHttpHandler<T> asyncHandler);
	
	public Representation get() throws ResourceException  ;
	public Representation put() throws ResourceException  ;
	public Representation delete() throws ResourceException  ;
	public Representation post() throws ResourceException  ;
	public Representation multipart(Method method, Representation entity) ;
	
	public IAradonRequest addParameter(String name, String value) ;
	public IAradonRequest addHeader(String string, String string2);
	public Form getForm() ;
	public User getUser();
	
	public String getFullPath() ;
	public String getHost() ;
	public String getPath() ;
	// public void handle(Method method, Representation re);
	
//	public <T> T handle(Method method, Object plainObject, Class<T> rtnClz) throws ResourceException;
//	public <T> List<T> handles(Method get, Object object, Class<T> clz) throws ResourceException;
}

package net.ion.radon.client;

import java.util.List;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.security.User;

public interface IAradonRequest {

	public Representation get() throws ResourceException  ;
	public Representation put() throws ResourceException  ;
	public Representation delete() throws ResourceException  ;
	public Representation post() throws ResourceException  ;
	public IAradonRequest addParameter(String name, String value) ;
	public IAradonRequest addHeader(String string, String string2);
	public Form getForm() ;
	public User getUser();

}

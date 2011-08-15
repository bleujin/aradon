package net.ion.radon.client;

import java.util.List;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.security.User;

public interface IAradonRequest {

	public Representation get() throws ResourceException  ;

	public <T> T get(Class<? extends T> clz) throws ResourceException;

	public User getUser();

	public <T> T put(T obj, Class<? extends T> clz);
	
	public <T> T post(T obj, Class<? extends T> clz)  ;

	public <T> T delete(Class<? extends T> clz)  ;

	public <T, C> List<C> list(T obj, Class<? extends C> clz);
	
	public <T, S> S execute(T obj, Class<? extends S> clz) ;
}

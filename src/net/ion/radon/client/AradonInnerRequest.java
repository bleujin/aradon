package net.ion.radon.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import net.ion.framework.db.procedure.SerializedQuery;
import net.ion.radon.core.Aradon;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.restlet.Request;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.security.User;

public class AradonInnerRequest implements IAradonRequest{

	private Aradon aradon ;
	private String path ;
	private User user ;
	private AradonInnerRequest(Aradon aradon, String path, User user) {
		this.aradon = aradon ;
		this.path = path ;
		this.user = user ;
	}

	public static AradonInnerRequest create(Aradon aradon, String path, String id, String pwd) {
		return new AradonInnerRequest(aradon, path, new User(id, pwd));
	}
	
	public Representation get() {
		Request request = makeRequest(Method.GET) ;
		
		return aradon.handle(request).getEntity();
	}

	private Request makeRequest(Method method) {
		Request request = new Request(method, getFullPath());
		ChallengeResponse challengeResponse = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, user.getIdentifier(), user.getSecret());
		request.setChallengeResponse(challengeResponse) ;
		//request.getClientInfo().setUser(user) ;

		
		return request;
	}

	private String getFullPath() {
		return "riap://component" + (path.startsWith("/") ? "" : "/") + path;
	}

	public <T> T get(Class<? extends T> resultClass) {
		try {
			Request request = makeRequest(Method.GET) ; 
			return aradon.handle(request, resultClass);
		} catch (IOException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e) ;
		} catch (ClassNotFoundException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e) ;
		}
	}

	public <T> T post(T obj, Class<? extends T> resultClass) {
		try {
			Request request = makeRequest(Method.POST) ;
			request.setEntity(new ObjectRepresentation((Serializable)obj)) ;
			return aradon.handle(request, resultClass);
		} catch (IOException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e) ;
		} catch (ClassNotFoundException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e) ;
		}
	}
	

	public <T> T put(T obj, Class<? extends T> clz) {
		try {
			Request request = makeRequest(Method.PUT) ;
			request.setEntity(new ObjectRepresentation((Serializable)obj)) ;
			return (T) aradon.handle(request, clz);
		} catch (IOException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e) ;
		} catch (ClassNotFoundException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e) ;
		}
	}
	
	public <T> T delete(Class<? extends T> resultClass) {
		try {
			Request request = makeRequest(Method.DELETE) ; 
			return aradon.handle(request, resultClass);
		} catch (IOException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e) ;
		} catch (ClassNotFoundException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e) ;
		}
	}
	

	public <T, C> List<C> list(T obj, Class<? extends C> clz) throws ResourceException {
		try {
			Request request = makeRequest(Method.LIST) ;
			request.setEntity(new ObjectRepresentation((Serializable)obj)) ;
			return aradon.handle(request, List.class);
		} catch (IOException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e) ;
		} catch (ClassNotFoundException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e) ;
		}
	}
	
	public <T, S> S execute(T obj, Class<? extends S> clz) {
		try {
			Request request = makeRequest(Method.EXECUTE) ;
			
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos) ;
			oos.writeObject(obj) ;

			final ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			Serializable sobject = (Serializable) new ObjectInputStream(bis).readObject() ;
			
			request.setEntity(new ObjectRepresentation(sobject)) ;

			return aradon.handle(request, clz);
		} catch (IOException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e) ;
		} catch (ClassNotFoundException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e) ;
		}
	}
	
	

	public User getUser() {
		return user;
	}



}

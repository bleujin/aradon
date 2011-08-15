package net.ion.radon.client;

import java.util.List;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Preference;
import org.restlet.data.Protocol;
import org.restlet.engine.application.Decoder;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.security.User;

public class AnonyRequest implements IAradonRequest{

	private final AradonClientResource resource;
	private User user;
	
	private AnonyRequest(AradonHttpClient aclient, String path, String id, String pwd){
		this.resource =  AradonRequestUtil.createClientResource(aclient.getHostAddress() + path, aclient.getClient());
		this.user = new User(id, pwd);
		this.resource.getClientInfo().setUser(user) ;
	}
	
	
	public static AnonyRequest create(AradonHttpClient client, String path, String id, String pwd) {
		return new AnonyRequest(client, path, id, pwd);
	}

	public Representation get(){
		return resource.get() ;
	}

	public <T> T get(Class<? extends T> resultClass) {
		return resource.get(resultClass);
	}


	public User getUser() {
		return user;
	}


	public <T> T put(T obj, Class<? extends T> clz) {
		return resource.put(obj, clz);
	}
	
	
	public <T> T post(T obj, Class<? extends T> clz) {
		return resource.post(obj, clz);
	}
	

	public <T> T delete(Class<? extends T> clz) {
		return resource.delete(clz);
	}


	public <T, C> List<C> list(T obj, Class<? extends C> clz) {
		return resource.list(obj, clz);
	}
	
	public <T, S> S execute(T obj, Class<? extends S> clz) {
		return resource.execute(obj, clz);
	}
}

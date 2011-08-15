package net.ion.radon.client;

import java.util.List;

import org.restlet.Client;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.engine.application.Decoder;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.security.User;

public class BasicRequest implements IAradonRequest {

	
	private Form form = new Form() ;
	private final AradonClientResource resource;

	private BasicRequest(AradonHttpClient aclient, String path, String id, String pwd) {
		this.resource =  AradonRequestUtil.createClientResource(aclient.getHostAddress() + path, aclient.getClient());
		ChallengeResponse challengeResponse = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, id, pwd.toCharArray());
		this.resource.setChallengeResponse(challengeResponse);
		// this.resource.getClientInfo().setUser(new User(id, pwd)) ;
	}

	public final static BasicRequest create(AradonHttpClient client, String path, String id, String pwd) {
		return new BasicRequest(client, path, id, pwd);
	}

	public void clearParam(){
		this.form = new Form() ;
	}
	
	public BasicRequest addParameter(Parameter param){
		form.add(param) ;
		return this ;
	}

	public BasicRequest addParameter(String name, String value){
		form.add(name, value) ;
		return this ;
	}

	
	public Representation get()  {

		if (form.size() <= 0) return resource.get() ;
		else {
			resource.setHostRef(resource.getHostRef().toString() + "?" + form.getQueryString()) ;
			return resource.get() ;
		}
	}
	
	public Representation post() {
		return resource.post(form.getWebRepresentation()) ;
	}

	
	public Representation delete() {
		if (form.size() <= 0) return resource.delete() ;
		else {
			resource.setHostRef(resource.getHostRef().toString() + "?" + form.getQueryString()) ;
			return resource.delete() ;
		}
	}

	
	public Representation put() {
		return resource.put(form.getWebRepresentation()) ;
	}
	
	
	public String getHostRef(){
		return resource.getHostRef().toString() ;
	}
	

	public User getUser(){
		return resource.getClientInfo().getUser() ;
	}

	public <T> T get(Class<? extends T> resultClass) {
		return resource.get(resultClass);
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

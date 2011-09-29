package net.ion.radon.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.RadonAttributeKey;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.restlet.Request;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.data.Status;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.security.User;

public class AradonRequest implements IAradonRequest{

	private Form form = new Form() ;
	private Aradon aradon ;
	private String path ;
	private User user ;
	private Form headerForm ;
	
	private AradonRequest(Aradon aradon, String path, User user) {
		this.aradon = aradon ;
		this.path = path ;
		this.user = user ;
	}

	public static AradonRequest create(Aradon aradon, String path, String id, String pwd) {
		return new AradonRequest(aradon, path, new User(id, pwd));
	}
	
	public void clearParam(){
		this.form = new Form() ;
	}
	
	public Form getForm(){
		return form ;
	}
	
	public AradonRequest addParameter(Parameter param){
		form.add(param) ;
		return this ;
	}

	public AradonRequest addParameter(String name, String value){
		form.add(name, value) ;
		return this ;
	}
	
	public Representation get() {
		Request request = makeRequest(Method.GET) ;
		return aradon.handle(request).getEntity();
	}


	public Representation delete() throws ResourceException {
		Request request = makeRequest(Method.DELETE) ;
		return aradon.handle(request).getEntity();
	}

	public Representation post() throws ResourceException {
		Request request = makeRequest(Method.POST) ;
		return aradon.handle(request).getEntity();
	}

	public Representation put() throws ResourceException {
		Request request = makeRequest(Method.PUT) ;
		return aradon.handle(request).getEntity();
	}

	
	private Request makeRequest(Method method) {
		Request request = new Request(method, getFullPath());
		ChallengeResponse challengeResponse = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, user.getIdentifier(), user.getSecret());
		request.setChallengeResponse(challengeResponse) ;
		if (headerForm != null) request.getAttributes().put(RadonAttributeKey.ATTRIBUTE_HEADERS, headerForm) ;
		//request.getClientInfo().setUser(user) ;

		
		return request;
	}

	private String getFullPath() {
		return "riap://component" + (path.startsWith("/") ? "" : "/") + path;
	}

	public User getUser() {
		return user;
	}

	public IAradonRequest addHeader(String name, String value) {
		if (headerForm == null){
			headerForm = new Form() ;
		}
		
		headerForm.add(name, value) ;
		return this;
	}


}

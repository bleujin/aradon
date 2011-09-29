package net.ion.radon.client;

import java.util.List;

import net.ion.framework.util.StringUtil;
import net.ion.radon.core.RadonAttributeKey;

import org.apache.ecs.xhtml.head;
import org.restlet.Request;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.representation.Representation;
import org.restlet.security.User;

public class BasicRequest implements IAradonRequest {

	private Form form ;
	private AradonHttpClient aclient ;
	private ChallengeResponse challengeResponse ;
	private String fullPath ;
	private User user ;
	private Form headerForm ;
	
	private BasicRequest(AradonHttpClient aclient, String path, String id, String pwd, Form form) {
		this.aclient = aclient ;
		this.fullPath = aclient.getHostAddress() + path ;
		this.challengeResponse = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, id, pwd.toCharArray()) ;
		this.user = new User(challengeResponse.getIdentifier(), challengeResponse.getSecret()) ;
		this.form = form ;
	}

	public final static BasicRequest create(AradonHttpClient client, String path, String id, String pwd) {
		String[] getPath = StringUtil.split(path, '?') ;
		if (getPath.length == 1){
			return new BasicRequest(client, path, id, pwd, new Form());
		} else {
			Form form = new Form(getPath[1], CharacterSet.UTF_8) ;
			return new BasicRequest(client, path, id, pwd, form);
		}
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

	public Form getForm() {
		return form ;
	}
	
	
	public Representation get()  {
		return handle(makeRequest(Method.GET));
	}

	public Representation post() {
		return handle(makeRequest(Method.POST));
	}

	public Representation delete() {
		return handle(makeRequest(Method.DELETE));
	}
	
	public Representation put() {
		return handle(makeRequest(Method.PUT));
	}
	
	private Request makeRequest(Method method) {
		Request request = new Request(method, fullPath) ;
		
		request.setEntity(form.getWebRepresentation()) ;
		request.setChallengeResponse(challengeResponse) ;
		if (headerForm != null) request.getAttributes().put(RadonAttributeKey.ATTRIBUTE_HEADERS, headerForm) ;
		
		return request;
	}	
	
	
	private Representation handle(Request request) {
		Representation result = aclient.handle(request);
		request.release() ;
		return result ;
	}

	public String getHostRef(){
		return fullPath ;
	}

	public User getUser(){
		return user ;
	}

	public IAradonRequest addHeader(String name, String value) {
		if (headerForm == null){
			headerForm = new Form() ;
		}
		
		headerForm.add(name, value) ;
		return this;
	}
}

package net.ion.radon.client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.util.StringUtil;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.security.User;

public class BasicRequest implements IAradonRequest {

	private Form form;
	private AradonHttpClient aclient;
	private ChallengeResponse challengeResponse;
	private final String path;
	private final String fullPath;
	private User user;
	private Form tempHeaderForm;
	private Representation directEntity = null ;

	private BasicRequest(AradonHttpClient aclient, String path, String id, String pwd, Form form) {
		this.aclient = aclient;
		this.fullPath = aclient.getHostAddress() + path;
		this.path = path;
		this.challengeResponse = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, id, pwd.toCharArray());
		this.user = new User(challengeResponse.getIdentifier(), challengeResponse.getSecret());
		this.form = form;
		this.tempHeaderForm = new Form();
	}

	public final static BasicRequest create(AradonHttpClient client, String path, String id, String pwd) {
		String[] getPath = StringUtil.split(path, '?');
		if (getPath.length == 1) {
			return new BasicRequest(client, path, id, pwd, new Form());
		} else {
			Form form = new Form(getPath[1], CharacterSet.UTF_8);
			return new BasicRequest(client, path, id, pwd, form);
		}
	}

	public void clearParam() {
		this.form = new Form();
	}

	public BasicRequest addParameter(Parameter param) {
		form.add(param);
		return this;
	}

	public BasicRequest addParameter(String name, String value) {
		form.add(name, value);
		return this;
	}

	public Form getForm() {
		return form;
	}

	public Response handle(Method method) {
		Response response = aclient.syncHandle(makeRequest(method));
		return response;
	}
	
	public <T> Future<T> asyncHandle(Method method, final AsyncHttpHandler<T> ahandler) {
		Request request = makeRequest(method);
		return aclient.asyncHandle(request, ahandler);	
	}


	public Representation get() {
		return handleRequest(makeRequest(Method.GET));
	}

	public Representation post() {
		return handleRequest(makeRequest(Method.POST));
	}

	public Representation delete() {
		return handleRequest(makeRequest(Method.DELETE));
	}

	public Representation put() {
		return handleRequest(makeRequest(Method.PUT));
	}

	private Request makeRequest(Method method) {
		Request request = null;
		if (method == Method.GET || method == Method.DELETE) {
			request = new Request(method, getFullPath() + form.getQueryString());
		} else {
			request = new Request(method, getFullPath());
			if (directEntity != null) request.setEntity(directEntity) ; 
			else request.setEntity(form.getWebRepresentation());
		}
		request.setChallengeResponse(challengeResponse);
		HeaderUtil.setHeader(request, tempHeaderForm);

		return request;
	}

	private Representation handleRequest(Request request) {
		Representation result = aclient.handleRequest(request);
		return result;
	}

	public String getFullPath() {
		String pathString = (StringUtil.contains(fullPath, '?')) ? fullPath + "&" : fullPath + "?" ;
		return pathString;
	}

	public User getUser() {
		return user;
	}

	public IAradonRequest addHeader(String name, String value) {
		tempHeaderForm.add(name, value);
		return this;
	}

	public String getHost() {
		return aclient.getHostAddress();
	}

	public String getPath() {
		return path;
	}

	public String toString() {
		return getFullPath() + "[" + this.getClass().getName() + "]";
	}

	public <T> T handle(Method method, Object plainObject, Class<T> rtnClz) throws ResourceException {
		try {
			Request request = makeRequest(method);
			request.setEntity(new StringRepresentation(JsonParser.fromObject(plainObject).toString()));
			Representation repr = handleRequest(request);
			if (repr.getMediaType().equals(MediaType.APPLICATION_JSON)) {
				String str = repr.getText();
				if (StringUtil.isBlank(str)) return null ;
				return JsonParser.fromString(str).getAsJsonObject().getAsObject(rtnClz) ;
			}
			return null;
		} catch (IOException ex) {
			throw new ResourceException(ex) ;
		}
	}
	
	public <T> List<T> handles(Method method, Object plainObject, Class<T> rtnClz) throws ResourceException {
		try {
			Request request = makeRequest(method);
			request.setEntity(new StringRepresentation(JsonParser.fromObject(plainObject).toString()));
			Representation repr = handleRequest(request);
			if (repr.getMediaType().equals(MediaType.APPLICATION_JSON)) {
				String str = repr.getText();
				if (StringUtil.isBlank(str)) return null ;
				return JsonParser.fromString(str).getAsJsonArray().asList(rtnClz) ;
			}
			return null;
		} catch (IOException ex) {
			throw new ResourceException(ex) ;
		}
	}

	public IAradonRequest setEntity(Representation entity) {
		this.directEntity = entity ;
		return this;
	}

	
}

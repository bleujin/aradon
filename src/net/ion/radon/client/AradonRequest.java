package net.ion.radon.client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.RadonAttributeKey;

import org.eclipse.jetty.http.HttpHeaders;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Uniform;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.engine.header.CookieReader;
import org.restlet.engine.util.CookieSeries;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.security.User;

public class AradonRequest implements IAradonRequest{

	private Form form ;
	private Aradon aradon ;
	private ExecutorService es ;
	private String path ;
	private User user ;
	private Form headerForm ;
	private ChallengeResponse challengeResponse;
	
	private AradonRequest(Aradon aradon, ExecutorService es, String path, User user, Form form) {
		this.aradon = aradon ;
		this.es = es; 
		this.path = path ;
		this.challengeResponse = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, user.getIdentifier(), user.getSecret());
		this.user = user ;
		this.form = form ;
		this.headerForm = new Form() ;
	}

	public static AradonRequest create(Aradon aradon, ExecutorService es, String path, String id, String pwd) {
		String[] getPath = StringUtil.split(path, '?');
		if (getPath.length == 1) {
			return new AradonRequest(aradon, es, path, new User(id, pwd), new Form());
		} else {
			Form form = new Form(getPath[1], CharacterSet.UTF_8);
			return new AradonRequest(aradon, es, getPath[0], new User(id, pwd), form);
		}
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
	
	public Response handle(Method method){
		return aradon.handle(makeRequest(method)) ;
	}

	public <T> Future<T> handle(final Method method, final AsyncHttpHandler<T> ahandler){
		
		return es.submit(new Callable<T>() {
			public T call() throws Exception {
				final Request request = makeRequest(method);
				Response response = aradon.handle(request) ;
				
				if (response.getStatus().isServerError() || response.getStatus().isClientError()){
					ahandler.onError(request, response) ;
					return null;
				} else return ahandler.onCompleted(request, response) ;
			}
		}) ;
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

	public Representation multipart(Method method, Representation entity) {
		return aradon.handle(makeRequest(method, entity)).getEntity();
	}

	private Request makeRequest(Method method) {
		Request request = null;
		if (method == Method.GET || method == Method.DELETE) {
			request = new Request(method, getFullPath() + "?" + form.getQueryString()) ;
		} else {
			request = new Request(method, getFullPath());
			request.setEntity(form.getWebRepresentation());
		}

		setHeader(request) ;

		return request;
	}
	
	private void setHeader(Request request) {
		request.setChallengeResponse(challengeResponse);
		String cookieValue = headerForm.getFirstValue(HttpHeaders.COOKIE);
		if (StringUtil.isNotBlank(cookieValue)) {
			request.setCookies(new CookieSeries(new CookieReader(cookieValue).readValues()));
		}

		if (headerForm.size() > 0) request.getAttributes().put(RadonAttributeKey.ATTRIBUTE_HEADERS, headerForm);
	}
	
	private Request makeRequest(Method method, Representation entity) {
		Request request = new Request(method, getFullPath());
		request.setEntity(entity);
		setHeader(request) ;

		return request;
	}

	public String getFullPath() {
		return "riap://component" + (path.startsWith("/") ? "" : "/") + path;
	}

	public User getUser() {
		return user;
	}

	public IAradonRequest addHeader(String name, String value) {
		headerForm.add(name, value) ;
		return this;
	}

	public String getHost() {
		return "riap://component/" ;
	}

	public String getPath() {
		return path;
	}
	
	public <T> T handle(Method method, Object plainObject, Class<T> rtnClz) throws ResourceException {
		try {
			Request request = makeRequest(method);
			request.setEntity(new StringRepresentation(JsonParser.fromObject(plainObject).toString()));
			Representation repr = aradon.handle(request).getEntity() ;
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
			Representation repr = aradon.handle(request).getEntity();
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

}

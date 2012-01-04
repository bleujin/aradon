package net.ion.radon.client;

import net.ion.framework.util.StringUtil;
import net.ion.radon.core.RadonAttributeKey;

import org.eclipse.jetty.http.HttpHeaders;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.engine.header.CookieReader;
import org.restlet.engine.util.CookieSeries;
import org.restlet.representation.Representation;
import org.restlet.security.User;

public class BasicRequest implements IAradonRequest {

	private Form form;
	private AradonHttpClient aclient;
	private ChallengeResponse challengeResponse;
	private final String path;
	private final String fullPath;
	private User user;
	private Form headerForm;

	private BasicRequest(AradonHttpClient aclient, String path, String id, String pwd, Form form) {
		this.aclient = aclient;
		this.fullPath = aclient.getHostAddress() + path;
		this.path = path;
		this.challengeResponse = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, id, pwd.toCharArray());
		this.user = new User(challengeResponse.getIdentifier(), challengeResponse.getSecret());
		this.form = form;
		this.headerForm = new Form();
	}

	public final static BasicRequest create(AradonHttpClient client, String path, String id, String pwd) {
		String[] getPath = StringUtil.split(path, '?');
		if (getPath.length == 1) {
			return new BasicRequest(client, path, id, pwd, new Form());
		} else {
			Form form = new Form(getPath[1], CharacterSet.UTF_8);
			return new BasicRequest(client, getPath[0], id, pwd, form);
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
		return aclient.handle(makeRequest(method));
	}

	public Representation get() {
		return handle(makeRequest(Method.GET));
	}

	public Representation post() {
		return handle(makeRequest(Method.POST));
	}

	public Representation multipart(Method method, Representation entity) {
		return handle(makeRequest(method, entity));
	}

	public Representation delete() {
		return handle(makeRequest(Method.DELETE));
	}

	public Representation put() {
		return handle(makeRequest(Method.PUT));
	}

	private Request makeRequest(Method method) {
		Request request = null;
		if (method == Method.GET || method == Method.DELETE) {
			request = new Request(method, fullPath + "?" + form.getQueryString());
		} else {
			request = new Request(method, fullPath);
			request.setEntity(form.getWebRepresentation());
		}

		setHeader(request);

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
		Request request = new Request(method, fullPath);
		request.setEntity(entity);
		setHeader(request);

		return request;
	}

	private Representation handle(Request request) {
		Representation result = aclient.handleRequest(request);
		return result;
	}

	public String getFullPath() {
		return fullPath;
	}

	public User getUser() {
		return user;
	}

	public IAradonRequest addHeader(String name, String value) {
		headerForm.add(name, value);
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
}

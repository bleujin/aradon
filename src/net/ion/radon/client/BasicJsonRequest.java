package net.ion.radon.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.List;

import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.representation.JsonObjectRepresentation;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.UniformResource;
import org.restlet.security.User;
import org.restlet.service.ConverterService;

public class BasicJsonRequest implements IJsonRequest {

	private AradonHttpClient aclient;
	private ChallengeResponse challengeResponse;
	private String fullPath;
	private User user;
	private Form headerForm;

	private BasicJsonRequest(AradonHttpClient aclient, String path, String id, String pwd) {
		this.aclient = aclient;
		this.fullPath = aclient.getHostAddress() + path;
		this.challengeResponse = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, id, pwd.toCharArray());
		this.user = new User(challengeResponse.getIdentifier(), challengeResponse.getSecret());
	}

	public static BasicJsonRequest create(AradonHttpClient aclient, String path, String id, String pwd) {
		return new BasicJsonRequest(aclient, path, id, pwd);
	}

	public <V> V get(Class<V> resultClass) {
		V result = createResource(Method.GET, null, resultClass);
		return result;
	}

	public <V> List<V> list(Class<V> resultClass) {
		List<V> result = createResources(Method.GET, null, resultClass);
		return result;
	}

	public <T, V> V put(T arg, Class<V> clz) {
		V result = createResource(Method.PUT, arg, clz);
		return result;
	}

	public <T, V> V post(T arg, Class<V> clz) {
		return createResource(Method.POST, arg, clz);
	}

	public <T, V> V handle(Method method, T arg, Class<V> clz) {
		return createResource(method, arg, clz);
	}

	public <V> V delete(Class<V> clz) {
		return createResource(Method.DELETE, null, clz);
	}

	private <T, V> V createResource(Method method, T arg, Class<V> rtnClz) {
		Response response = handle(method, arg);
		try {
			Representation repr = response.getEntity();
			if (repr.getMediaType().equals(MediaType.APPLICATION_JSON) || repr.getMediaType().equals(MediaType.TEXT_ALL)) {
				String str = repr.getText();
				if (StringUtil.isBlank(str))
					return null;
				return JsonParser.fromString(str).getAsJsonObject().getAsObject(rtnClz);
			}
			return null;
		} catch (IOException e) {
			throw new ResourceException(response.getStatus(), e.getMessage());
		}
	}

	private <T, V> List<V> createResources(Method method, T arg, Class<V> rtnClz) {
		Response response = handle(method, arg);
		try {
			Representation repr = response.getEntity();
			if (repr.getMediaType().equals(MediaType.APPLICATION_JSON) || repr.getMediaType().equals(MediaType.TEXT_ALL)) {
				String str = repr.getText();
				if (StringUtil.isBlank(str))
					return null;
				return JsonParser.fromString(str).getAsJsonArray().asList(rtnClz);
			}
			return null;
		} catch (IOException e) {
			throw new ResourceException(response.getStatus(), e.getMessage());
		}
	}


	private <T> Response handle(Method method, T arg) {
		Request request = new Request(method, fullPath);
		request.getClientInfo().setUser(this.user);
		request.setChallengeResponse(new ChallengeResponse(ChallengeScheme.HTTP_BASIC, user.getIdentifier(), user.getSecret()));
		if (headerForm != null)
			request.getAttributes().put(RadonAttributeKey.ATTRIBUTE_HEADERS, headerForm);

		if (arg != null) {
			ClientResource resource = new ClientResource(aclient.getClient().getContext(), getFullPath());
			request.setEntity(toRepresentation(resource, arg, null));
		}
		try {
			Response response = aclient.getClient().handle(request);
			if (!response.getStatus().isSuccess()) {
				throw new ResourceException(response.getStatus());
			}
			return response;
		} finally {
			request.release();
		}
	}

	private String getFullPath() {
		return fullPath;
	}

	public BasicJsonRequest addHeader(String name, String value) {
		if (headerForm == null) {
			headerForm = new Form();
		}

		headerForm.add(name, value);
		return this;
	}

	private Representation toRepresentation(UniformResource resource, Object source, Variant target) {
		Representation result = null;
		if (source != null) {
			result = new JsonObjectRepresentation(JsonParser.fromObject(source));
		}
		return result;
	}

}

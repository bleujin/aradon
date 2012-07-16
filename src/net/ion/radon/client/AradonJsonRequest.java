package net.ion.radon.client;

import java.io.IOException;
import java.util.List;

import net.ion.framework.parse.gson.JsonElement;
import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.representation.JsonObjectRepresentation;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.UniformResource;
import org.restlet.security.User;

public class AradonJsonRequest implements IJsonRequest {

	private Aradon aradon;
	private String path;
	private User user;
	private Form tempHeaderForm;

	private AradonJsonRequest(Aradon aradon, String path, User user, Form form) {
		this.aradon = aradon;
		this.path = path;
		this.user = user;
	}

	public static IJsonRequest create(Aradon aradon, String path, String id, String pwd) {
		String[] getPath = StringUtil.split(path, '?');
		if (getPath.length == 1) {
			return new AradonJsonRequest(aradon, path, new User(id, pwd), new Form());
		} else {
			Form form = new Form(getPath[1], CharacterSet.UTF_8);
			return new AradonJsonRequest(aradon, path, new User(id, pwd), form);
		}
	}

	private <T> Request makeRequest(Method method, T arg) {
		Request request = new Request(method, getFullPath());
		ChallengeResponse challengeResponse = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, user.getIdentifier(), user.getSecret());
		request.setChallengeResponse(challengeResponse);
		HeaderUtil.setHeader(request, tempHeaderForm) ;

		if (arg != null) {
			ClientResource resource = new ClientResource(aradon.getContext(), getFullPath());
			request.setEntity(toRepresentation(resource, arg, null));
		}
		// request.getClientInfo().setUser(user) ;

		return request;
	}

	private String getFullPath() {
		return "riap://component" + (path.startsWith("/") ? "" : "/") + path;
	}

	public <V> V get(Class<V> resultClass) {
		return (V) handle(makeRequest(Method.GET, null), resultClass);
	}

	public <V> List<V> list(Class<V> resultClass) {
		return (List<V>) handle(makeRequest(Method.GET, null), resultClass);
	}

	public <T, V> V post(T arg, Class<V> resultClass) {
		return (V) handle(makeRequest(Method.POST, arg), resultClass);
	}

	public <V> V delete(Class<V> resultClass) {
		Request request = makeRequest(Method.DELETE, null);
		return (V) handle(request, resultClass);
	}

	private <V> Object handle(Request request, Class<? extends V> rtnClz) {
		Response response = aradon.handle(request);

		try {
			if (!response.getStatus().isSuccess())
				throw new ResourceException(response.getStatus(), response.toString());

			Representation repr = response.getEntity();
			if (repr == null) {
				return null; // or Unable to find a converter for this object
			} else if (repr.getMediaType().equals(MediaType.APPLICATION_JSON) || repr.getMediaType().equals(MediaType.TEXT_ALL)) {
				String str = repr.getText();
				if (StringUtil.isBlank(str))
					return null;
				JsonElement jsonEle = JsonParser.fromString(str);
				if (jsonEle.isJsonObject()) {
					return jsonEle.getAsJsonObject().getAsObject(rtnClz);
				} else if (jsonEle.isJsonArray()) {
					return jsonEle.getAsJsonArray().asList(rtnClz);
				} else if (jsonEle.isJsonPrimitive()){
					return jsonEle.getAsJsonPrimitive().getValue() ;
				} else {
					return null ;
				}
			} else {
				return null;
			}
		} catch (IOException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e.getMessage());
		} finally {
			request.release();
		}
	}

	public <T, V> V handle(Method method, T arg, Class<V> resultClass) {
		return (V) handle(makeRequest(method, arg), resultClass);
	}

	public <T, V> V put(T arg, Class<V> resultClass) {
		return (V) handle(makeRequest(Method.PUT, arg), resultClass);
	}

	public AradonJsonRequest addHeader(String name, String value) {
		if (tempHeaderForm == null) {
			tempHeaderForm = new Form();
		}

		tempHeaderForm.add(name, value);
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

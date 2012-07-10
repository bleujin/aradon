package net.ion.radon.client;

import java.io.IOException;

import net.ion.framework.util.StringUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.representation.JsonObjectRepresentation;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.UniformResource;
import org.restlet.security.User;
import org.restlet.service.ConverterService;

public class AradonSerialRequest implements ISerialRequest {

	private Aradon aradon;
	private String path;
	private User user;
	private Form headerForm ;

	private AradonSerialRequest(Aradon aradon, String path, User user, Form form) {
		this.aradon = aradon;
		this.path = path;
		this.user = user;
	}

	public static ISerialRequest create(Aradon aradon, String path, String id, String pwd) {
		String[] getPath = StringUtil.split(path, '?');
		if (getPath.length == 1) {
			return new AradonSerialRequest(aradon, path, new User(id, pwd), new Form());
		} else {
			Form form = new Form(getPath[1], CharacterSet.UTF_8);
			return new AradonSerialRequest(aradon, path, new User(id, pwd), form);
		}
	}

	private <T> Request makeRequest(Method method, T arg) {
		Request request = new Request(method, getFullPath());
		ChallengeResponse challengeResponse = new ChallengeResponse(ChallengeScheme.HTTP_BASIC, user.getIdentifier(), user.getSecret());
		request.setChallengeResponse(challengeResponse);
		if (headerForm != null) request.getAttributes().put(RadonAttributeKey.ATTRIBUTE_HEADERS, headerForm) ;
		
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

	public <V> V get(Class<? extends V> resultClass) {
		Request request = makeRequest(Method.GET, null);
		return handle(request, resultClass);
	}

	public <T, V> V post(T arg, Class<? extends V> resultClass) {
		Request request = makeRequest(Method.POST, arg);
		return handle(request, resultClass);
	}

	public <V> V delete(Class<? extends V> resultClass) {
		Request request = makeRequest(Method.DELETE, null);
		return handle(request, resultClass);
	}

	private <V> V handle(Request request, Class<? extends V> resultClass) {
		Response response = aradon.handle(request) ;
		if (!response.getStatus().isSuccess())
			throw new ResourceException(response.getStatus(), response.toString());

		try {
			Representation entity = response.getEntity();
			if (entity == null) {
				return null; // or Unable to find a converter for this object
			} else {
				Object resultObj = ((ObjectRepresentation) entity).getObject();
				return resultClass.cast(resultObj);
			}
		} catch (IOException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e.getMessage());
		}
	}

	public <T, V> V handle(Method method, T arg, Class<? extends V> resultClass) {
		Request request = makeRequest(method, arg);
		return handle(request, resultClass);
	}

	public <T, V> V put(T arg, Class<? extends V> resultClass) {
		Request request = makeRequest(Method.PUT, arg);
		return handle(request, resultClass);
	}

	public AradonSerialRequest addHeader(String name, String value) {
		if (headerForm == null){
			headerForm = new Form() ;
		}
		
		headerForm.add(name, value) ;
		return this;
	}
	
	private Representation toRepresentation(UniformResource resource, Object source, Variant target) {
		Representation result = null;
		if (source != null) {
			ConverterService cs = aradon.getConverterService();
			result = cs.toRepresentation(source, target, resource);
		}
		return result;
	}

}

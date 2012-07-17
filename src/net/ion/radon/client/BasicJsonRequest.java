package net.ion.radon.client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.util.StringUtil;
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
import org.restlet.resource.ResourceException;
import org.restlet.security.User;

public class BasicJsonRequest implements IJsonRequest {

	private AradonHttpClient aclient;
	private ChallengeResponse challengeResponse;
	private String fullPath;
	private User user;
	private Form tempHeaderForm;

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


	public <T, V> Future<V> asyncHandle(Method method, T arg, final Class<V> resultClass) {
		Request request = new Request(method, fullPath);
		request.getClientInfo().setUser(this.user);
		request.setChallengeResponse(new ChallengeResponse(ChallengeScheme.HTTP_BASIC, user.getIdentifier(), user.getSecret()));
		HeaderUtil.setHeader(request, tempHeaderForm);

		if (arg != null) {
			request.setEntity(toRepresentation(arg, null));
		}
		
		Future<V> future = aclient.asyncHandle(request, new AsyncHttpCompletionHandler<V>() {
			public V onCompleted(Request request, Response response) {
				Representation entity = null ;
				try {
					if (!response.getStatus().isSuccess()) {
						throw new ResourceException(response.getStatus());
					}
					entity = response.getEntity();
					if (entity == null) {
						return null;
					}
					if (entity.getMediaType().equals(MediaType.APPLICATION_JSON) || entity.getMediaType().equals(MediaType.TEXT_ALL)) {
						String jsonText = entity.getText();
						if (StringUtil.isBlank(jsonText))
							return null;
						return JsonParser.fromString(jsonText).getAsJsonObject().getAsObject(resultClass);
					} else {
						return null;
					}
				} catch (IOException ex) {
					return null;
				} finally {
					if (entity != null) entity.release() ;
				}
			}
		});
		
		return future;
	}
	
	public <V> V delete(Class<V> clz) {
		return createResource(Method.DELETE, null, clz);
	}

	private <T, V> V createResource(Method method, T arg, Class<V> rtnClz) {
		String str = handleText(method, arg);
		if (StringUtil.isBlank(str))
			return null;
		return JsonParser.fromString(str).getAsJsonObject().getAsObject(rtnClz);
	}

	private <T, V> List<V> createResources(Method method, T arg, Class<V> rtnClz) {
		String str = handleText(method, arg);
		if (StringUtil.isBlank(str))
			return null;
		return JsonParser.fromString(str).getAsJsonArray().asList(rtnClz);
	}

	private <T> String handleText(Method method, T arg) {
		Request request = new Request(method, fullPath);
		request.getClientInfo().setUser(this.user);
		request.setChallengeResponse(new ChallengeResponse(ChallengeScheme.HTTP_BASIC, user.getIdentifier(), user.getSecret()));
		HeaderUtil.setHeader(request, tempHeaderForm);

		if (arg != null) {
			request.setEntity(toRepresentation(arg, null));
		}
		Future<String> future = aclient.asyncHandle(request, new AsyncHttpCompletionHandler<String>() {
			public String onCompleted(Request request, Response response) {
				Representation entity = null ;
				try {
					if (!response.getStatus().isSuccess()) {
						throw new ResourceException(response.getStatus());
					}
					entity = response.getEntity();
					if (entity == null) {
						return null;
					}
					if (entity.getMediaType().equals(MediaType.APPLICATION_JSON) || entity.getMediaType().equals(MediaType.TEXT_ALL)) {
						return entity.getText();
					} else {
						return null;
					}
				} catch (IOException ex) {
					return null;
				} finally {
					if (entity != null) entity.release() ;
				}
			}
		});

		try {
			return future.get();
		} catch (InterruptedException e) {
			return null ;
		} catch (ExecutionException e) {
			return null ;
		}
	}

	public BasicJsonRequest addHeader(String name, String value) {
		if (tempHeaderForm == null) {
			tempHeaderForm = new Form();
		}

		tempHeaderForm.add(name, value);
		return this;
	}

	private Representation toRepresentation(Object source, Variant target) {
		if (source != null) {
			return new JsonObjectRepresentation(JsonParser.fromObject(source));
		}
		return null;
	}


}

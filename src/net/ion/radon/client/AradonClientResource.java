package net.ion.radon.client;

import java.util.List;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

public class AradonClientResource extends ClientResource {

	public AradonClientResource(Context context, Request request, Response response) {
		super(context, request, response);
	}

	public <T, C> List<C> list(T entity, Class<? extends C> clz) throws ResourceException {
		return handle(Method.LIST, entity, List.class);
	}

	public <T, S> S execute(T entity, Class<? extends S> clz) throws ResourceException {
		return handle(Method.EXECUTE, entity, clz);
	}
}

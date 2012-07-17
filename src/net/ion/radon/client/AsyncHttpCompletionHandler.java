package net.ion.radon.client;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.ResourceException;

public abstract class AsyncHttpCompletionHandler<T> implements AsyncHttpHandler<T> {

	public void onError(Request request, Response response) {
		throw new ResourceException(response.getStatus(), response.getEntityAsText()) ;
	}
}

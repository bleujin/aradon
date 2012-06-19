package net.ion.radon.client;

import org.restlet.Request;
import org.restlet.Response;

public interface AsyncHttpHandler<T> {
	public <T> T onCompleted(Request request, Response response) ;
	public void onError(Request request, Response response) ;
}

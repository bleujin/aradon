package net.ion.nradon.handler;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;

public class NotFoundHttpHandler extends AbstractHttpHandler {

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) {
		response.status(404).end();
	}

}

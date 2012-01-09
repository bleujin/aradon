package net.ion.nradon.handler.aradon;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.radon.core.Aradon;

public class AradonHandler implements HttpHandler {

	private NradonClient client;
	public AradonHandler(Aradon aradon) {
		this.client = NradonClient.create(aradon);
	}

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		client.handle(request, response);
	}

}


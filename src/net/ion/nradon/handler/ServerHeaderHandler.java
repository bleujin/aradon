package net.ion.nradon.handler;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;

/**
 * Handler that sets the HTTP 'Server' response header.
 */
public class ServerHeaderHandler extends AbstractHttpHandler {

	private final String value;

	/**
	 * Value to set for HTTP Server header, or null to ensure the header is blank.
	 */
	public ServerHeaderHandler(String value) {
		this.value = value;
	}

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		response.header("Server", value);
		control.nextHandler();
	}
}

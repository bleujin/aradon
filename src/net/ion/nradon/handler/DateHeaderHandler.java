package net.ion.nradon.handler;

import java.util.Date;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;

import org.jboss.netty.handler.codec.http.HttpHeaders;

/**
 * Handler that sets the HTTP 'Server' response header.
 */
public class DateHeaderHandler extends AbstractHttpHandler {

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		if (!response.containsHeader(HttpHeaders.Names.DATE)) {
			response.header(HttpHeaders.Names.DATE, new Date());
		}
		control.nextHandler();
	}
}

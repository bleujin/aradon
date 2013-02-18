package net.ion.nradon.handler;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.handler.event.ServerEvent.EventType;

public class PathMatchHandler extends AbstractHttpHandler {

	private final Pattern pathPattern;
	private final HttpHandler httpHandler;

	public PathMatchHandler(Pattern pathPattern, HttpHandler httpHandler) {
		this.pathPattern = pathPattern;
		this.httpHandler = httpHandler;
	}

	public PathMatchHandler(String path, HttpHandler httpHandler) {
		this(Pattern.compile(path), httpHandler);
	}

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		String path = URI.create(request.uri()).getPath();
		Matcher matcher = pathPattern.matcher(path);
		if (matcher.matches()) {
			httpHandler.handleHttpRequest(request, response, control);
		} else {
			control.nextHandler();
		}
	}
	
	public void onEvent(EventType etype, Radon webserver) {
		httpHandler.onEvent(etype, webserver) ;
	}
	
	public String toString(){
		return "pattern:" + pathPattern + ", handler:" + httpHandler ;  
	}

	public int order() {
		return httpHandler.order();
	}
}

package net.ion.nradon.handler;

import java.net.URI;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.handler.event.ServerEvent.EventType;
import net.ion.radon.util.uriparser.URIPattern;
import net.ion.radon.util.uriparser.URIResolveResult;
import net.ion.radon.util.uriparser.URIResolver;

public class URIPathMatchHandler extends AbstractHttpHandler {

	private final URIPattern uriPattern;
	private final HttpHandler httpHandler;

	private URIPathMatchHandler(URIPattern uriPattern, HttpHandler httpHandler) {
		this.uriPattern = uriPattern;
		this.httpHandler = httpHandler;
	}

	public URIPathMatchHandler(String template, HttpHandler httpHandler) {
		this(new URIPattern(template), httpHandler);
	}

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		String path = URI.create(request.uri()).getPath();
		if (uriPattern.match(path)) {
			URIResolveResult result = new URIResolver(path).resolve(uriPattern);
			for(String name : result.names()){
				request.data(name, result.get(name)) ;
			}
			
			httpHandler.handleHttpRequest(request, response, control);
		} else {
			control.nextHandler();
		}
	}
	
	public void onEvent(EventType etype, Radon webserver) {
		httpHandler.onEvent(etype, webserver) ;
	}
	
	public String toString(){
		return "pattern:" + uriPattern + ", handler:" + httpHandler ;  
	}
}

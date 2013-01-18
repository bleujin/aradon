package net.ion.nradon;

import java.util.concurrent.Future;

import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.HttpToEventSourceHandler;
import net.ion.nradon.handler.HttpToWebSocketHandler;
import net.ion.nradon.handler.URIPathMatchHandler;

public abstract class Radon implements Endpoint<Radon>{

	public abstract Future<Radon> start() ;

	public abstract Future<Radon> stop() ;

	public abstract RadonConfiguration getConfig() ;
	
	public Radon add(HttpHandler handler) {
		getConfig().add(handler) ;
		return this ;
	}

	public Radon add(String path, HttpHandler handler) {
		return add(new URIPathMatchHandler(path, handler));
	}

	public Radon add(String path, WebSocketHandler handler) {
		return add(path, new HttpToWebSocketHandler(handler));
	}

	public Radon add(String path, EventSourceHandler handler) {
		return add(path, new HttpToEventSourceHandler(handler));
	}
	
	
	
//	public abstract URI getUri();
//
//	public abstract int getPort();
//
//	public abstract Executor getExecutor();
}

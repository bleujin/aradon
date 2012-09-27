package net.ion.nradon.filter;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.WebSocketConnection;
import net.ion.radon.core.IService;

public class DefaultXRadonFilter implements XRadonFilter{

	public void esClose(IService iservice, EventSourceConnection conn) {
		
	}

	public void esOpen(IService iservice, EventSourceConnection conn) {
		
	}

	public void httpEnd(IService iservice, HttpRequest hreq, HttpResponse hres) {
		
	}

	public void httpStart(IService iservice, HttpRequest hreq, HttpResponse hres) {
		
	}

	public void init(IService service) {
		
	}

	public void wsClose(IService iservice, WebSocketConnection conn) {
		
	}

	public void wsInbound(IService iservice, WebSocketConnection conn, String data) {
		
	}

	public void wsInbound(IService iservice, WebSocketConnection conn, byte[] message) {
		
	}


	public void wsOpen(IService iservice, WebSocketConnection conn) {
		
	}

	public void wsInboundPong(IService iservice, WebSocketConnection conn, String message) {
		
	}


}

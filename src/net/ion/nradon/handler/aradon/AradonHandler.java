package net.ion.nradon.handler.aradon;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.handler.event.ServerEvent.EventType;
import net.ion.radon.core.Aradon;

public class AradonHandler implements HttpHandler {

	private Aradon aradon;
	private NradonClient client;
	private boolean ignoreEvent = false ;
	private AradonHandler(Aradon aradon) {
		this.aradon = aradon;
		this.client = NradonClient.create(aradon);
	}

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		
		client.handle(request, response);
	}

	public static AradonHandler create(Aradon aradon) {
		return new AradonHandler(aradon);
	}

	public void onEvent(EventType etype, Radon wserver) {
		if (ignoreEvent) return ;
		try {
			if (etype == EventType.START) {
				if (! aradon.isStarted()) aradon.start();
			} else if (etype == EventType.STOP) {
				if (aradon.isStarted()) aradon.stop();
			}
		} catch (Exception ignore) {
			ignore.printStackTrace();
		}
	}

	public void ignoreEvent(boolean ignoreEvent) {
		this.ignoreEvent = ignoreEvent ;
	}
}

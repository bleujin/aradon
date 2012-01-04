package net.ion.nradon.wrapper;

import java.util.concurrent.Executor;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;

public class HttpControlWrapper implements HttpControl {

	private HttpControl control;

	public HttpControlWrapper(HttpControl control) {
		this.control = control;
	}

	public HttpControl underlyingControl() {
		return control;
	}

	public HttpControlWrapper underlyingControl(HttpControl control) {
		this.control = control;
		return this;
	}

	public HttpControl originalControl() {
		if (control instanceof HttpControlWrapper) {
			HttpControlWrapper wrapper = (HttpControlWrapper) control;
			return wrapper.originalControl();
		} else {
			return control;
		}
	}

	public void nextHandler() {
		control.nextHandler();
	}

	public void nextHandler(HttpRequest request, HttpResponse response) {
		control.nextHandler(request, response);
	}

	public void nextHandler(HttpRequest request, HttpResponse response, HttpControl control) {
		control.nextHandler(request, response, control);
	}

	public WebSocketConnection upgradeToWebSocketConnection(WebSocketHandler handler) {
		return control.upgradeToWebSocketConnection(handler);
	}

	public WebSocketConnection webSocketConnection() {
		return control.webSocketConnection();
	}

	public EventSourceConnection upgradeToEventSourceConnection(EventSourceHandler handler) {
		return control.upgradeToEventSourceConnection(handler);
	}

	public EventSourceConnection eventSourceConnection() {
		return control.eventSourceConnection();
	}

	public Executor handlerExecutor() {
		return control.handlerExecutor();
	}

	public void execute(Runnable command) {
		control.execute(command);
	}
}

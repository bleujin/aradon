package net.ion.nradon.stub;

import java.util.concurrent.Executor;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;

public class StubHttpControl implements HttpControl {

	private HttpRequest request;
	private HttpResponse response;
	private WebSocketHandler webSocketHandler;
	private WebSocketConnection webSocketConnection;

	public StubHttpControl() {
	}

	public StubHttpControl(HttpRequest request, HttpResponse response) {
		this.request = request;
		this.response = response;
	}

	public StubHttpControl(WebSocketConnection connection) {
		this.webSocketConnection = connection;
	}

	public StubHttpControl(HttpRequest request, HttpResponse response, WebSocketConnection connection) {
		this.request = request;
		this.response = response;
		this.webSocketConnection = connection;
	}

	public HttpRequest request() {
		return request;
	}

	public HttpResponse response() {
		return response;
	}

	public StubHttpControl request(HttpRequest request) {
		this.request = request;
		return this;
	}

	public StubHttpControl response(HttpResponse response) {
		this.response = response;
		return this;
	}

	public void nextHandler() {
		nextHandler(request, response, this);
	}

	public void nextHandler(HttpRequest request, HttpResponse response) {
		nextHandler(request, response, this);
	}

	public void nextHandler(HttpRequest request, HttpResponse response, HttpControl control) {
		response.status(404).end();
	}

	public WebSocketConnection upgradeToWebSocketConnection(WebSocketHandler handler) {
		this.webSocketHandler = handler;
		return this.webSocketConnection;
	}

	public WebSocketConnection webSocketConnection() {
		return this.webSocketConnection;
	}

	public EventSourceConnection upgradeToEventSourceConnection(EventSourceHandler handler) {
		throw new UnsupportedOperationException();
		// this.webSocketHandler = handler;
		// return webSocketConnection;
	}

	public EventSourceConnection eventSourceConnection() {
		throw new UnsupportedOperationException();
		// return this.webSocketConnection;
	}

	public StubHttpControl webSocketConnection(WebSocketConnection connection) {
		this.webSocketConnection = connection;
		return this;
	}

	public WebSocketHandler webSocketHandler() {
		return webSocketHandler;
	}

	public Executor handlerExecutor() {
		return this;
	}

	public void execute(Runnable command) {
		command.run();
	}
}

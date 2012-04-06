package net.ion.nradon.handler.logging;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.handler.AbstractHttpHandler;
import net.ion.nradon.wrapper.HttpControlWrapper;
import net.ion.nradon.wrapper.HttpResponseWrapper;

public class LoggingHandler extends AbstractHttpHandler {

	private final LogSink logSink;

	public LoggingHandler(LogSink logSink) {
		this.logSink = logSink;
	}

	public LogSink logSink() {
		return logSink;
	}

	public void handleHttpRequest(final HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		logSink.httpStart(request);

		HttpResponseWrapper responseWrapper = new HttpResponseWrapper(response) {
			@Override
			public HttpResponseWrapper end() {
				logSink.httpEnd(request);
				return super.end();
			}

			@Override
			public HttpResponseWrapper error(Throwable error) {
				logSink.httpEnd(request);
				logSink.error(request, error);
				return super.error(error);
			}
		};

		HttpControlWrapper controlWrapper = new HttpControlWrapper(control) {

			private LoggingWebSocketConnection loggingWebSocketConnection;
			private LoggingEventSourceConnection loggingEventSourceConnection;

			@Override
			public WebSocketConnection webSocketConnection() {
				return loggingWebSocketConnection;
			}

			@Override
			public WebSocketConnection upgradeToWebSocketConnection(WebSocketHandler handler) {
				loggingWebSocketConnection = new LoggingWebSocketConnection(logSink, super.webSocketConnection());
				return super.upgradeToWebSocketConnection(new LoggingWebSocketHandler(logSink, loggingWebSocketConnection, handler));
			}

			@Override
			public EventSourceConnection eventSourceConnection() {
				return loggingEventSourceConnection;
			}

			@Override
			public EventSourceConnection upgradeToEventSourceConnection(EventSourceHandler handler) {
				loggingEventSourceConnection = new LoggingEventSourceConnection(logSink, super.eventSourceConnection());
				return super.upgradeToEventSourceConnection(new LoggingEventSourceHandler(logSink, loggingEventSourceConnection, handler));
			}
		};
		control.nextHandler(request, responseWrapper, controlWrapper);
	}

}

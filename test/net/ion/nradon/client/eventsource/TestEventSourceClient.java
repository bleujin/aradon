package net.ion.nradon.client.eventsource;

import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import net.ion.framework.util.Debug;
import net.ion.framework.util.TimeoutThread;
import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceMessage;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.AbstractHttpHandler;
import net.ion.nradon.handler.event.ServerEvent.EventType;

import org.junit.Test;

public class TestEventSourceClient {

	@Test
	public void testTimeServer() throws Exception {

		Runnable serverRun = new Runnable() {
			public void run() {
				ExecutorService webThread = newSingleThreadExecutor();
				try {
					TimeHandler timeHandler = new TimeHandler(webThread);
					Radon ws = RadonConfiguration.newBuilder(webThread, 8090).add("/", new HtmlHandler()).add("/es", timeHandler).startRadon();
					Debug.debug("Server running on http://localhost:8090");
					timeHandler.start();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				} catch (ExecutionException ex) {
					ex.printStackTrace();
				}
			}
		};

		Thread serverThread = new Thread(serverRun);
		serverThread.start();

		EventSource es = new EventSource(URI.create("http://localhost:8090/es"), new EventSourceHandler() {
			public void onConnect() {
				Debug.debug("CONNECTED");
			}

			public void onMessage(String event, MessageEvent message) {
				Debug.line("event = " + event + ", message = " + message);
			}

			public void onError(Throwable t) {
				Debug.debug("ERROR");
				t.printStackTrace();
			}
		});

		es.connect();
		TimeoutThread.createWithSec(5).startNJoin() ;

		serverThread.interrupt() ;

	}

	private static class TimeHandler implements net.ion.nradon.EventSourceHandler {
		private Set<EventSourceConnection> connections = new HashSet<EventSourceConnection>();
		private final ExecutorService webThread;

		public TimeHandler(ExecutorService webThread) {
			this.webThread = webThread;
		}

		private void broadcast(EventSourceMessage message) {
			for (EventSourceConnection connection : connections) {
				Debug.debug(message.build());
				connection.send(message);
			}
		}

		public void onOpen(EventSourceConnection connection) throws Exception {
			Debug.debug("OPEN - HEADERS = " + connection.httpRequest().allHeaders());
			connections.add(connection);
		}

		public void onClose(EventSourceConnection connection) throws Exception {
			Debug.debug("DISCONNECTED");
			connections.remove(connection);
		}

		public void start() throws InterruptedException, ExecutionException {
			while (true) {
				sleep(1000);
				webThread.submit(new Runnable() {
					public void run() {
						Date date = new Date();
						EventSourceMessage message = new EventSourceMessage().data(date.toString()).id(date.getTime()) ; 
						// Chrome/FF doesn't fire events if the event field is set (?) .event("event-" + date.getTime())
						broadcast(message);
					}
				}).get();
			}
		}
	}

	private static class HtmlHandler extends AbstractHttpHandler {
		public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
			response.header("Content-Type", "text/html").charset(Charset.forName("UTF-8")).content(
					"" + "<!DOCTYPE html>\n" + "<html>\n" 
					+ "  <head>\n" 
					+ "    <script>\n" 
					+ "      function logText(msg) {\n" + "        var textArea = document.getElementById('log');\n" 
					+ "        textArea.value = textArea.value + msg + '\\n';\n"
					+ "        textArea.scrollTop = textArea.scrollHeight; // scroll into view\n" 
					+ "      }\n\n" 
					+ "      window.onload = function() {\n" 
					+ "        var es = new EventSource(document.location + 'es');\n" 
					+ "        es.onopen = function() {\n"
					+ "          logText('OPEN');\n" 
					+ "        };\n" 
					+ "        es.onmessage = function(e) {\n" 
					+ "          logText('MESSAGE:' + e.data);\n" 
					+ "        };\n" 
					+ "        es.onerror = function(e) {\n" 
					+ "          logText('ERROR');\n" 
					+ "        };\n" 
					+ "      };\n"
					+ "    </script>\n" + "  </head>\n" 
					+ "  <body>\n" 
					+ "    <textarea id=\"log\" rows=\"40\" cols=\"70\"></textarea>\n" + "  </body>\n" + "</html>")
					.end();
		}

		public void onEvent(EventType event, Radon wserver) {

		}
	}
}

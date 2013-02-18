package net.ion.nradon.handler;

import static net.ion.nradon.testutil.HttpClient.contents;
import static net.ion.nradon.testutil.HttpClient.httpGet;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import net.ion.framework.util.Debug;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.ajax.EchoWebSocketResource;
import net.ion.nradon.client.websocket.IResponseMessageHandler;
import net.ion.nradon.client.websocket.WebSocketClient;
import net.ion.nradon.filter.XRadonFilter;
import net.ion.nradon.let.IServiceLet;
import net.ion.nradon.netty.codec.http.websocketx.TextWebSocketFrame;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.IService;
import net.ion.radon.core.annotation.PathParam;
import net.ion.radon.core.config.ConfigurationBuilder;

import org.junit.After;
import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.Get;

public class TestFromAradon {
	private Radon radon = null;

	@After
	public void die() throws IOException, InterruptedException, ExecutionException {
		if (radon != null)
			radon.stop().get();
	}

	@Test
	public void complexPath() throws Exception {
		final Aradon aradon = Aradon.create(ConfigurationBuilder.newBuilder().aradon().sections().restSection("aradon").path("hello").addUrlPattern("/hello/{name}").handler(SimpleLet.class).build());
		this.radon = aradon.toRadon(59504).add("/tomayto", new AliasHandler("/tomato")).add("/tomato", new StringHttpHandler("text/plain", "body")).start().get();

		assertEquals("body", contents(httpGet(radon, "/tomayto")));
		assertEquals("hi bleujin", contents(httpGet(radon, "/aradon/hello/bleujin")));

		AradonClient ac = AradonClientFactory.create("http://localhost:59504");
		assertEquals("body", ac.createRequest("/tomayto").handle(Method.GET).getEntityAsText());
		assertEquals("hi bleujin", ac.createRequest("/aradon/hello/bleujin").handle(Method.GET).getEntityAsText());
		assertEquals("hi bleujin", AradonClientFactory.create("http://localhost:59504/").createRequest("/aradon/hello/bleujin").handle(Method.GET).getEntityAsText());
	}

	@Test
	public void uriPath() throws Exception {
		this.radon = Aradon.create().toRadon(59504).add("/tomayto", new AliasHandler("/tomato/movemove")).add("/tomato/{num}*", new SimpleHttpHandler("text/plain", "num")).start().get();

		AradonClient ac = AradonClientFactory.create("http://localhost:59504");
		final Response response = ac.createRequest("/tomayto").handle(Method.GET);
		assertEquals("movemove", response.getEntityAsText());
	}

	@Test
	public void uriPath2() throws Exception {
		final Aradon aradon = Aradon.create(ConfigurationBuilder.newBuilder().aradon().sections().restSection("").path("hello").addUrlPattern("/hello/{name}").handler(SimpleLet.class).build());
		this.radon = aradon.toRadon(59504).start().get();

		AradonClient ac = AradonClientFactory.create("http://localhost:59504");
		assertEquals("hi bleujin", ac.createRequest("/hello/bleujin").handle(Method.GET).getEntityAsText());
	}

	@Test
	public void uriWebSocket() throws Exception {
		final Aradon aradon = Aradon.create(ConfigurationBuilder.newBuilder().aradon().sections()
					.restSection("").path("hello").addUrlPattern("/hello/{name}").handler(SimpleLet.class)
					.restSection("a").wspath("broadcast").addUrlPattern("/{p1}/{p2}").handler(EchoWebSocketResource.class).build());

		this.radon = aradon.toRadon(8080).start().get();

		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<String> text = new AtomicReference<String>("");
		
		WebSocketClient client = WebSocketClient.create(new IResponseMessageHandler.OnTextMessageHander() {
			public void onMessage(TextWebSocketFrame tframe) {
				text.set(tframe.getText());
				latch.countDown() ;
			}
		});
		try {
			client.connect(new URI("ws://127.0.0.1:8080/a/b/c?name=bleujin"));
			client.sendMessage("Hi bleujin");
			latch.await() ;

			assertEquals("Hi bleujin", text.get()) ;
			
			EchoWebSocketResource echo = (EchoWebSocketResource) aradon.getChildService("a").wspath("broadcast").websocketResource();
			WebSocketConnection conn = echo.getConnections().get(0) ;
			
			assertEquals("b", conn.data("p1")) ;
			assertEquals("bleujin", conn.httpRequest().queryParam("name")) ;
		} finally {
			client.disconnect();
		}
	}
	
	@Test
	public void filter() throws Exception {
		final Aradon aradon = Aradon.create(ConfigurationBuilder.newBuilder().aradon().sections()
				.restSection("").path("hello").addUrlPattern("/hello/{name}").handler(SimpleLet.class).parentSection().addFilter(new XRadonFilter.DefaultFilter(){
					public void httpEnd(IService iservice, HttpRequest hreq, HttpResponse hres) {
						Debug.line("end") ;
					}
					public void httpStart(IService iservice, HttpRequest hreq, HttpResponse hres) {
						Debug.line("end") ;
					}
				}).build()) ;
		
//		final Aradon aradon = Aradon.create(ConfigurationBuilder.newBuilder().aradon().sections()
//				.restSection("").path("hello").addUrlPattern("/hello/{name}").handler(SimpleLet.class).parentSection().addPreFilter(new IRadonFilter(){
//					@Override
//					public IFilterResult afterHandle(IService iservice, Request request, Response response) {
//						Debug.line("end") ;
//						return IFilterResult.CONTINUE_RESULT;
//					}
//					@Override
//					public IFilterResult preHandle(IService iservice, Request request, Response response) {
//						Debug.line("pre") ;
//						return IFilterResult.CONTINUE_RESULT;
//					}
//					
//				}).build()) ;

		this.radon = aradon.toRadon(8080).start().get();
		AradonClient ac = AradonClientFactory.create("http://localhost:8080");
		Response res = ac.createRequest("/hello/bleujin").handle(Method.GET);
		
	}
	
	
	

	// context... let... filter...

}

class SimpleLet implements IServiceLet {

	@Get
	public String sayHello(@PathParam("name") String name) {
		return "hi " + name;
	}
}

class SimpleHttpHandler extends AbstractHttpHandler {

	private final String contentType;
	private final String pathAttr;
	private final Charset charset;

	public SimpleHttpHandler(String contentType, String pathAttr) {
		this(contentType, pathAttr, Charset.forName("UTF-8"));
	}

	public SimpleHttpHandler(String contentType, String pathAttr, Charset charset) {
		this.contentType = contentType;
		this.charset = charset;
		this.pathAttr = pathAttr;
	}

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) {
		response.charset(charset).header("Content-Type", contentType + "; charset=" + charset.name()).header("Content-Length", pathAttr.length()).content(request.data(pathAttr).toString()).end();
	}

}

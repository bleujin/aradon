package net.ion.nradon.handler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.stub.StubHttpControl;
import net.ion.nradon.stub.StubHttpRequest;
import net.ion.nradon.stub.StubHttpResponse;

import org.junit.Assert;
import org.junit.Test;

public class TestURIPathMatchHander {

	
	@Test
	public void simplePattern() throws Exception {
		HttpHandler handler = mock(HttpHandler.class);
		URIPathMatchHandler pmh = new URIPathMatchHandler("/users/{user}", handler);

		HttpRequest req = new StubHttpRequest("http://host.com:8080/users/bleujin");
		HttpResponse res = new StubHttpResponse();
		HttpControl ctl = new StubHttpControl();

		pmh.handleHttpRequest(req, res, ctl);
		verify(handler).handleHttpRequest(req, res, ctl);
	
		Assert.assertEquals("bleujin", req.data("user")) ;
	}
	
	@Test
	public void asterkPattern() throws Exception {
		HttpHandler handler = mock(HttpHandler.class);
		URIPathMatchHandler pmh = new URIPathMatchHandler("/users/{user}*", handler);

		HttpRequest req = new StubHttpRequest("http://host.com:8080/users/bleujin/hero/jin");
		HttpResponse res = new StubHttpResponse();
		HttpControl ctl = new StubHttpControl();

		pmh.handleHttpRequest(req, res, ctl);
		verify(handler).handleHttpRequest(req, res, ctl);
	
		Assert.assertEquals("bleujin", req.data("user")) ;
		Assert.assertEquals("/hero/jin", req.data("*")) ;
	}
	

	
	
	
	
	@Test
	public void matchesRequestWithFullUri() throws Exception {
		HttpHandler handler = mock(HttpHandler.class);
		URIPathMatchHandler pmh = new URIPathMatchHandler("/hello", handler);

		HttpRequest req = new StubHttpRequest("http://host.com:8080/hello");
		HttpResponse res = new StubHttpResponse();
		HttpControl ctl = new StubHttpControl();

		pmh.handleHttpRequest(req, res, ctl);
		verify(handler).handleHttpRequest(req, res, ctl);
	}

	@Test
	public void matchesRequestWithPathOnly() throws Exception {
		HttpHandler handler = mock(HttpHandler.class);
		URIPathMatchHandler pmh = new URIPathMatchHandler("/hello", handler);

		HttpRequest req = new StubHttpRequest("/hello");
		HttpResponse res = new StubHttpResponse();
		HttpControl ctl = new StubHttpControl();

		pmh.handleHttpRequest(req, res, ctl);
		verify(handler).handleHttpRequest(req, res, ctl);
	}

	@Test
	public void handsOffWhenNoMatch() throws Exception {
		HttpHandler handler = mock(HttpHandler.class);
		URIPathMatchHandler pmh = new URIPathMatchHandler("/hello", handler);

		HttpRequest req = new StubHttpRequest("http://hello.com:8080/wtf");
		HttpResponse res = new StubHttpResponse();
		HttpControl ctl = mock(HttpControl.class);

		pmh.handleHttpRequest(req, res, ctl);

		verifyZeroInteractions(handler);
		verify(ctl).nextHandler();
	}
	
	

}

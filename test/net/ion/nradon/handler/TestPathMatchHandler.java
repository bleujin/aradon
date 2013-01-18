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

import org.junit.Test;

@Deprecated
public class TestPathMatchHandler {
    @Test
    public void matchesRequestWithFullUri() throws Exception {
        HttpHandler handler = mock(HttpHandler.class);
        PathMatchHandler pmh = new PathMatchHandler("/hello", handler);

        HttpRequest req = new StubHttpRequest("http://host.com:8080/hello");
        HttpResponse res = new StubHttpResponse();
        HttpControl ctl = new StubHttpControl();

        pmh.handleHttpRequest(req, res, ctl);
        verify(handler).handleHttpRequest(req, res, ctl);
    }

    @Test
    public void matchesRequestWithPathOnly() throws Exception {
        HttpHandler handler = mock(HttpHandler.class);
        PathMatchHandler pmh = new PathMatchHandler("/hello", handler);

        HttpRequest req = new StubHttpRequest("/hello");
        HttpResponse res = new StubHttpResponse();
        HttpControl ctl = new StubHttpControl();

        pmh.handleHttpRequest(req, res, ctl);
        verify(handler).handleHttpRequest(req, res, ctl);
    }

    @Test
    public void handsOffWhenNoMatch() throws Exception {
        HttpHandler handler = mock(HttpHandler.class);
        PathMatchHandler pmh = new PathMatchHandler("/hello", handler);

        HttpRequest req = new StubHttpRequest("http://hello.com:8080/wtf");
        HttpResponse res = new StubHttpResponse();
        HttpControl ctl = mock(HttpControl.class);

        pmh.handleHttpRequest(req, res, ctl);

        verifyZeroInteractions(handler);
        verify(ctl).nextHandler();
    }
}


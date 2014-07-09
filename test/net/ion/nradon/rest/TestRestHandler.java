package net.ion.nradon.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import net.ion.nradon.HttpHandler;
import net.ion.nradon.stub.StubHttpControl;
import net.ion.nradon.stub.StubHttpRequest;
import net.ion.nradon.stub.StubHttpResponse;

import org.junit.Test;
public class TestRestHandler {

    private HttpHandler handler;

    @Test
    public void respondWithExplicitlyResources() throws Exception {
        handler = new Rest311Handler(new HelloResource());
        StubHttpResponse response = handle(request("/hello/world").method("GET"));
        assertReturnedWithStatus(200, response);
        assertEquals("Hello world", response.contentsString());
    }

    @Test
    public void respondsWithClz() throws Exception {
        handler = new Rest311Handler(HelloResource.class);
        StubHttpResponse response = handle(request("/hello/bleujin").method("GET"));
        assertReturnedWithStatus(200, response);
        assertEquals("Hello bleujin", response.contentsString());
    }

    @Test
    public void respondsWithNotAllowedPostMethod() throws Exception {
        handler = new Rest311Handler(getClass());
        StubHttpResponse response = handle(request("/hello").method("POST"));
        assertEquals("", response.contentsString());
        assertReturnedWithStatus(405, response);
    }

    @Test
    public void respondsWith404() throws Exception {
        handler = new Rest311Handler(getClass());
        StubHttpResponse response = handle(request("/nuffink").method("GET"));
        assertEquals("", response.contentsString());
        assertReturnedWithStatus(404, response);
    }

    private StubHttpResponse handle(StubHttpRequest request) throws Exception {
        StubHttpResponse response = new StubHttpResponse();
        handler.handleHttpRequest(request, response, new StubHttpControl(request, response));
        return response;
    }

    private StubHttpRequest request(String uri) {
        return new StubHttpRequest(uri);
    }

    private void assertReturnedWithStatus(int expectedStatus, StubHttpResponse response) {
        assertEquals(expectedStatus, response.status());
        assertTrue(response.ended());
        assertNull(response.error());
    }

    
    @Path("/hello")
    public static class HelloResource {
        @GET
        @Path("{name}")
        public String hello(@PathParam("name") final String name) {
            return "Hello " + name;
        }
    }
}





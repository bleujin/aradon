package net.ion.nradon.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.stub.StubHttpControl;
import net.ion.nradon.stub.StubHttpRequest;
import net.ion.nradon.stub.StubHttpResponse;

public class TestBaseRest {

    protected HttpHandler handler;

    protected StubHttpResponse handle(StubHttpRequest request) throws Exception {
        StubHttpResponse response = new StubHttpResponse();
        handler.handleHttpRequest(request, response, new StubHttpControl(request, response));
        return response;
    }

    protected StubHttpRequest request(String uri) {
        return new StubHttpRequest(uri);
    }

    protected void assertReturnedWithStatus(int expectedStatus, StubHttpResponse response) {
        assertEquals(expectedStatus, response.status());
        assertTrue(response.ended());
        assertNull(response.error());
    }

}

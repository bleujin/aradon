package net.ion.nradon.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import net.ion.nradon.HttpRequest;

import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.jboss.resteasy.specimpl.UriInfoImpl;
import org.jboss.resteasy.spi.AsynchronousResponse;
import org.jboss.resteasy.util.HttpRequestImpl;

public class RestRequest extends HttpRequestImpl {
    public RestRequest(InputStream inputStream, HttpHeaders httpHeaders, String httpMethod, UriInfo uri) {
        super(inputStream, httpHeaders, httpMethod, uri);
    }

    public void setInputStream(InputStream stream) {
        throw new UnsupportedOperationException();
    }

    public Object getAttribute(String attribute) {
        throw new UnsupportedOperationException();
    }

    public void setAttribute(String name, Object value) {
    }

    public void removeAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    public AsynchronousResponse createAsynchronousResponse(long suspendTimeout) {
        throw new UnsupportedOperationException();
    }

    public AsynchronousResponse getAsynchronousResponse() {
        throw new UnsupportedOperationException();
    }

    public void initialRequestThreadFinished() {
        throw new UnsupportedOperationException();
    }

    public static RestRequest wrap(final HttpRequest request) throws UnsupportedEncodingException {
        HttpHeaders headers = new RestRequestHeaders(request);

        // org.jboss.resteasy.plugins.server.servlet.ServletUtil is doing this differently (much more complex - not sure why)
        URI uri = URI.create(request.uri());

        UriInfo uriInfo = new UriInfoImpl(uri, uri, uri.getPath(), uri.getQuery(), PathSegmentImpl.parseSegments(uri.getPath()));
        String body = request.body();
        InputStream in = body == null ? new ByteArrayInputStream(new byte[0]) : new ByteArrayInputStream(body.getBytes("UTF-8"));
        return new RestRequest(in, headers, request.method(), uriInfo);
    }

}

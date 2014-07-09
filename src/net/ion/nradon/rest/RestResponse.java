package net.ion.nradon.rest;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;

import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.helpers.HttpCookie;

import org.jboss.resteasy.core.Dispatcher;

public class RestResponse implements org.jboss.resteasy.spi.HttpResponse {
    private final HttpRequest request;
    private final HttpResponse response;
    private final HttpControl control;
    private MultivaluedMap<String, Object> outputHeaders;
    private boolean wasHandled;

    public RestResponse(HttpRequest request, HttpResponse response, Dispatcher dispatcher, HttpControl control) {
        this.request = request;
        this.response = response;
        this.control = control;
        this.outputHeaders = new RadonResponseHeaders(response, dispatcher.getProviderFactory());
    }

    public int getStatus() {
        return response.status();
    }

    public void setStatus(int status) {
        if (status == 404) {
            wasHandled = false;
            control.nextHandler(request, response);
        } else {
            wasHandled = true;
            response.status(status);
        }
    }

    public MultivaluedMap<String, Object> getOutputHeaders() {
        return outputHeaders;
    }

    public OutputStream getOutputStream() throws IOException {
        return new OutputStream() {
            @Override
            public void write(int i) throws IOException {
                response.content(new byte[]{(byte) i});
            }
        };
    }

    public void addNewCookie(NewCookie cookie) {
        HttpCookie httpCookie = new HttpCookie(cookie.getName(), cookie.getValue());
        httpCookie.setComment(cookie.getComment());
        httpCookie.setDomain(cookie.getDomain());
        httpCookie.setMaxAge(cookie.getMaxAge());
        httpCookie.setVersion(cookie.getVersion());
        httpCookie.setPath(cookie.getPath());
        response.cookie(httpCookie);
    }


    public void sendError(int status) throws IOException {
        response.status(status);
    }


    public void sendError(int status, String message) throws IOException {
        setStatus(status);
    }

    public boolean isCommitted() {
        throw new UnsupportedOperationException();
    }

    public void reset() {
    }

    public boolean wasHandled() {
        return wasHandled;
    }
}

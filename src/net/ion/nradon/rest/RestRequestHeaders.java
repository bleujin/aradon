package net.ion.nradon.rest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import net.ion.nradon.HttpRequest;
import net.ion.nradon.helpers.HttpCookie;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.util.HttpHeaderNames;
import org.jboss.resteasy.util.MediaTypeHelper;

class RestRequestHeaders implements HttpHeaders {
    private final HttpRequest request;

    public RestRequestHeaders(HttpRequest request) {
        this.request = request;
    }

    public List<String> getRequestHeader(String name) {
        throw new UnsupportedOperationException();
    }

    public MultivaluedMap<String, String> getRequestHeaders() {
        Headers<String> result = new Headers<String>();
        List<Entry<String, String>> entries = request.allHeaders();
        for (Entry<String, String> entry : entries) {
            result.add(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public List<MediaType> getAcceptableMediaTypes() {
        String accept = request.header(HttpHeaderNames.ACCEPT);
        return accept == null ? Collections.<MediaType>emptyList() : MediaTypeHelper.parseHeader(accept);
    }

    public List<Locale> getAcceptableLanguages() {
        throw new UnsupportedOperationException();
    }

    public MediaType getMediaType() {
        String contentType = request.header(HttpHeaderNames.CONTENT_TYPE);
        return contentType == null ? null : MediaType.valueOf(contentType);
    }

    public Locale getLanguage() {
        throw new UnsupportedOperationException();
    }

    public Map<String, Cookie> getCookies() {
        Map<String, Cookie> result = new HashMap<String, Cookie>();
        List<HttpCookie> cookies = request.cookies();
        for (HttpCookie cookie : cookies) {
            result.put(cookie.getName(), new Cookie(cookie.getName(), cookie.getValue(), cookie.getPath(), cookie.getDomain(), cookie.getVersion()));
        }
        return result;
    }
}

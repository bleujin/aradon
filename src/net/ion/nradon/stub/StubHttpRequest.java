package net.ion.nradon.stub;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ion.nradon.HttpRequest;
import net.ion.nradon.helpers.HttpCookie;
import net.ion.nradon.helpers.QueryParameters;

import org.apache.commons.collections.DefaultMapEntry;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

/**
 * Implementation of HttpRequest that is easy to construct manually and populate. Useful for testing.
 */
public class StubHttpRequest extends StubDataHolder implements HttpRequest {

	private String uri = "/";
	private String method = "GET";
	private List<Map.Entry<String, String>> headers = new ArrayList<Map.Entry<String, String>>();
	private SocketAddress remoteAddress = new InetSocketAddress("localhost", 0);
	private Object id = "StubID";
	private long timestamp = 0;
	private String body;

	public StubHttpRequest() {
	}

	public StubHttpRequest(String uri) {
		this.uri = uri;
	}

	public String uri() {
		return uri;
	}

	public StubHttpRequest uri(String uri) {
		this.uri = uri;
		return this;
	}

	public String header(String name) {
		for (Map.Entry<String, String> header : headers) {
			if (header.getKey().equals(name)) {
				return header.getValue();
			}
		}
		return null;
	}

	public boolean hasHeader(String name) {
		for (Map.Entry<String, String> header : headers) {
			if (header.getKey().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public List<HttpCookie> cookies() {
		return HttpCookie.parse(header(COOKIE_HEADER)) ;
	}

	public HttpCookie cookie(String name) {
		for (HttpCookie cookie : cookies()) {
			if (cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}

	public String queryParam(String key) {
		return new QueryParameters(URI.create(uri()).getQuery()).first(key);
	}

	public List<String> queryParams(String key) {
		return new QueryParameters(URI.create(uri()).getQuery()).all(key);
	}

	public Set<String> queryParamKeys() {
		return new QueryParameters(URI.create(uri()).getQuery()).keys();
	}

	public String postParam(String key) {
		return new QueryParameters(body()).first(key);
	}

	public List<String> postParams(String key) {
		return new QueryParameters(body()).all(key);
	}

	public Set<String> postParamKeys() {
		return new QueryParameters(body()).keys();
	}

	public String cookieValue(String name) {
		HttpCookie cookie = cookie(name);
		return cookie == null ? null : cookie.getValue();
	}

	public List<String> headers(String name) {
		List<String> result = new ArrayList<String>();
		for (Map.Entry<String, String> header : headers) {
			if (header.getKey().equals(name)) {
				result.add(header.getValue());
			}
		}
		return result;
	}

	public List<Map.Entry<String, String>> allHeaders() {
		return headers;
	}

	public String method() {
		return method;
	}

	public String body() {
		return body;
	}

	public byte[] bodyAsBytes() {
		return body.getBytes();
	}
	
	public Representation bodyAsRepresentation(MediaType mtype) throws IOException {
		Representation result = new StringRepresentation(body) ;
		result.setMediaType(mtype) ;
		return result ;
	}
	

	public StubHttpRequest body(String body) {
		this.body = body;
		return this;
	}

	public StubHttpRequest method(String method) {
		this.method = method;
		return this;
	}
	
	public StubHttpRequest method(HttpMethod method) {
		this.method = method.toString();
		return this;
	}
	
	

	public StubHttpRequest header(String name, String value) {
		headers.add(new DefaultMapEntry(name, value));
		return this;
	}

	public StubHttpRequest data(String key, Object value) {
		super.data(key, value);
		return this;
	}

	public SocketAddress remoteAddress() {
		return remoteAddress;
	}

	public Object id() {
		return id;
	}

	public StubHttpRequest id(Object id) {
		this.id = id;
		return this;
	}

	public long timestamp() {
		return timestamp;
	}

	public StubHttpRequest timestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public StubHttpRequest remoteAddress(SocketAddress remoteAddress) {
		this.remoteAddress = remoteAddress;
		return this;
	}
}

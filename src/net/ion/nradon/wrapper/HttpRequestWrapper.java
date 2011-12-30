package net.ion.nradon.wrapper;

import java.net.SocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ion.nradon.HttpRequest;

import org.restlet.data.Cookie;

public class HttpRequestWrapper implements HttpRequest {

	private HttpRequest request;

	public HttpRequestWrapper(HttpRequest request) {
		this.request = request;
	}

	public HttpRequest underlyingRequest() {
		return request;
	}

	public HttpRequestWrapper underlyingRequest(HttpRequest request) {
		this.request = request;
		return this;
	}

	public HttpRequest originalRequest() {
		if (request instanceof HttpRequestWrapper) {
			HttpRequestWrapper wrapper = (HttpRequestWrapper) request;
			return wrapper.originalRequest();
		} else {
			return request;
		}
	}

	public String uri() {
		return request.uri();
	}

	public HttpRequestWrapper uri(String uri) {
		request.uri(uri);
		return this;
	}

	public String header(String name) {
		return request.header(name);
	}

	public boolean hasHeader(String name) {
		return request.hasHeader(name);
	}

	public List<Cookie> cookies() {
		return request.cookies();
	}

	public Cookie cookie(String name) {
		return request.cookie(name);
	}

	public String queryParam(String key) {
		return request.queryParam(key);
	}

	public List<String> queryParams(String key) {
		return request.queryParams(key);
	}

	public Set<String> queryParamKeys() {
		return request.queryParamKeys();
	}

	public String postParam(String key) {
		return request.postParam(key);
	}

	public List<String> postParams(String key) {
		return request.postParams(key);
	}

	public Set<String> postParamKeys() {
		return request.postParamKeys();
	}

	public String cookieValue(String name) {
		return request.cookieValue(name);
	}

	public List<String> headers(String name) {
		return request.headers(name);
	}

	public List<Map.Entry<String, String>> allHeaders() {
		return request.allHeaders();
	}

	public String method() {
		return request.method();
	}

	public String body() {
		return request.body();
	}

	public byte[] bodyAsBytes() {
		return request.bodyAsBytes();
	}

	public Map<String, Object> data() {
		return request.data();
	}

	public Object data(String key) {
		return request.data(key);
	}

	public HttpRequestWrapper data(String key, Object value) {
		request.data(key, value);
		return this;
	}

	public Set<String> dataKeys() {
		return request.dataKeys();
	}

	public SocketAddress remoteAddress() {
		return request.remoteAddress();
	}

	public Object id() {
		return request.id();
	}

	public long timestamp() {
		return request.timestamp();
	}
}

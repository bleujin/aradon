package net.ion.nradon.wrapper;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;

import net.ion.nradon.HttpResponse;

import org.restlet.Response;
import org.restlet.data.Cookie;

public class HttpResponseWrapper implements HttpResponse {

	private HttpResponse response;

	public HttpResponseWrapper(HttpResponse response) {
		this.response = response;
	}

	public HttpResponse underlyingResponse() {
		return response;
	}

	public HttpResponseWrapper underlyingResponse(HttpResponse response) {
		this.response = response;
		return this;
	}

	public HttpResponse originalResponse() {
		if (response instanceof HttpResponseWrapper) {
			HttpResponseWrapper wrapper = (HttpResponseWrapper) response;
			return wrapper.originalResponse();
		} else {
			return response;
		}
	}

	public HttpResponseWrapper charset(Charset charset) {
		response.charset(charset);
		return this;
	}

	public Charset charset() {
		return response.charset();
	}

	public HttpResponseWrapper status(int status) {
		response.status(status);
		return this;
	}

	public int status() {
		return response.status();
	}

	public HttpResponseWrapper header(String name, String value) {
		response.header(name, value);
		return this;
	}

	public HttpResponseWrapper header(String name, long value) {
		response.header(name, value);
		return this;
	}

	public HttpResponseWrapper cookie(Cookie httpCookie) {
		response.cookie(httpCookie);
		return this;
	}

	public HttpResponseWrapper content(String content) {
		response.content(content);
		return this;
	}

	public HttpResponseWrapper write(String content) {
		response.write(content);
		return this;
	}
	
	public HttpResponseWrapper write(Response res) {
		response.write(res) ;
		return this ;
	}

	public HttpResponseWrapper content(byte[] content) {
		response.content(content);
		return this;
	}

	public HttpResponseWrapper content(ByteBuffer buffer) {
		response.content(buffer);
		return this;
	}

	public HttpResponseWrapper error(Throwable error) {
		response.error(error);
		return this;
	}

	public HttpResponseWrapper end() {
		response.end();
		return this;
	}

	public HttpResponseWrapper header(String name, Date value) {
		response.header(name, value);
		return this;
	}

	public boolean containsHeader(String name) {
		return response.containsHeader(name);
	}
}

package net.ion.nradon;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;

import org.restlet.Response;
import org.restlet.data.Cookie;

public interface HttpResponse {
	String SET_COOKIE_HEADER = "Set-Cookie";

	HttpResponse charset(Charset charset);

	Charset charset();

	HttpResponse status(int status);

	int status();

	HttpResponse header(String name, String value);

	HttpResponse header(String name, long value);

	HttpResponse header(String name, Date value);

	boolean containsHeader(String name);

	HttpResponse cookie(Cookie httpCookie);

	HttpResponse content(String content);

	public HttpResponse write(Response response);

	HttpResponse write(String content);

	HttpResponse content(byte[] content);

	HttpResponse content(ByteBuffer buffer);

	HttpResponse error(Throwable error);

	HttpResponse end();
}

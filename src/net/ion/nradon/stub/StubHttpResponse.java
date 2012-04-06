package net.ion.nradon.stub;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.ion.framework.util.IOUtil;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.helpers.DateHelper;

import org.restlet.Response;
import org.restlet.data.Cookie;

/**
 * Implementation of HttpResponse that is easy to construct manually, and inspect results. Useful for testing.
 */
public class StubHttpResponse implements HttpResponse {

	private Charset charset = Charset.forName("UTF-8");
	private int status = 200;
	private Map<String, String> headers = new HashMap<String, String>();
	private Throwable error;
	private boolean ended;
	private ByteArrayOutputStream contents = new ByteArrayOutputStream();
	private List<Cookie> cookies = new ArrayList<Cookie>();

	public StubHttpResponse charset(Charset charset) {
		this.charset = charset;
		return this;
	}

	public Charset charset() {
		return charset;
	}

	public StubHttpResponse status(int status) {
		this.status = status;
		return this;
	}

	public int status() {
		return status;
	}

	public StubHttpResponse header(String name, String value) {
		if (value == null) {
			headers.remove(name);
		} else {
			headers.put(name, value);
		}
		return this;
	}

	public StubHttpResponse header(String name, long value) {
		return header(name, String.valueOf(value));
	}

	public StubHttpResponse header(String name, Date value) {
		return header(name, DateHelper.rfc1123Format(value));
	}

	public StubHttpResponse cookie(Cookie httpCookie) {
		cookies.add(httpCookie);
		return this;
	}

	public StubHttpResponse cookie(String name, String value) {
		return cookie(new Cookie(name, value));
	}

	public String header(String name) {
		return headers.get(name);
	}

	public boolean containsHeader(String name) {
		return headers.containsKey(name);
	}

	public StubHttpResponse content(String content) {
		try {
			return content(content.getBytes(charset.toString()));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e) ;
		}
	}

	public StubHttpResponse write(String content) {
		return content(content);
	}

	public StubHttpResponse content(byte[] content) {
		try {
			contents.write(content);
		} catch (IOException e) {
			throw new Error(e);
		}
		return this;
	}
	
	public StubHttpResponse write(Response response) {
		try {
			InputStream input = response.getEntity().getStream();
			IOUtil.copy(input, contents) ;
			input.close() ;
		} catch (IOException e) {
			throw new Error(e);
		} finally{
			response.release() ;
		}
		return this ;
	}

	public StubHttpResponse content(ByteBuffer buffer) {
		while (buffer.hasRemaining()) {
			contents.write(buffer.get());
		}
		return this;
	}

	public byte[] contents() {
		return contents.toByteArray();
	}

	public String contentsString() {
		try {
			return new String(contents(), charset.toString());
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e) ;
		}
	}

	public StubHttpResponse error(Throwable error) {
		this.error = error;
		status = 500;
		String message = error.toString();
		this.content(message);
		header("Content-Type", "text/plain");
		header("Content-Length", message.length());
		ended = true;
		return this;
	}

	public Throwable error() {
		return error;
	}

	public StubHttpResponse end() {
		ended = true;
		return this;
	}

	public boolean ended() {
		return ended;
	}

	public List<Cookie> cookies() {
		return cookies;
	}

	@Override
	public String toString() {
		return "StubHttpResponse{" + "charset=" + charset + ", status=" + status + ", headers=" + headers + ", error=" + error + ", ended=" + ended + ", contents=" + contentsString() + '}';
	}
}

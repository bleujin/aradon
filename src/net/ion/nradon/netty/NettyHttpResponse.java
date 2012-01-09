package net.ion.nradon.netty;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;

import net.ion.nradon.helpers.DateHelper;
import net.ion.radon.core.except.AradonRuntimeException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.util.CharsetUtil;
import org.restlet.data.Cookie;
import org.restlet.engine.header.CookieWriter;

public class NettyHttpResponse implements net.ion.nradon.HttpResponse {

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private final ChannelHandlerContext ctx;
	private final HttpResponse response;
	private final boolean isKeepAlive;
	private final Thread.UncaughtExceptionHandler exceptionHandler;
	private final Thread.UncaughtExceptionHandler ioExceptionHandler;
	private final ChannelBuffer responseBuffer;
	private Charset charset;

	public NettyHttpResponse(ChannelHandlerContext ctx, HttpResponse response, boolean isKeepAlive, Thread.UncaughtExceptionHandler exceptionHandler, Thread.UncaughtExceptionHandler ioExceptionHandler) {
		this.ctx = ctx;
		this.response = response;
		this.isKeepAlive = isKeepAlive;
		this.exceptionHandler = exceptionHandler;
		this.ioExceptionHandler = ioExceptionHandler;
		this.charset = DEFAULT_CHARSET;
		responseBuffer = ChannelBuffers.dynamicBuffer();
	}

	public NettyHttpResponse charset(Charset charset) {
		this.charset = charset;
		return this;
	}

	public Charset charset() {
		return charset;
	}

	public NettyHttpResponse status(int status) {
		response.setStatus(HttpResponseStatus.valueOf(status));
		return this;
	}

	public int status() {
		return response.getStatus().getCode();
	}

	public NettyHttpResponse header(String name, String value) {
		if (value == null) {
			response.removeHeader(name);
		} else {
			response.addHeader(name, value);
		}
		return this;
	}

	public NettyHttpResponse header(String name, long value) {
		response.addHeader(name, value);
		return this;
	}

	public NettyHttpResponse header(String name, Date value) {
		response.addHeader(name, DateHelper.rfc1123Format(value));
		return this;
	}

	public boolean containsHeader(String name) {
		return response.containsHeader(name);
	}

	public NettyHttpResponse cookie(Cookie httpCookie) {
		return header(SET_COOKIE_HEADER, CookieWriter.write(httpCookie));
	}

	public NettyHttpResponse content(String content) {
		return content(ChannelBuffers.copiedBuffer(content, charset()));
	}

	public NettyHttpResponse content(byte[] content) {
		return content(ChannelBuffers.copiedBuffer(content));
	}

	public NettyHttpResponse content(ByteBuffer buffer) {
		return content(ChannelBuffers.wrappedBuffer(buffer));
	}

	
	private NettyHttpResponse content(ChannelBuffer content) {
		responseBuffer.writeBytes(content);
		return this;
	}

	public NettyHttpResponse write(String content) {
		write(ChannelBuffers.copiedBuffer(content, CharsetUtil.UTF_8));
		return this;
	}

	public NettyHttpResponse error(Throwable error) {
		response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR);
		String message = getStackTrace(error);
		header("Content-Type", "text/plain");
		content(message);
		flushResponse();

		exceptionHandler.uncaughtException(Thread.currentThread(), AradonRuntimeException.fromException(error, ctx.getChannel()));

		return this;
	}

	private String getStackTrace(Throwable error) {
		StringWriter buffer = new StringWriter();
		PrintWriter writer = new PrintWriter(buffer);
		error.printStackTrace(writer);
		writer.flush();
		return buffer.toString();
	}

	public NettyHttpResponse end() {
		flushResponse();
		return this;
	}

	private void flushResponse() {
		try {
			// TODO: Shouldn't have to do this, but without it we sometimes seem to get two Content-Length headers in the response.
			header("Content-Length", (String) null);
			header("Content-Length", responseBuffer.readableBytes());
			ChannelFuture future = write(responseBuffer);
			if (!isKeepAlive) {
				future.addListener(ChannelFutureListener.CLOSE);
			}
		} catch (Exception e) {
			exceptionHandler.uncaughtException(Thread.currentThread(), AradonRuntimeException.fromException(e, ctx.getChannel()));
		}
	}

	private ChannelFuture write(ChannelBuffer responseBuffer) {
		
		
		response.setContent(responseBuffer);
		return ctx.getChannel().write(response);
	}

}

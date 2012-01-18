package net.ion.nradon.netty;

import static org.jboss.netty.buffer.ChannelBuffers.copiedBuffer;
import static org.jboss.netty.buffer.ChannelBuffers.wrappedBuffer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Date;

import net.ion.nradon.helpers.DateHelper;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.except.AradonRuntimeException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.stream.ChunkedStream;
import org.jboss.netty.util.CharsetUtil;
import org.restlet.Response;
import org.restlet.data.Cookie;
import org.restlet.engine.ConnectorHelper;
import org.restlet.engine.header.CookieWriter;
import org.restlet.engine.header.Header;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.representation.Representation;
import org.restlet.service.ConnectorService;
import org.restlet.util.Series;

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
		return content(copiedBuffer(content, charset()));
	}

	public NettyHttpResponse content(byte[] content) {
		return content(copiedBuffer(content));
	}

	public NettyHttpResponse content(ByteBuffer buffer) {
		return content(wrappedBuffer(buffer));
	}

	public NettyHttpResponse write(Response aradonResponse) {

		Representation entity = aradonResponse.getEntity(); // Get the connector service to callback
		ConnectorService connectorService = ConnectorHelper.getConnectorService();

		if (connectorService != null) {
			connectorService.beforeSend(entity);
		}

		try {
			// Copy general, response and entity headers
			Series<Header> headers = (Series<Header>) aradonResponse.getAttributes().get(RadonAttributeKey.ATTRIBUTE_HEADERS);
			for (Header header : headers) {
				response.addHeader(header.getName(), header.getValue());
			}
			// TODO: Shouldn't have to do this, but without it we sometimes seem to get two Content-Length headers in the response.
			header("Content-Length", (String) null);
			header("Content-Length", entity.getSize());
			header("Content-Type", entity.getMediaType().getName()) ;
			status(aradonResponse.getStatus().getCode()) ;
			

			if (shouldResponseBeChunked(entity)) { // Check if 'Transfer-Encoding' header should be set
				response.addHeader(HeaderConstants.HEADER_TRANSFER_ENCODING, "chunked");
			}

			// Write the response
			ChannelFuture future = null;

			if (entity != null) {
					response.setContent(null);
					future = ctx.getChannel().write(response);
					ctx.getChannel().write(new ChunkedStream(entity.getStream()));
//					responseBuffer.writeBytes(entity.getStream(), (int) entity.getAvailableSize());
//					response.setContent(responseBuffer);
//					future = ctx.getChannel().write(response);
			}

			if (!isKeepAlive) { // Close the connection after the write operation is done.
				future.addListener(ChannelFutureListener.CLOSE);
			}
		} catch (Exception ex) {
			exceptionHandler.uncaughtException(Thread.currentThread(), AradonRuntimeException.fromException(ex, ctx.getChannel()));
		} finally {
			if (entity != null) {
				entity.release();
			}

			if (connectorService != null) {
				connectorService.afterSend(entity);
			}
		}
		return this;
	}

	private boolean shouldResponseBeChunked(Representation rep) {
		return (rep != null) && (rep.getSize() == Representation.UNKNOWN_SIZE);
	}

	private NettyHttpResponse content(ChannelBuffer content) {
		responseBuffer.writeBytes(content);
		return this;
	}

	public NettyHttpResponse write(String content) {
		write(copiedBuffer(content, CharsetUtil.UTF_8));
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

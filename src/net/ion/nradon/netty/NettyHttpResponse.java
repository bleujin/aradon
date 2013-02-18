package net.ion.nradon.netty;

import static org.jboss.netty.buffer.ChannelBuffers.copiedBuffer;
import static org.jboss.netty.buffer.ChannelBuffers.wrappedBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Date;

import net.ion.framework.util.IOUtil;
import net.ion.nradon.helpers.DateHelper;
import net.ion.nradon.helpers.HttpCookie;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.except.AradonRuntimeException;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.util.CharsetUtil;
import org.restlet.Response;
import org.restlet.data.CookieSetting;
import org.restlet.engine.header.Header;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.engine.header.HeaderUtils;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.util.Series;

public class NettyHttpResponse implements net.ion.nradon.HttpResponse {

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	private final ChannelHandlerContext ctx;
	private final HttpResponse response;
	private final boolean isKeepAlive;
	private final Thread.UncaughtExceptionHandler exceptionHandler;
	private final ChannelBuffer responseBuffer;
	private Charset charset;

	public NettyHttpResponse(ChannelHandlerContext ctx, HttpResponse response, boolean isKeepAlive, Thread.UncaughtExceptionHandler exceptionHandler) {
		this.ctx = ctx;
		this.response = response;
		this.isKeepAlive = isKeepAlive;
		this.exceptionHandler = exceptionHandler;
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

	public NettyHttpResponse cookie(HttpCookie httpCookie) {
		return header(SET_COOKIE_HEADER, httpCookie.toString());
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

	public NettyHttpResponse write(Response ares) {
		// if (!aradonResponse.getStatus().isSuccess()) throw new ResourceException(aradonResponse.getStatus()) ;

		Representation entity = ares.getEntity(); // Get the connector service to callback
		if (ares.getStatus().isSuccess() && entity instanceof FileRepresentation) {
			FileRepresentation fentity = (FileRepresentation) entity ;
			if (fentity.getSize() < 1024 * 512){
				return writeFileEntity(ares, fentity) ;
			} else {
				return writeBigFile(ares, fentity);
			}
		} else {
			return writeEntity(ares, entity);
		}
	}

	private NettyHttpResponse writeFileEntity(Response ares, FileRepresentation fentity) {
		try {
			setFileHeader(ares, fentity) ;
			
			File file = fentity.getFile();
			FileChannel fc = fentity.getChannel();

			MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			content(bb);
		} catch (IOException ex) {
			exceptionHandler.uncaughtException(Thread.currentThread(), AradonRuntimeException.fromException(ex, ctx.getChannel())) ;
			error(ex);
		} finally {
			end();
		}
		return this;
	}
	
	private void setFileHeader(Response ares, FileRepresentation fentity){
		// TODO: Shouldn't have to do this, but without it we sometimes seem to get two Content-Length headers in the response.
		header("Content-Length", (String) null);
		header("Content-Length", fentity.getSize());
		header("Content-Type", fentity.getMediaType().getName());
		
		setHeader(ares);
	}

	private NettyHttpResponse writeBigFile(Response ares, FileRepresentation fentity) {

		try {
			setFileHeader(ares, fentity) ;

			ChannelFuture writeFuture = write(responseBuffer);

			final long fileLength = fentity.getSize();
			final FileInputStream fis = fentity.getStream();
			writeFuture.addListener(new ChannelFutureListener() {
				private final ChannelBuffer buffer = ChannelBuffers.buffer(4096);
				private long offset = 0;

				public void operationComplete(ChannelFuture future) throws Exception {
					if (!future.isSuccess()) {
						future.getCause().printStackTrace();
						future.getChannel().close();
						fis.close();
						return;
					}

					// System.out.println("SENDING: " + offset + " / " + fileLength);
					buffer.clear();
					buffer.writeBytes(fis, (int) Math.min(fileLength - offset, buffer.writableBytes()));
					offset += buffer.writerIndex();
					ChannelFuture chunkWriteFuture = future.getChannel().write(buffer);
					if (offset < fileLength) { // Send the next chunk
						chunkWriteFuture.addListener(this);
					} else {
						// Wrote the last chunk - close the connection if the write is done.
						// System.out.println("DONE: " + fileLength);
						chunkWriteFuture.addListener(ChannelFutureListener.CLOSE);
						fis.close();
					}
				}
			});

		} catch (Exception ex) {
			exceptionHandler.uncaughtException(Thread.currentThread(), AradonRuntimeException.fromException(ex, ctx.getChannel()));
		}
		return this;
	}

	private NettyHttpResponse writeEntity(Response ares, Representation entity) {
		InputStream in = null;
		try {
			setHeader(ares);

			if (!ares.getStatus().isSuccess()) {
				if (ares.getStatus().getCode() == 401) {
					header("WWW-Authenticate", "Basic realm=\"secure\"");
				}
				if (ares.getEntity() == null || ares.getEntity().getStream() == null)
					return end();
				InputStream istream = ares.getEntity().getStream();
				try {
					return content(IOUtil.toByteArray(istream)).end();
				} finally {
					IOUtil.closeQuietly(istream);
				}
			}

			// TODO: Shouldn't have to do this, but without it we sometimes seem to get two Content-Length headers in the response.
			header("Content-Length", (String) null);
			header("Content-Length", entity.getSize());
			header("Content-Type", entity.getMediaType().getName());

			in = entity.getStream();
			// byte[] data = IOUtil.toByteArray(in) ;
			// content(data) ;

			byte[] data = new byte[1024 * 16];
			int nRead = 0;
			while ((nRead = in.read(data, 0, data.length)) != -1) {
				content(ChannelBuffers.copiedBuffer(data, 0, nRead));
			}

		} catch (Throwable ex) {
			error(ex);
		} finally {
			IOUtil.closeQuietly(in);
			end();
		}
		return this;
	}

	private void setHeader(Response ares) {
		Series<Header> headers = (Series<Header>) ares.getAttributes().get(RadonAttributeKey.ATTRIBUTE_HEADERS);
		if (headers == null) headers = new Series(Header.class) ;
		HeaderUtils.addEntityHeaders(ares.getEntity(), headers) ;
		for (Header header : headers) {
			header(header.getName(), header.getValue());
		}

		Series<CookieSetting> cookies = ares.getCookieSettings();
		for (CookieSetting c : cookies) {
			HttpCookie hc = new HttpCookie(c.getName(), c.getValue());
			hc.setVersion(c.getVersion()) ;
			hc.setPath(c.getPath()) ;
			hc.setDomain(c.getDomain()) ;
			cookie(hc);
		}

		status(ares.getStatus().getCode());
		if (ares.getLocationRef() != null) {
			header(HeaderConstants.HEADER_LOCATION, ares.getLocationRef().toString());
		}

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
//			if (StringUtil.isEmpty(response.getHeader(HeaderConstants.HEADER_CONTENT_LENGTH))) {
//				header(HeaderConstants.HEADER_CONTENT_LENGTH, responseBuffer.readableBytes());
//			}
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

package net.ion.radon.core.server.netty.internal;

import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.buffer.HeapChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpChunkTrailer;
import org.jboss.netty.handler.codec.http.HttpMessage;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.jboss.netty.util.CharsetUtil;

public class HttpCompositeEncoder extends OneToOneEncoder {

	public HttpCompositeEncoder() {
	}

	private static final ChannelBuffer LAST_CHUNK;
	private volatile boolean chunked;

	static {
		LAST_CHUNK = ChannelBuffers.copiedBuffer("0\r\n\r\n", CharsetUtil.US_ASCII);
	}

	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		
		if (msg instanceof HeapChannelBuffer) {
			int estimatedLength = 512;
			final byte LENGTH_PLACEHOLDER[] = new byte[4];
			ChannelBufferOutputStream bout = new ChannelBufferOutputStream(ChannelBuffers.dynamicBuffer(estimatedLength, ctx.getChannel().getConfig().getBufferFactory()));
			bout.write(LENGTH_PLACEHOLDER);
			ObjectOutputStream oout = new CompactObjectOutputStream(bout);
			oout.writeObject(msg);
			oout.flush();
			oout.close();
			ChannelBuffer encoded = bout.buffer();
			encoded.setInt(0, encoded.writerIndex() - 4);
			return encoded;
		}
		
		
		if (msg instanceof HttpMessage) {
			HttpMessage m = (HttpMessage) msg;
			
			boolean chunked = this.chunked = HttpCodecUtil.isTransferEncodingChunked(m);
			ChannelBuffer header = ChannelBuffers.dynamicBuffer(channel.getConfig().getBufferFactory());
			encodeInitialLine(header, m);
			encodeHeaders(header, m);
			header.writeByte(13);
			header.writeByte(10);
			ChannelBuffer content = m.getContent();
			if (!content.readable())
				return header;
			if (chunked)
				throw new IllegalArgumentException("HttpMessage.content must be empty if Transfer-Encoding is chunked.");
			else
				return ChannelBuffers.wrappedBuffer(new ChannelBuffer[] { header, content });
		}
		if (msg instanceof HttpChunk) {
			HttpChunk chunk = (HttpChunk) msg;
			if (this.chunked)
				if (chunk.isLast()) {
					this.chunked = false;
					if (chunk instanceof HttpChunkTrailer) {
						ChannelBuffer trailer = ChannelBuffers.dynamicBuffer(channel.getConfig().getBufferFactory());
						trailer.writeByte(48);
						trailer.writeByte(13);
						trailer.writeByte(10);
						encodeTrailingHeaders(trailer, (HttpChunkTrailer) chunk);
						trailer.writeByte(13);
						trailer.writeByte(10);
						return trailer;
					} else {
						return LAST_CHUNK.duplicate();
					}
				} else {
					ChannelBuffer content = chunk.getContent();
					int contentLength = content.readableBytes();
					return ChannelBuffers.wrappedBuffer(new ChannelBuffer[] { ChannelBuffers.copiedBuffer(Integer.toHexString(contentLength), CharsetUtil.US_ASCII), ChannelBuffers.wrappedBuffer(HttpCodecUtil.CRLF), content.slice(content.readerIndex(), contentLength),
							ChannelBuffers.wrappedBuffer(HttpCodecUtil.CRLF) });
				}
			if (chunk.isLast())
				return null;
			else
				return chunk.getContent();
		} else {
			return msg;
		}
	}

	private void encodeHeaders(ChannelBuffer buf, HttpMessage message) {
		try {
			java.util.Map.Entry h;
			for (Iterator i$ = message.getHeaders().iterator(); i$.hasNext(); encodeHeader(buf, (String) h.getKey(), (String) h.getValue()))
				h = (java.util.Map.Entry) i$.next();

		} catch (UnsupportedEncodingException e) {
			throw (Error) (new Error()).initCause(e);
		}
	}

	private void encodeTrailingHeaders(ChannelBuffer buf, HttpChunkTrailer trailer) {
		try {
			java.util.Map.Entry h;
			for (Iterator i$ = trailer.getHeaders().iterator(); i$.hasNext(); encodeHeader(buf, (String) h.getKey(), (String) h.getValue()))
				h = (java.util.Map.Entry) i$.next();

		} catch (UnsupportedEncodingException e) {
			throw (Error) (new Error()).initCause(e);
		}
	}

	private void encodeHeader(ChannelBuffer buf, String header, String value) throws UnsupportedEncodingException {
		buf.writeBytes(header.getBytes("ASCII"));
		buf.writeByte(58);
		buf.writeByte(32);
		buf.writeBytes(value.getBytes("ASCII"));
		buf.writeByte(13);
		buf.writeByte(10);
	}

	protected void encodeInitialLine(ChannelBuffer channelBuf, HttpMessage message) throws Exception {
		HttpResponse response = (HttpResponse) message;
		channelBuf.writeBytes(response.getProtocolVersion().toString().getBytes("ASCII"));
		channelBuf.writeByte(32);
		channelBuf.writeBytes(String.valueOf(response.getStatus().getCode()).getBytes("ASCII"));
		channelBuf.writeByte(32);
		channelBuf.writeBytes(String.valueOf(response.getStatus().getReasonPhrase()).getBytes("ASCII"));
		channelBuf.writeByte(13);
		channelBuf.writeByte(10);
	}

}

class HttpCodecUtil {

	private HttpCodecUtil() {
	}

	static void validateHeaderName(String name) {
		if (name == null)
			throw new NullPointerException("name");
		int i = 0;
		do {
			if (i >= name.length())
				break;
			char c = name.charAt(i);
			if (c > '\177')
				throw new IllegalArgumentException((new StringBuilder()).append("name contains non-ascii character: ").append(name).toString());
			switch (c) {
			case 9: // '\t'
			case 10: // '\n'
			case 11: // '\013'
			case 12: // '\f'
			case 13: // '\r'
			case 32: // ' '
			case 44: // ','
			case 58: // ':'
			case 59: // ';'
			case 61: // '='
				throw new IllegalArgumentException((new StringBuilder()).append("name contains one of the following prohibited characters: =,;: \\t\\r\\n\\v\\f: ").append(name).toString());
			}
			i++;
		} while (true);
	}

	static void validateHeaderValue(String value) {
		if (value == null)
			throw new NullPointerException("value");
		int state = 0;
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case 11: // '\013'
				throw new IllegalArgumentException((new StringBuilder()).append("value contains a prohibited character '\\v': ").append(value).toString());

			case 12: // '\f'
				throw new IllegalArgumentException((new StringBuilder()).append("value contains a prohibited character '\\f': ").append(value).toString());
			}
			switch (state) {
			default:
				break;

			case 0: // '\0'
				switch (c) {
				case 13: // '\r'
					state = 1;
					break;

				case 10: // '\n'
					state = 2;
					break;
				}
				break;

			case 1: // '\001'
				switch (c) {
				case 10: // '\n'
					state = 2;
					break;

				default:
					throw new IllegalArgumentException((new StringBuilder()).append("Only '\\n' is allowed after '\\r': ").append(value).toString());
				}
				break;

			case 2: // '\002'
				switch (c) {
				case 9: // '\t'
				case 32: // ' '
					state = 0;
					break;

				default:
					throw new IllegalArgumentException((new StringBuilder()).append("Only ' ' and '\\t' are allowed after '\\n': ").append(value).toString());
				}
				break;
			}
		}

		if (state != 0)
			throw new IllegalArgumentException((new StringBuilder()).append("value must not end with '\\r' or '\\n':").append(value).toString());
		else
			return;
	}

	static boolean isTransferEncodingChunked(HttpMessage m) {
		List chunked = m.getHeaders("Transfer-Encoding");
		if (chunked.isEmpty())
			return false;
		for (Iterator i$ = chunked.iterator(); i$.hasNext();) {
			String v = (String) i$.next();
			if (v.equalsIgnoreCase("chunked"))
				return true;
		}

		return false;
	}

	static final byte SP = 32;
	static final byte HT = 9;
	static final byte CR = 13;
	static final byte EQUALS = 61;
	static final byte LF = 10;
	static final byte CRLF[] = { 13, 10 };
	static final byte COLON = 58;
	static final byte SEMICOLON = 59;
	static final byte COMMA = 44;
	static final byte DOUBLE_QUOTE = 34;
	static final Charset DEFAULT_CHARSET;

	static {
		DEFAULT_CHARSET = CharsetUtil.UTF_8;
	}
}

package net.ion.nradon.netty;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.channels.WritableByteChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ion.nradon.helpers.HttpCookie;
import net.ion.nradon.helpers.QueryParameters;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.util.CharsetUtil;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.WritableRepresentation;

public class NettyHttpRequest implements net.ion.nradon.HttpRequest {

	private final HttpRequest httpRequest;
	private final MessageEvent messageEvent;
	private final Map<String, Object> data = new HashMap<String, Object>();
	private final Object id;
	private final long timestamp;

	public QueryParameters queryParameters;
	public QueryParameters postParameters;

	public NettyHttpRequest(MessageEvent messageEvent, HttpRequest httpRequest, Object id, long timestamp) {
		this.messageEvent = messageEvent;
		this.httpRequest = httpRequest;
		this.id = id;
		this.timestamp = timestamp;
	}

	public String uri() {
		return httpRequest.getUri();
	}

	public NettyHttpRequest uri(String uri) {
		httpRequest.setUri(uri);
		return this;
	}

	public String header(String name) {
		return httpRequest.getHeader(name);
	}

	public List<String> headers(String name) {
		return httpRequest.getHeaders(name);
	}

	public boolean hasHeader(String name) {
		return httpRequest.containsHeader(name);
	}

	public List<HttpCookie> cookies() {
		return HttpCookie.parse(header(COOKIE_HEADER));
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
		return parsedQueryParams().first(key);
	}

	public List<String> queryParams(String key) {
		return parsedQueryParams().all(key);
	}

	public Set<String> queryParamKeys() {
		return parsedQueryParams().keys();
	}

	public String postParam(String key) {
		return parsedPostParams().first(key);
	}

	public List<String> postParams(String key) {
		return parsedPostParams().all(key);
	}

	public Set<String> postParamKeys() {
		return parsedPostParams().keys();
	}

	private QueryParameters parsedQueryParams() {
		if (queryParameters == null) {
			queryParameters = new QueryParameters(URI.create(uri()).getRawQuery());
		}
		return queryParameters;
	}

	private QueryParameters parsedPostParams() {
		if (postParameters == null) {
			postParameters = new QueryParameters(body());
		}
		return postParameters;
	}

	public String cookieValue(String name) {
		HttpCookie cookie = cookie(name);
		return cookie == null ? null : cookie.getValue();
	}

	public List<Map.Entry<String, String>> allHeaders() {
		return httpRequest.getHeaders();
	}

	public String method() {
		return httpRequest.getMethod().getName();
	}

	public String body() {
		return httpRequest.getContent().toString(CharsetUtil.UTF_8); // TODO get charset from request
	}

	public byte[] bodyAsBytes() {
		
        ChannelBuffer buffer = httpRequest.getContent();
        byte[] body = new byte[buffer.readableBytes()];
        buffer.getBytes(buffer.readerIndex(), body);
        
        return body;		
//		return httpRequest.getContent().array();
	}

	public Representation bodyAsRepresentation(MediaType mtype) throws IOException{
		ChannelBuffer buffer = httpRequest.getContent();
		
		return toRepresentation(buffer, mtype) ;
	}
	
	private Representation toRepresentation(final ChannelBuffer buffer, MediaType mtype) throws IOException{

		WritableRepresentation woutput = new WritableRepresentation(mtype) {
			@Override
			public void write(WritableByteChannel writable) throws IOException {
				writable.write(buffer.toByteBuffer()) ;
			}
		};
		return woutput ;
		
//		final int readable = buffer.readableBytes() ;
//		OutputRepresentation output = new OutputRepresentation(mtype) {
//			@Override
//			public void write(OutputStream outputstream) throws IOException {
//				buffer.readBytes(outputstream, readable) ;
//			}
//			
//			@Override
//			public long getSize(){
//				return (long)readable ;
//			}
//		};
//		return output ;
		
//		File file = File.createTempFile("req", "upx") ;
//		FileOutputStream outputStream = new FileOutputStream(file);
//	    FileChannel localfileChannel = outputStream.getChannel();
//	    ByteBuffer byteBuffer = buffer.toByteBuffer();
//	    int written = localfileChannel.write(byteBuffer);
//	    buffer.readerIndex(buffer.readerIndex() + written);
//	    localfileChannel.force(false);
//	    localfileChannel.close();
//	    InputRepresentation result = new InputRepresentation(new FileInputStream(file), mtype) ;
//	    result.setSize(file.length()) ;
//	    return result ; 
	}
	
	public Map<String, Object> data() {
		return data;
	}

	public Object data(String key) {
		return data.get(key);
	}

	public NettyHttpRequest data(String key, Object value) {
		data.put(key, value);
		return this;
	}

	public Set<String> dataKeys() {
		return data.keySet();
	}

	public SocketAddress remoteAddress() {
		return messageEvent.getRemoteAddress();
	}

	public Object id() {
		return id;
	}

	public long timestamp() {
		return timestamp;
	}

	public String toString() {
		return messageEvent.getRemoteAddress() + " " + httpRequest.getMethod() + " " + httpRequest.getUri();
	}
}

package net.ion.nradon.netty;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import net.ion.framework.util.ObjectUtil;
import net.ion.nradon.WebSocketConnection;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.websocket.DefaultWebSocketFrame;
import org.jboss.netty.util.CharsetUtil;

public class NettyWebSocketConnection implements WebSocketConnection {

	private final Executor executor;
	private final NettyHttpRequest nettyHttpRequest;
	private final ChannelHandlerContext ctx;
	private String version;
	private boolean hybi;

	public NettyWebSocketConnection(Executor executor, NettyHttpRequest nettyHttpRequest, ChannelHandlerContext ctx) {
		this.executor = executor;
		this.nettyHttpRequest = nettyHttpRequest;
		this.ctx = ctx;
	}

	public NettyHttpRequest httpRequest() {
		return nettyHttpRequest;
	}

	public NettyWebSocketConnection send(String message) {
		if (hybi) {
			return write(new EncodingHybiFrame(Opcodes.OPCODE_TEXT, true, 0, ChannelBuffers.copiedBuffer(message, CharsetUtil.UTF_8)));
		} else {
			return write(new DefaultWebSocketFrame(message));
		}
	}

	public NettyWebSocketConnection send(byte[] message) {
		return write(new EncodingHybiFrame(Opcodes.OPCODE_BINARY, true, 0, ChannelBuffers.wrappedBuffer(message)));
	}

	public NettyWebSocketConnection ping(String message) {
		return write(new EncodingHybiFrame(Opcodes.OPCODE_PING, true, 0, ChannelBuffers.copiedBuffer(message, CharsetUtil.UTF_8)));
	}

	private NettyWebSocketConnection write(Object frame) {
		ctx.getChannel().write(frame);
		return this;
	}

	public NettyWebSocketConnection close() {
		ctx.getChannel().close();
		return this;
	}

	public Map<String, Object> data() {
		return nettyHttpRequest.data();
	}

	public Object data(String key) {
		return data().get(key);
	}

	public NettyWebSocketConnection data(String key, Object value) {
		data().put(key, value);
		return this;
	}

	public Set<String> dataKeys() {
		return data().keySet();
	}

	public Executor handlerExecutor() {
		return executor;
	}

	public void execute(Runnable command) {
		handlerExecutor().execute(command);
	}

	public String version() {
		return version;
	}

	void setVersion(String version) {
		this.version = version;
	}

	void setHybiWebSocketVersion(int webSocketVersion) {
		setVersion("Sec-WebSocket-Version-" + webSocketVersion);
		hybi = true;
	}

	Channel getChannel() {
		return ctx.getChannel();
	}

	public String getString(String key) {
		return ObjectUtil.toString(data(key));
	}
}

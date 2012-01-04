package net.ion.nradon.netty;

import static org.jboss.netty.buffer.ChannelBuffers.copiedBuffer;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.netty.contrib.EventSourceMessage;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.util.CharsetUtil;

public class NettyEventSourceConnection implements EventSourceConnection {
	private final Executor executor;
	private final NettyHttpRequest nettyHttpRequest;
	private final ChannelHandlerContext ctx;

	public NettyEventSourceConnection(Executor executor, NettyHttpRequest nettyHttpRequest, ChannelHandlerContext ctx) {
		this.executor = executor;
		this.nettyHttpRequest = nettyHttpRequest;
		this.ctx = ctx;
	}

	public NettyHttpRequest httpRequest() {
		return nettyHttpRequest;
	}

	public EventSourceConnection send(EventSourceMessage message) {
		return send(message.build() + "\n");
	}

	public NettyEventSourceConnection send(String message) {
		ctx.getChannel().write(copiedBuffer(message, CharsetUtil.UTF_8));
		return this;
	}

	public NettyEventSourceConnection close() {
		ctx.getChannel().close();
		return this;
	}

	public void execute(Runnable command) {
		executor.execute(command);
	}

	public Map<String, Object> data() {
		return nettyHttpRequest.data();
	}

	public Object data(String key) {
		return data().get(key);
	}

	public NettyEventSourceConnection data(String key, Object value) {
		data().put(key, value);
		return this;
	}

	public Set<String> dataKeys() {
		return data().keySet();
	}

	public Executor handlerExecutor() {
		return executor;
	}
}

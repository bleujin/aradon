package net.ion.nradon.netty;

import static org.jboss.netty.buffer.ChannelBuffers.copiedBuffer;

import java.util.concurrent.Executor;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceMessage;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.util.CharsetUtil;

public class NettyEventSourceConnection extends AbstractHttpConnection implements EventSourceConnection {
    public NettyEventSourceConnection(Executor executor, NettyHttpRequest nettyHttpRequest, ChannelHandlerContext ctx) {
        super(ctx, nettyHttpRequest, executor);
    }

    public NettyEventSourceConnection send(EventSourceMessage message) {
        writeMessage(copiedBuffer(message.build(), CharsetUtil.UTF_8));
        return this;
    }

    public NettyEventSourceConnection data(String key, Object value) {
        putData(key, value);
        return this;
    }

    public NettyEventSourceConnection close() {
        closeChannel();
        return this;
    }
}

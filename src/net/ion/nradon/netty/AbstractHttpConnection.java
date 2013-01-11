package net.ion.nradon.netty;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

import net.ion.nradon.HttpConnection;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;

public abstract class AbstractHttpConnection  implements HttpConnection {

    private final Executor executor;
    private final NettyHttpRequest nettyHttpRequest;
    private final ChannelHandlerContext ctx;

    public AbstractHttpConnection(ChannelHandlerContext ctx, NettyHttpRequest nettyHttpRequest, Executor executor) {
        this.ctx = ctx;
        this.nettyHttpRequest = nettyHttpRequest;
        this.executor = executor;
    }

    protected ChannelFuture writeMessage(Object message) {
        return ctx.getChannel().write(message);
    }

    protected void closeChannel() {
        ctx.getChannel().write(ChannelBuffers.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    protected void putData(String key, Object value) {
        data().put(key, value);
    }

    public NettyHttpRequest httpRequest() {
        return nettyHttpRequest;
    }

    public Map<String, Object> data() {
        return nettyHttpRequest.data();
    }

    public Object data(String key) {
        return data().get(key);
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
}


package net.ion.nradon.netty;

import java.util.Iterator;
import java.util.concurrent.Executor;

import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.helpers.RadonException;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

public class NettyHttpControl implements HttpControl {
    private final Iterator<HttpHandler> handlerIterator;
    private final Executor executor;
    private final ChannelHandlerContext ctx;
    private final NettyHttpRequest webbitHttpRequest;
    private final org.jboss.netty.handler.codec.http.HttpRequest nettyHttpRequest;
    private final org.jboss.netty.handler.codec.http.HttpResponse nettyHttpResponse;
    private final Thread.UncaughtExceptionHandler exceptionHandler;
    private final Thread.UncaughtExceptionHandler ioExceptionHandler;

    private HttpRequest defaultRequest;
    private HttpResponse webbitHttpResponse;
    private HttpControl defaultControl;
    private NettyWebSocketConnection webSocketConnection;
    private NettyEventSourceConnection eventSourceConnection;

    public NettyHttpControl(Iterator<HttpHandler> handlerIterator,
                            Executor executor,
                            ChannelHandlerContext ctx,
                            NettyHttpRequest webbitHttpRequest,
                            NettyHttpResponse webbitHttpResponse,
                            org.jboss.netty.handler.codec.http.HttpRequest nettyHttpRequest,
                            org.jboss.netty.handler.codec.http.HttpResponse nettyHttpResponse,
                            Thread.UncaughtExceptionHandler exceptionHandler,
                            Thread.UncaughtExceptionHandler ioExceptionHandler) {
        this.handlerIterator = handlerIterator;
        this.executor = executor;
        this.ctx = ctx;
        this.webbitHttpRequest = webbitHttpRequest;
        this.webbitHttpResponse = webbitHttpResponse;
        this.nettyHttpRequest = nettyHttpRequest;
        this.nettyHttpResponse = nettyHttpResponse;
        this.ioExceptionHandler = ioExceptionHandler;
        this.exceptionHandler = exceptionHandler;

        defaultRequest = webbitHttpRequest;
        defaultControl = this;
    }

    public void nextHandler() {
        nextHandler(defaultRequest, webbitHttpResponse, defaultControl);
    }

    public void nextHandler(HttpRequest request, HttpResponse response) {
        nextHandler(request, response, defaultControl);
    }

    public void nextHandler(HttpRequest request, HttpResponse response, HttpControl control) {
        this.defaultRequest = request;
        this.webbitHttpResponse = response;
        this.defaultControl = control;
        if (handlerIterator.hasNext()) {
            HttpHandler handler = handlerIterator.next();
            try {
                handler.handleHttpRequest(request, response, control);
            } catch (Throwable e) {
                response.error(e);
            }
        } else {
            response.status(404).end();
        }
    }

    public WebSocketConnection upgradeToWebSocketConnection(WebSocketHandler webSocketHandler) {
        NettyWebSocketConnection webSocketConnection = webSocketConnection();
        WebSocketConnectionHandler webSocketConnectionHandler = new WebSocketConnectionHandler(executor, exceptionHandler, ioExceptionHandler, webSocketConnection, webSocketHandler);
        performWebSocketHandshake(webSocketConnection, webSocketConnectionHandler);

        try {
            webSocketHandler.onOpen(webSocketConnection);
        } catch (Throwable e) {
            exceptionHandler.uncaughtException(Thread.currentThread(), new RadonException(e));
        }
        return webSocketConnection;
    }

    public NettyWebSocketConnection webSocketConnection() {
        if (webSocketConnection == null) {
            webSocketConnection = new NettyWebSocketConnection(executor, webbitHttpRequest, ctx, null);
        }
        return webSocketConnection;
    }

    public NettyEventSourceConnection upgradeToEventSourceConnection(EventSourceHandler eventSourceHandler) {
        NettyEventSourceConnection eventSourceConnection = eventSourceConnection();
        EventSourceConnectionHandler eventSourceConnectionHandler = new EventSourceConnectionHandler(executor, exceptionHandler, ioExceptionHandler, eventSourceConnection, eventSourceHandler);
        performEventSourceHandshake(eventSourceConnectionHandler);

        try {
            eventSourceHandler.onOpen(eventSourceConnection);
        } catch (Exception e) {
            exceptionHandler.uncaughtException(Thread.currentThread(), new RadonException(e));
        }
        return eventSourceConnection;
    }

    public NettyEventSourceConnection eventSourceConnection() {
        if (eventSourceConnection == null) {
            eventSourceConnection = new NettyEventSourceConnection(executor, webbitHttpRequest, ctx);
        }
        return eventSourceConnection;
    }

    public Executor handlerExecutor() {
        return executor;
    }

    public void execute(Runnable command) {
        handlerExecutor().execute(command);
    }

    private void performEventSourceHandshake(ChannelHandler eventSourceConnectionHandler) {
        nettyHttpResponse.setStatus(HttpResponseStatus.OK);
        nettyHttpResponse.addHeader("Content-Type", "text/event-stream");
        nettyHttpResponse.addHeader("Transfer-Encoding", "identity");
        nettyHttpResponse.addHeader("Connection", "keep-alive");
        nettyHttpResponse.addHeader("Cache-Control", "no-cache");
        nettyHttpResponse.setChunked(false);
        ctx.getChannel().write(nettyHttpResponse);
        getReadyToSendEventSourceMessages(eventSourceConnectionHandler);
    }

    private void getReadyToSendEventSourceMessages(ChannelHandler eventSourceConnectionHandler) {
        ChannelPipeline p = ctx.getChannel().getPipeline();
        StaleConnectionTrackingHandler staleConnectionTracker = (StaleConnectionTrackingHandler) p.remove("staleconnectiontracker");
        staleConnectionTracker.stopTracking(ctx.getChannel());
        p.remove("aggregator");
        p.replace("handler", "ssehandler", eventSourceConnectionHandler);
    }


    private void performWebSocketHandshake(NettyWebSocketConnection webSocketConnection, ChannelHandler webSocketConnectionHandler) {
        WebSocketVersion[] versions = new WebSocketVersion[]{
                new Hybi(nettyHttpRequest, nettyHttpResponse),
                new Hixie76(nettyHttpRequest, nettyHttpResponse),
                new Hixie75(nettyHttpRequest, nettyHttpResponse)
        };

        Channel channel = ctx.getChannel();
        ChannelPipeline pipeline = channel.getPipeline();

        for (WebSocketVersion webSocketVersion : versions) {
            if (webSocketVersion.matches()) {
                ChannelHandler webSocketFrameDecoder = webSocketVersion.createDecoder();
                getReadyToReceiveWebSocketMessages(webSocketFrameDecoder, webSocketConnectionHandler, pipeline, channel);
                webSocketVersion.prepareHandshakeResponse(webSocketConnection);
                channel.write(nettyHttpResponse);
                getReadyToSendWebSocketMessages(webSocketVersion.createEncoder(), pipeline);
                break;
            }
        }
    }

    private void getReadyToReceiveWebSocketMessages(ChannelHandler webSocketFrameDecoder, ChannelHandler webSocketConnectionHandler, ChannelPipeline p, Channel channel) {
        StaleConnectionTrackingHandler staleConnectionTracker = (StaleConnectionTrackingHandler) p.remove("staleconnectiontracker");
        staleConnectionTracker.stopTracking(channel);
        p.remove("aggregator");
        p.replace("decoder", "wsdecoder", webSocketFrameDecoder);
        p.replace("handler", "wshandler", webSocketConnectionHandler);
    }

    private void getReadyToSendWebSocketMessages(ChannelHandler webSocketFrameEncoder, ChannelPipeline p) {
        p.replace("encoder", "wsencoder", webSocketFrameEncoder);
    }

}

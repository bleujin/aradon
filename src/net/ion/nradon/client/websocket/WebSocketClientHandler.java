package net.ion.nradon.client.websocket;

import net.ion.nradon.netty.codec.http.websocketx.BinaryWebSocketFrame;
import net.ion.nradon.netty.codec.http.websocketx.CloseWebSocketFrame;
import net.ion.nradon.netty.codec.http.websocketx.PongWebSocketFrame;
import net.ion.nradon.netty.codec.http.websocketx.TextWebSocketFrame;
import net.ion.nradon.netty.codec.http.websocketx.WebSocketClientHandshaker;
import net.ion.nradon.netty.codec.http.websocketx.WebSocketFrame;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;

public class WebSocketClientHandler extends SimpleChannelUpstreamHandler {

	private final WebSocketClientHandshaker handshaker;
	private IResponseMessageHandler shandler;

	private WebSocketClientHandler(WebSocketClientHandshaker handshaker, IResponseMessageHandler shandler) {
		this.handshaker = handshaker;
		this.shandler = shandler;
	}

	public final static WebSocketClientHandler create(WebSocketClientHandshaker handshaker) {
		return new WebSocketClientHandler(handshaker, IResponseMessageHandler.DEBUG);
	}

	public final static WebSocketClientHandler create(WebSocketClientHandshaker handshaker, IResponseMessageHandler shandler) {
		return new WebSocketClientHandler(handshaker, shandler);
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		shandler.onDisconnected();
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		Channel ch = ctx.getChannel();
		if (!handshaker.isHandshakeComplete()) {
			handshaker.finishHandshake(ch, (HttpResponse) e.getMessage());

			shandler.onOpen();
			return;
		}

		if (e.getMessage() instanceof HttpResponse) {
			HttpResponse response = (HttpResponse) e.getMessage();
			throw new Exception("Unexpected HttpResponse (status=" + response.getStatus() + ", content=" + response.getContent().toString(CharsetUtil.UTF_8) + ")");
		}

		WebSocketFrame frame = (WebSocketFrame) e.getMessage();
		if (frame instanceof TextWebSocketFrame) {
			TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
			shandler.onMessage(textFrame);
		} else if (frame instanceof BinaryWebSocketFrame) {
			BinaryWebSocketFrame binFrame = (BinaryWebSocketFrame) frame;
			shandler.onBinMessage(binFrame);
		} else if (frame instanceof PongWebSocketFrame) {
			shandler.onPong();
		} else if (frame instanceof CloseWebSocketFrame) {
			ch.close();
			shandler.onClosed();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		final Throwable t = e.getCause();
		t.printStackTrace();
		e.getChannel().close();
	}
}

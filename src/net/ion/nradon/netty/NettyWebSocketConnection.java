package net.ion.nradon.netty;

import java.util.concurrent.Executor;

import net.ion.framework.util.ObjectUtil;
import net.ion.nradon.WebSocketConnection;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.websocket.DefaultWebSocketFrame;
import org.jboss.netty.util.CharsetUtil;

public class NettyWebSocketConnection extends AbstractHttpConnection implements WebSocketConnection {

    private final byte[] outboundMaskingKey;
    private String version;
    private boolean hybi;

    public NettyWebSocketConnection(Executor executor, NettyHttpRequest nettyHttpRequest, ChannelHandlerContext ctx, byte[] outboundMaskingKey) {
        super(ctx, nettyHttpRequest, executor);
        this.outboundMaskingKey = outboundMaskingKey;
    }

	public NettyWebSocketConnection send(String message) {
		if (hybi) {
			writeMessage(new EncodingHybiFrame(Opcodes.OPCODE_TEXT, true, 0, outboundMaskingKey, ChannelBuffers.copiedBuffer(message, CharsetUtil.UTF_8)));
		} else {
			writeMessage(new DefaultWebSocketFrame(message));
		}
		return this ;
	}
	
	public ChannelFuture sendFuture(String message) {
		if (hybi) {
			return writeMessage(new EncodingHybiFrame(Opcodes.OPCODE_TEXT, true, 0, outboundMaskingKey, ChannelBuffers.copiedBuffer(message, CharsetUtil.UTF_8)));
		} else {
			return writeMessage(new DefaultWebSocketFrame(message));
		}	
	}
	

	public NettyWebSocketConnection send(byte[] message) {
		writeMessage(new EncodingHybiFrame(Opcodes.OPCODE_BINARY, true, 0, outboundMaskingKey, ChannelBuffers.wrappedBuffer(message)));
		return this ;
	}
	
    public NettyWebSocketConnection send(byte[] message, int offset, int length) {
        writeMessage(new EncodingHybiFrame(Opcodes.OPCODE_BINARY, true, 0, outboundMaskingKey, ChannelBuffers.copiedBuffer(message, offset, length)));
        return this;
    }


    public NettyWebSocketConnection ping(byte[] message) {
        writeMessage(new EncodingHybiFrame(Opcodes.OPCODE_PING, true, 0, outboundMaskingKey, ChannelBuffers.copiedBuffer(message)));
        return this;
    }

    public NettyWebSocketConnection pong(byte[] message) {
        writeMessage(new EncodingHybiFrame(Opcodes.OPCODE_PONG, true, 0, outboundMaskingKey, ChannelBuffers.copiedBuffer(message)));
        return this;
    }
    
    public NettyWebSocketConnection close() {
        if (hybi) {
            writeMessage(new EncodingHybiFrame(Opcodes.OPCODE_CLOSE, true, 0, outboundMaskingKey, ChannelBuffers.buffer(0))).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    closeChannel();
                }
            });
        } else {
            closeChannel();
        }
        return this;
    }

	public NettyWebSocketConnection data(String key, Object value) {
		putData(key, value);
		return this;
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

	public String getString(String key) {
		return ObjectUtil.toString(data(key));
	}
}

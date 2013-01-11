package net.ion.nradon.netty;

import org.jboss.netty.channel.ChannelHandler;

public interface WebSocketVersion {
    boolean matches();

    void prepareHandshakeResponse(NettyWebSocketConnection webSocketConnection);

    ChannelHandler createDecoder();

    ChannelHandler createEncoder();
}

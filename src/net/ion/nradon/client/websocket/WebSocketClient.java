package net.ion.nradon.client.websocket;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.Executors;

import net.ion.nradon.netty.codec.http.websocketx.BinaryWebSocketFrame;
import net.ion.nradon.netty.codec.http.websocketx.CloseWebSocketFrame;
import net.ion.nradon.netty.codec.http.websocketx.PingWebSocketFrame;
import net.ion.nradon.netty.codec.http.websocketx.TextWebSocketFrame;
import net.ion.nradon.netty.codec.http.websocketx.WebSocketClientHandshaker;
import net.ion.nradon.netty.codec.http.websocketx.WebSocketClientHandshakerFactory;
import net.ion.nradon.netty.codec.http.websocketx.WebSocketVersion;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpRequestEncoder;
import org.jboss.netty.handler.codec.http.HttpResponseDecoder;

public class WebSocketClient {

	// http://bloodguy.tistory.com/entry/HTML5-WebSocket-%EC%84%9C%EB%B2%84%EC%9D%98-handshake
	
	private ClientBootstrap bootstrap ;
	private Channel ch ;
	private IResponseMessageHandler responseHandler = IResponseMessageHandler.DEBUG ;

	private WebSocketClient(IResponseMessageHandler responseHandler) {
		this.responseHandler = responseHandler ;
	}
	
	public final static WebSocketClient create(){
		return create(IResponseMessageHandler.DEBUG) ;
	}
	
	public final static WebSocketClient create(IResponseMessageHandler serverHandler){
		return new WebSocketClient(serverHandler) ;
	}

	public void connect(URI uri) throws Exception {
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newSingleThreadExecutor(), Executors.newSingleThreadExecutor()));

		String protocol = uri.getScheme();
		if (!protocol.equals("ws")) {
			throw new IllegalArgumentException("Unsupported protocol: " + protocol);
		}

		HashMap<String, String> customHeaders = new HashMap<String, String>();
		customHeaders.put("MyHeader", "X-AradonWSClient");

		// Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
		// If you change it to V00, ping is not supported and remember to change HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
		final WebSocketClientHandshaker handshaker = new WebSocketClientHandshakerFactory().newHandshaker(uri, WebSocketVersion.V13, null, false, customHeaders);
		final WebSocketClientHandler clientHandler = WebSocketClientHandler.create(handshaker, responseHandler);
		
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();

				pipeline.addLast("decoder", new HttpResponseDecoder());
				pipeline.addLast("encoder", new HttpRequestEncoder());
				pipeline.addLast("ws-handler", clientHandler);
				return pipeline;
			}
		});

		// Connect
		ChannelFuture future = bootstrap.connect(new InetSocketAddress(uri.getHost(), uri.getPort()));
		future.awaitUninterruptibly() ; //.rethrowIfFailed();

		ch = future.getChannel();
		handshaker.handshake(ch).awaitUninterruptibly() ; // .rethrowIfFailed();
	}
	
	public void ping() throws InterruptedException{
		waitUntilConnected() ;
		ch.write(new PingWebSocketFrame(ChannelBuffers.copiedBuffer(new byte[] { 1, 2, 3, 4, 5, 6 })));
	}
	
	public ChannelFuture sendMessage(String msg) throws InterruptedException{
		waitUntilConnected() ;
		return ch.write(new TextWebSocketFrame(msg));
	}
	
	public ChannelFuture sendMessage(ChannelBuffer binaryData) throws InterruptedException{
		waitUntilConnected() ;
		return ch.write(new BinaryWebSocketFrame(binaryData)) ;
	}
	

	public void disconnect() throws InterruptedException{
		try {
			waitUntilConnected() ;
			ch.write(new CloseWebSocketFrame());

			// WebSocketClientHandler will close the connection when the server responds to the CloseWebSocketFrame.
			ch.getCloseFuture().awaitUninterruptibly();
		} finally {
			if (ch != null) {
				ch.close();
			}
			bootstrap.releaseExternalResources();
		}
	}

	private void waitUntilConnected() throws InterruptedException {
		int tryCount = 0 ;
		while(! isConnected()){
			Thread.sleep(100) ;
			if (tryCount++ > 10) throw new InterruptedException("cant connect to server") ; 
		}
	}

	private boolean isConnected(){
		return responseHandler.isConnected() ;
	}
	
	public IResponseMessageHandler getResponseHandler(){
		return responseHandler ;
	}
	
}
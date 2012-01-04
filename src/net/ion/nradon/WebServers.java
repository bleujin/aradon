package net.ion.nradon;

import java.net.SocketAddress;
import java.net.URI;
import java.util.concurrent.Executor;

import net.ion.nradon.netty.NettyWebServer;

public class WebServers {

	public static WebServer createWebServer(int port) {
		return new NettyWebServer(port);
	}

	public static WebServer createWebServer(Executor executor, int port) {
		return new NettyWebServer(executor, port);
	}

	public static WebServer createWebServer(Executor executor, SocketAddress socketAddress, URI publicUri) {
		return new NettyWebServer(executor, socketAddress, publicUri);
	}

}

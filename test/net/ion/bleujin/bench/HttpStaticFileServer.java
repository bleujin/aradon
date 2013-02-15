package net.ion.bleujin.bench;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class HttpStaticFileServer {

	private final int port;

	public HttpStaticFileServer(int port) {
		this.port = port;
	}

	public void run() {
		// Configure the server.
		ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

		// Set up the event pipeline factory.
		bootstrap.setPipelineFactory(new HttpStaticFileServerPipelineFactory());

		// Bind and start to accept incoming connections.
		bootstrap.bind(new InetSocketAddress(port));
	}

	public static void main(String[] args) throws Exception{
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = 8080;
		}
		File home = new File("./resource/radon");
		System.getProperties().put("user.dir", home.getCanonicalPath()) ;
		new HttpStaticFileServer(port).run();
	}
}
package net.ion.radon.core.server.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import net.ion.framework.util.Debug;
import net.ion.radon.core.server.netty.internal.NettyParams;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.restlet.Server;
import org.restlet.data.Parameter;
import org.restlet.engine.adapter.HttpServerHelper;
import org.restlet.util.Series;

public abstract class NettyServerHelper extends HttpServerHelper {

	private static final String CHILD_CHANNEL_PREFIX = "child.";

	private static final String RESTLET_NETTY_SERVER = "restlet-netty-server";

	private ChannelGroup allChannels = new DefaultChannelGroup(RESTLET_NETTY_SERVER);

	private final ChannelFactory factory;
	// private ChannelFactory factory = new OioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
	// private ChannelFactory factory = new NioServerSocketChannelFactory(Executors.newFixedThreadPool(10), Executors.newFixedThreadPool(10));

	public NettyServerHelper(Server server) {
		super(server);
		factory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool());
	}

	/**
	 * Returns the Netty pipeline factory.
	 * 
	 * @return The Netty pipeline factory.
	 */
	protected abstract ChannelPipelineFactory getPipelineFatory();

	@Override
	public synchronized void start() throws Exception {
		super.start() ;
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		bootstrap.setPipelineFactory(getPipelineFatory());

		// Copy the parameters as channel options
		setServerParameters(bootstrap);

		int port = getHelped().getPort();
		Channel channel = bootstrap.bind(new InetSocketAddress(port));
		InetSocketAddress address = (InetSocketAddress) channel.getLocalAddress();
		setEphemeralPort(address.getPort());
		allChannels.add(channel);
		getLogger().log(Level.INFO, "Started Netty " + getProtocols() + " server on port " + getHelped().getPort());
	}

	/**
	 * <p>
	 * Pass netty channel parameters through bootstrap.
	 * </p>
	 * 
	 * @param bootstrap
	 *            - server bootstrap instance
	 */
	private void setServerParameters(final ServerBootstrap bootstrap) {
		Series<Parameter> options = getHelpedParameters();

		for (Parameter option : options) {
			String paramName = option.getName();

			if (paramName.startsWith(CHILD_CHANNEL_PREFIX)) {
				paramName = option.getName().substring(CHILD_CHANNEL_PREFIX.length());
			}

			try {
				NettyParams param = NettyParams.valueOf(paramName);
				if (param != null) {
					final Object value = param.getValue(option.getValue());
					if ((value != null) && (param.isChannelOption())) {
						bootstrap.setOption(option.getName(), value);
					}
				}
			} catch (IllegalArgumentException iae) {
				getLogger().log(Level.FINE, "Unable to process the \"" + paramName + "\" parameter");
			}
		}

	}

	@Override
	public synchronized void stop() throws Exception {
		ChannelGroupFuture future = allChannels.close();
		future.awaitUninterruptibly();

		factory.releaseExternalResources();
		getLogger().log(Level.INFO, "Stopped Netty " + getProtocols() + " server");
		super.stop();
	}


}

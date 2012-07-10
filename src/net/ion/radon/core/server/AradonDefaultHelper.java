package net.ion.radon.core.server;

import java.util.Map.Entry;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.ConnectorConfig;

import org.restlet.Context;
import org.restlet.Server;
import org.restlet.util.ServerList;

public class AradonDefaultHelper implements AradonServerHelper {

	private Server server;

	private AradonDefaultHelper(Server server) {
		this.server = server;
	}

	public final static AradonDefaultHelper create(Context context, ConnectorConfig cfig, Aradon aradon) {

		Server server = new Server(context, cfig.getProtocol(), cfig.getPort(), aradon);

		for (Entry<String, String> entry : cfig.getParams().entrySet()) {
			server.getContext().getParameters().add(entry.getKey(), entry.getValue());
		}
		
		return new AradonDefaultHelper(server);
	}

	public void start() throws Exception {
		server.start();
	}

	public void stop() throws Exception {
		server.stop();
	}
}

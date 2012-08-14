package net.ion.radon.core.server;

import java.util.Map.Entry;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.ConnectorConfiguration;

import org.restlet.Context;
import org.restlet.Server;

public class AradonDefaultHelper implements AradonServerHelper {

	private Server server;

	private AradonDefaultHelper(Server server) {
		this.server = server;
	}

	public final static AradonDefaultHelper create(Context context, ConnectorConfiguration cfig, Aradon aradon) {

		Server server = new Server(context, cfig.protocol(), cfig.port(), aradon);

		for (Entry<String, String> entry : cfig.properties().entrySet()) {
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

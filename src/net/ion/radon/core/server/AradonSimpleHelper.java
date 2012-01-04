package net.ion.radon.core.server;

import java.util.Map.Entry;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.ConnectorConfig;

import org.restlet.Context;
import org.restlet.Server;
import org.restlet.util.ServerList;

public class AradonSimpleHelper implements AradonServerHelper {

	private Server server;

	private AradonSimpleHelper(Server server) {
		this.server = server;
	}

	public final static AradonSimpleHelper create(Context context, ConnectorConfig cfig, Aradon aradon) {
//		ConnectorHelper<Server> helper = new HttpServerHelper(null);
// 		ConnectorHelper<Server> helper = new HttpsServerHelper(null) ;
//		Engine.getInstance().getRegisteredServers().add(0, helper);

		Server server = new Server(context, cfig.getProtocol(), cfig.getPort(), aradon);

		for (Entry<String, String> entry : cfig.getParams().entrySet()) {
			server.getContext().getParameters().add(entry.getKey(), entry.getValue());
		}
		
		return new AradonSimpleHelper(server);
	}

	public void start() throws Exception {
		server.start();
	}

	public void stop() throws Exception {
		server.stop();
	}

	public void addTo(ServerList servers) {
		// servers.add(Protocol.HTTP, 0) ;
	}

}

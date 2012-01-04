package net.ion.radon.core.server;

import org.restlet.Server;
import org.restlet.util.ServerList;

public interface AradonServerHelper {

	void stop() throws Exception;

	void start()  throws Exception;

	void addTo(ServerList servers);

}

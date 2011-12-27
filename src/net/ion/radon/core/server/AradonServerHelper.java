package net.ion.radon.core.server;

import java.util.Map;

import org.restlet.Server;
import org.restlet.data.Protocol;

import net.ion.radon.core.config.XMLConfig;

public interface AradonServerHelper {

	void stop() throws Exception;

	void start()  throws Exception;

	Server getReal();

}

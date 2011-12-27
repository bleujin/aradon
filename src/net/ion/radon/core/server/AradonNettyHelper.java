package net.ion.radon.core.server;

import java.util.Map;
import java.util.Map.Entry;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.ConnectorConfig;

import org.restlet.Context;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.engine.ServerHelper;
import org.restlet.ext.jetty.HttpServerHelper;
import org.restlet.ext.jetty.HttpsServerHelper;
//import org.restlet.ext.netty.HttpServerHelper;
//import org.restlet.ext.netty.HttpsServerHelper;

public class AradonNettyHelper implements AradonServerHelper {

	private final ServerHelper helper ;
	private AradonNettyHelper(ServerHelper helper) {
		this.helper = helper ;
	}
	
	public final static AradonServerHelper create(Context context, ConnectorConfig cfig, Aradon aradon) {
		Server server = new Server(context, cfig.getProtocol(), cfig.getPort(), aradon);
		
		ServerHelper serverHelper ;
		if (cfig.getProtocol() == Protocol.HTTPS){
			serverHelper = new HttpsServerHelper(server);
		} else {
			
			serverHelper = new HttpServerHelper(server);
		}
		for (Entry<String, String> entry : cfig.getParams().entrySet()) {
			serverHelper.getContext().getParameters().add(entry.getKey(), entry.getValue());
		}
		return new AradonNettyHelper(serverHelper) ;
	}

	public Server getReal() {
		return helper.getHelped();
	}

	public void start() throws Exception {
		if (! helper.getHelped().isStarted()) helper.start() ;
	}

	public void stop() throws Exception {
		helper.getConnectorService().stop() ;
		helper.stop() ;
	}


}

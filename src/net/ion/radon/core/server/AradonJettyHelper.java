package net.ion.radon.core.server;

import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.ConnectorConfig;
import net.ion.radon.core.config.XMLConfig;

import org.restlet.Context;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.engine.ServerHelper;
import org.restlet.ext.jetty.HttpServerHelper;
import org.restlet.ext.jetty.HttpsServerHelper;

public class AradonJettyHelper implements AradonServerHelper{

	private ServerHelper helper ;
	private AradonJettyHelper(ServerHelper helper) {
		this.helper = helper ;
	}
	
	public final static AradonJettyHelper create(Context context, ConnectorConfig cfig, Aradon aradon){
		Server server = new Server(context, cfig.getProtocol(), cfig.getPort(), aradon);
		
		ServerHelper serverHelper ;
		if (cfig.getProtocol() == Protocol.HTTPS){
			// http://www.restlet.org/documentation/2.0/jse/ext/org/restlet/ext/jetty/HttpsServerHelper.html
			serverHelper = new HttpsServerHelper(server);
		} else {
			serverHelper = new HttpServerHelper(server);
		}
		for (Entry<String, String> entry : cfig.getParams().entrySet()) {
			serverHelper.getContext().getParameters().add(entry.getKey(), entry.getValue());
		}

		return new AradonJettyHelper(serverHelper) ;
	}

	public Server getReal() {
		return helper.getHelped() ;
	}

	public void start() throws Exception {
		helper.start() ;
	}

	public void stop() throws Exception {
		helper.getConnectorService().stop() ;
		helper.stop() ;
	}


}

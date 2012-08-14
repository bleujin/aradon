package net.ion.radon.core.server;

import java.util.Map.Entry;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.ConnectorConfiguration;
import net.ion.radon.core.server.simple.SimpleServerHelper;

import org.restlet.Context;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.engine.local.RiapServerHelper;

public class AradonSimpleHelper implements AradonServerHelper {

	private SimpleServerHelper helper;

	private AradonSimpleHelper(SimpleServerHelper server) {
		this.helper = server;
	}

	public final static AradonSimpleHelper create(Context context, ConnectorConfiguration cfig, Aradon aradon) {
//		ConnectorHelper<Server> helper = new HttpServerHelper(null);
// 		ConnectorHelper<Server> helper = new HttpsServerHelper(null) ;
//		Engine.getInstance().getRegisteredServers().add(0, helper);
		Engine.getInstance().getRegisteredServers().clear() ;
		Engine.getInstance().getRegisteredServers().add(new RiapServerHelper(null)) ;

		Server server = new Server(context, cfig.protocol(), cfig.port(), aradon);
		
		for (Entry<String, String> entry : cfig.properties().entrySet()) {
			server.getContext().getParameters().add(entry.getKey(), entry.getValue());
		}
		if (cfig.protocol() == Protocol.HTTP){
			return new AradonSimpleHelper(new net.ion.radon.core.server.simple.HttpServerHelper(server)) ;
		} else {
			return new AradonSimpleHelper(new net.ion.radon.core.server.simple.HttpsServerHelper(server)); 
		}
	}

	public void start() throws Exception {
		helper.start();
	}

	public void stop() throws Exception {
		helper.stop();
	}


}

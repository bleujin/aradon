package net.ion.radon.core.server;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.ConnectorConfig;
import net.ion.radon.core.config.ConnectorConfig.Engine;

import org.apache.commons.configuration.ConfigurationException;
import org.restlet.Context;

public class ServerFactory {

	public static AradonServerHelper create(Context context, Aradon aradon, ConnectorConfig cfig) throws ConfigurationException {

		AradonServerHelper result = null ;
		if (cfig.getEngine() == Engine.Netty) {
			result = AradonJettyHelper.create(context, cfig, aradon) ;
		} else if (cfig.getEngine() == Engine.Jetty) {
			result = AradonJettyHelper.create(context, cfig, aradon) ;
		} else {
			result = AradonSimpleHelper.create(context, cfig, aradon);
		}
		return result;

	}


}

package net.ion.radon.core.server;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.ConnectorConfiguration;
import net.ion.radon.core.config.ConnectorConfig.EngineType;

import org.apache.commons.configuration.ConfigurationException;
import org.restlet.Context;

public class ServerFactory {

	public static AradonServerHelper create(Context context, Aradon aradon, ConnectorConfiguration cfig) throws ConfigurationException {

		AradonServerHelper result = null ;
		if (cfig.engine() == EngineType.Simple) {
			result = AradonSimpleHelper.create(context, cfig, aradon) ;
		} else if (cfig.engine() == EngineType.Jetty) {
			result = AradonJettyHelper.create(context, cfig, aradon) ;
		} else if (cfig.engine() == EngineType.Netty) {
			result = AradonNettyHelper.create(context, cfig, aradon) ;
		} else {
			result = AradonDefaultHelper.create(context, cfig, aradon);
		}
		return result;

	}


}

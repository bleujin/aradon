package net.ion.radon.core.server;

import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.restlet.Context;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.ConnectorConfig;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.config.ConnectorConfig.Engine;

public class ServerFactory {

	public static AradonServerHelper create(Context context, Aradon aradon, ConnectorConfig cfig) throws ConfigurationException {

		AradonServerHelper result = null ;
		if (cfig.getEngine() == Engine.Netty) {
			result = AradonNettyHelper.create(context, cfig, aradon) ;
		} else {
			result = AradonJettyHelper.create(context, cfig, aradon);
		}
		return result;

	}


}

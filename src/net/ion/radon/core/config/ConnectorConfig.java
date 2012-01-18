package net.ion.radon.core.config;

import java.io.IOException;
import java.util.Map;

import net.ion.framework.util.StringUtil;

import org.apache.commons.configuration.ConfigurationException;
import org.restlet.data.Protocol;

public class ConnectorConfig {

	private XMLConfig config ;
	private int defaultPort ;

	private ConnectorConfig(XMLConfig config, int defaultPort) {
		this.config = config;
		this.defaultPort = defaultPort;
	}

	public final static ConnectorConfig makeJettyHTTPConfig(int port) {
		try {
			String config = "<connector-config engine=\"jetty\" />";
			return new ConnectorConfig(XMLConfig.load(config), port);
		} catch (ConfigurationException e) {
			throw new IllegalStateException(e.getMessage());
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage());
		}
	}

	public static final ConnectorConfig create(XMLConfig connConfig, int defaultPort) {
		return new ConnectorConfig(connConfig, defaultPort);
	}

	public enum Engine {
		Jetty, Netty, Unknown
	}

	public Protocol getProtocol() {
		String pro = config.getString("[@protocol]", "http");
		if ("https".equalsIgnoreCase(pro)) {
			return Protocol.HTTPS;
		}
		return Protocol.HTTP;
	}

	public Engine getEngine() {
		String pro = config.getString("[@engine]", "unknown");
		if ("netty".equalsIgnoreCase(pro)) {
			return Engine.Netty ;
		} else if ("jetty".equalsIgnoreCase(pro)) {
			return Engine.Jetty ;
		}
		return Engine.Unknown;
	}

	public int getPort() {
		int portNum = config.getInt("[@port]", defaultPort);
		return portNum;
	}

	public String getKeyStorePath() {
		return StringUtil.defaultString(config.findChild("parameter", "name", "keystorePath").getElementValue(), "nodara");
	}

	public String getKeyStorePassword() {
		return StringUtil.defaultString(config.findChild("parameter", "name", "keystorePassword").getElementValue(), "nodara");
	}

	public String getKeyPassword() {
		return StringUtil.defaultString(config.findChild("parameter", "name", "keyPassword").getElementValue(), "nodara");
	}

	public Map<String, String> getParams() {
		return config.childMap("parameter", "name");
	}
}

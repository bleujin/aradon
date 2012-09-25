package net.ion.radon.core.config;

import java.io.IOException;
import java.util.Map;

import net.ion.framework.util.StringUtil;
import net.ion.radon.core.except.ConfigurationException;

import org.restlet.data.Protocol;

public class ConnectorConfig {

	private XMLConfig config ;
	private int defaultPortNum ;

	private ConnectorConfig(XMLConfig config, int defaultPortNum) {
		this.config = config;
		this.defaultPortNum = defaultPortNum;
	}

	public final static ConnectorConfig makeJettyHTTPConfig(int port) {
		return makeHttpConfig(port, "jetty") ;
	}

	private final static ConnectorConfig makeHttpConfig(int port, String enginetype){
		try {
			String config = "<connector-config engine=\"" + enginetype + "\" />";
			return new ConnectorConfig(XMLConfig.load(config), port);
		} catch (ConfigurationException e) {
			throw new IllegalStateException(e.getMessage());
		} catch (IOException e) {
			throw new IllegalStateException(e.getMessage());
		}
	}

	public final static ConnectorConfig makeDefaultHTTPConfig(int port) {
		return makeHttpConfig(port, "none") ;
	}

	public final static ConnectorConfig makeSimpleHTTPConfig(int port) {
		return makeHttpConfig(port, "simple") ;
	}

	public final static ConnectorConfig makeNettyHTTPConfig(int port) {
		return makeHttpConfig(port, "netty") ;
	}


	public static final ConnectorConfig create(XMLConfig connConfig, int defaultPort) {
		return new ConnectorConfig(connConfig, defaultPort);
	}

	public enum EngineType {
		Jetty, Netty, Simple, Unknown
	}

	public Protocol getProtocol() {
		String pro = config.getString("[@protocol]", "http");
		if ("https".equalsIgnoreCase(pro)) {
			return Protocol.HTTPS;
		}
		return Protocol.HTTP;
	}

	public EngineType getEngine() {
		String pro = config.getString("[@engine]", "unknown");
		if ("simple".equalsIgnoreCase(pro)) {
			return EngineType.Simple ;
		} else if ("jetty".equalsIgnoreCase(pro)) {
			return EngineType.Jetty ;
		} else if ("netty".equalsIgnoreCase(pro)) {
			return EngineType.Netty ;
		}
		return EngineType.Unknown;
	}

	public int getPort() {
		int portNum = config.getInt("[@port]", defaultPortNum);
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

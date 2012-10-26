package net.ion.radon.core.config;

import java.util.Collections;
import java.util.Map;

import net.ion.framework.util.MapUtil;
import net.ion.radon.core.config.ConnectorConfig.EngineType;

import org.restlet.data.Protocol;

public class ConnectorConfiguration {

	private final EngineType engine ;
	private final Protocol protocol ;
	private int port ;
	private final Map<String, String> properties ;
	
	
	ConnectorConfiguration(EngineType engine, Protocol protocol, int port, Map<String, String> properties) {
		this.engine = engine ;
		this.protocol = protocol ;
		this.port = port ;
		this.properties = properties;
	}

	private static ConnectorConfiguration makeConfig(EngineType engine, int port) {
		return new ConnectorConfiguration(engine, Protocol.HTTP, port, MapUtil.<String, String>newMap());
	}

	public static ConnectorConfiguration create(EngineType engine, Protocol protocol, int port, Map<String, String> properties) {
		return new ConnectorConfiguration(engine, protocol, port, properties);
	}


	public final static ConnectorConfiguration makeRestletHTTPConfig(int port) {
		return makeConfig(EngineType.Unknown, port) ;
	}

	public final static ConnectorConfiguration makeSimpleHTTPConfig(int port) {
		return makeConfig(EngineType.Simple, port) ;
	}

	public final static ConnectorConfiguration makeNettyHTTPConfig(int port) {
		return makeConfig(EngineType.Netty, port) ;
	}
	
	public final static ConnectorConfiguration makeJettyHTTPConfig(int port) {
		return makeConfig(EngineType.Jetty, port) ;
	}
	
	
	public int port(){
		return port ;
	}
	
	public EngineType engine(){
		return engine ;
	}
	
	public Map<String, String> properties(){
		return Collections.unmodifiableMap(properties) ;
	}

	public Protocol protocol() {
		return protocol;
	}

	public SslParameter getSslParam(){
		return new SslParameter(this.properties) ;
	}

	public ConnectorConfiguration port(int port) {
		this.port = port ;
		return this;
	}

}

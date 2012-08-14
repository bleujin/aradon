package net.ion.radon.core.config;

import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.MapUtil;
import net.ion.radon.core.config.ConnectorConfig.EngineType;

import org.restlet.data.Protocol;

public class ConnectorConfigurationBuilder extends AbstractConfigurationBuilder<ConnectorConfiguration> implements IConfigurationChildBuilder {

	private int port = 9000 ;
	private EngineType engine = EngineType.Simple;
	private Map<String, String> properties = MapUtil.newMap() ;
	private Protocol protocol = Protocol.HTTP;
	
	ConnectorConfigurationBuilder(ServerConfigurationBuilder serverBuilder) {
		super(serverBuilder) ;
	}
	
	public ConnectorConfigurationBuilder addParameter(String key, String value){
		properties.put(key, value) ;
		return this ;
	}
	
	public ConnectorConfigurationBuilder port(int portNum){
		this.port = portNum ;
		return this ;
	}
	
	public ConnectorConfigurationBuilder engine(EngineType engine){
		this.engine = engine ;
		return this ;
	}
	
	public ConnectorConfigurationBuilder protocol(Protocol protocol){
		this.protocol = protocol ;
		return this ;
	}
	
	@Override
	public ConnectorConfiguration create() {
		return new ConnectorConfiguration(this.engine, this.protocol, this.port, this.properties);
	}

	public ConnectorConfigurationBuilder fromLoad(ConnectorConfig config) {
		engine(config.getEngine()).protocol(config.getProtocol()).port(config.getPort()) ;
		for(Entry<String, String> entry : config.getParams().entrySet()){
			addParameter(entry.getKey(), entry.getValue()) ;
		}
		return this ;
	}
	

}

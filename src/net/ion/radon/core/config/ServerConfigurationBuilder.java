package net.ion.radon.core.config;

import net.ion.framework.util.StringUtil;

public class ServerConfigurationBuilder extends AbstractConfigurationBuilder<ServerConfiguration> implements IConfigurationChildBuilder{

	private String id = "emanon";
	private String logConfigPath = StringUtil.defaultIfEmpty(System.getProperty("java.util.logging.config.file") , "./resource/config/log4j.properties");
	private ConnectorConfigurationBuilder connBuilder ;
	
	private int managePort = 45678 ;
	private boolean manageAutostart = false ;
	
	ServerConfigurationBuilder(ConfigurationBuilder builder) {
		super(builder) ;
		this.connBuilder = new ConnectorConfigurationBuilder(this) ;
	}
	
	public ServerConfigurationBuilder id(String id){
		if (StringUtil.isBlank(id)) return this ;
		
		this.id = id ;
		return this ;
	}
	
	public ConnectorConfigurationBuilder connector(){
		return connBuilder ;
	}
	
	public ServerConfigurationBuilder logConfigPath(String path){
		if (StringUtil.isBlank(path)) return this ;
		
		this.logConfigPath = path ;
		return this ;
	}
	
	public ServerConfigurationBuilder shellPort(int shellPort){
		this.managePort = shellPort ;
		return this ;
	}
	
	public ServerConfigurationBuilder enableShell(){
		this.manageAutostart = true ;
		return this ;
	}
	
	public ServerConfigurationBuilder disableShell(){
		this.manageAutostart = false ;
		return this ;
	}
	
	@Override
	public ServerConfiguration create() {
		return new ServerConfiguration(connBuilder.create(), id, logConfigPath, managePort, manageAutostart);
	}

	public ServerConfigurationBuilder fromLoad(XMLConfig config, int defaultPort) {
		connector().fromLoad(ConnectorConfig.create(config.firstChild("connector-config"), defaultPort)) ;

		id(config.getString("[@id]")).shellPort(config.getInt("[@id]", 4567)) ;
		
		return this ;
	}

	

}

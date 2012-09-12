package net.ion.radon.core.config;

import net.ion.framework.util.InstanceCreationException;

public class ConfigurationBuilder implements IConfigurationChildBuilder{

	private final AradonConfigurationBuilder aradonBuilder ;
	private final ServerConfigurationBuilder serverBuilder ;
	private final PlugInsConfigurationBuilder pluginBuilder ;
	
	ConfigurationBuilder() {
		this.aradonBuilder = new AradonConfigurationBuilder(this);
		this.serverBuilder = new ServerConfigurationBuilder(this) ;
		this.pluginBuilder = new PlugInsConfigurationBuilder(this) ;
	}

	public static ConfigurationBuilder newBuilder() {
		return new ConfigurationBuilder() ;
	}

	public AradonConfigurationBuilder aradon() {
		return aradonBuilder ;
	}
	
	public ServerConfigurationBuilder server(){
		return serverBuilder ;
	}

	public Configuration build() {
		return new Configuration(aradonBuilder.create(), serverBuilder.create(), pluginBuilder.create());
	}

	public ConfigurationBuilder toBuilder() {
		return this;
	}

	public static ConfigurationBuilder load(XMLConfig config) throws InstanceCreationException {
		return ConfigParser.create(config).parse() ;
	}

	public static AradonConfiguration loadPlugin(XMLConfig config) throws InstanceCreationException {
		return ConfigParser.create(config).parsePlugin().aradonBuilder.create() ;
	}


	
	public PlugInsConfigurationBuilder plugin() {
		return pluginBuilder;
	}

}


class ConfigParser {

	private XMLConfig config ;
	private ConfigurationBuilder builder ;
	public ConfigParser(XMLConfig config) {
		this.config = config ;
		this.builder = ConfigurationBuilder.newBuilder() ;
	}

	public static ConfigParser create(XMLConfig config) {
		return new ConfigParser(config);
	}
	
	
	ConfigurationBuilder parse() throws InstanceCreationException{
		
		builder.aradon().fromLoad(config) ;
		builder.plugin().formLoad(config) ;
		builder.server().fromLoad(config.firstChild("server-config"), 9000) ;
		
		
		return builder ;
	}
	

	ConfigurationBuilder parsePlugin() throws InstanceCreationException{
		builder.aradon().fromLoad(config) ;
		return builder ;
	}
	
	
}
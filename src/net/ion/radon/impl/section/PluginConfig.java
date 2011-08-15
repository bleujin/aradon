package net.ion.radon.impl.section;


import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.config.XMLConfig;

import org.apache.commons.configuration.ConfigurationException;

public class PluginConfig {
	
	public static final PluginConfig EMPTY = new PluginConfig("", XMLConfig.BLANK);
	
	private String fileName;
	private String name;
	private String version;
	private String description;
	private String license;
	private PluginProvider provider;
	
	private PluginConfig(String fileName,  XMLConfig config) {
		this.version = config.getString("version");
		this.name = config.getString("name");
		this.description = config.getString("description");
		this.license = config.getString("license");
		
		try {
			this.provider = new PluginProvider(config.firstChild("provider"));
		} catch (ConfigurationException e) {
			this.provider = PluginProvider.NONE;
		}
	}
	
	public final static PluginConfig create(String fileName, XMLConfig config) throws ConfigurationException{
		XMLConfig pconfig = config.firstChild("plugin") ;
		return new PluginConfig(fileName, ObjectUtil.coalesce(pconfig, XMLConfig.BLANK));
	}

	public String getName() {
		return name;
	}
	
	public String getVersion() {
		return version;
	}

	public String getDescription() {
		return description;
	}

	public String getLicense() {
		return license;
	}

	public String getProviderHomePage() {
		return provider.homepage;
	}
	
	public String getProviderDeveloper() {
		return provider.developer;
	}
	
	public String getProviderEmail() {
		return provider.email;
	}

	private static class PluginProvider {
		public final static PluginProvider NONE = new PluginProvider() ;
		private String homepage;
		private String developer;
		private String email;
		
		private PluginProvider(){
		}
		
		public PluginProvider(XMLConfig config){
			this.homepage = config.getString("homepage");
			this.developer = StringUtil.join(config.getList("developer"), ",");
			this.email = config.getString("email");
		}
	}

}

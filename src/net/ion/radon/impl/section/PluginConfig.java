package net.ion.radon.impl.section;


import java.io.File;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.config.XMLConfig;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;

public class PluginConfig {
	
	public static final PluginConfig EMPTY = new PluginConfig(XMLConfig.BLANK);
	
	private XMLConfig config ;
	private PluginProvider provider;
	
	private PluginConfig(XMLConfig config) {
		this.config = config ;
		try {
			this.provider = new PluginProvider(config.firstChild("provider"));
		} catch (ConfigurationException e) {
			this.provider = PluginProvider.NONE;
		}
	}
	
	public final static PluginConfig create(XMLConfig config) throws ConfigurationException{
		XMLConfig pconfig = config.firstChild("plugin") ;
		return new PluginConfig(ObjectUtil.coalesce(pconfig, XMLConfig.BLANK));
	}

	public String getId(){
		return config.getString("id") ;
	}
	
	public String getName() {
		return config.getString("name");
	}
	
	public String getVersion() {
		return config.getString("version");
	}

	public String getDescription() {
		return config.getString("description");
	}

	public String getLicense() {
		return config.getString("license");
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

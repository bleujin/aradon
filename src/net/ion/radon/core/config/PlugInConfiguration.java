package net.ion.radon.core.config;


import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.EnumClass.PlugInApply;

import org.apache.commons.configuration.ConfigurationException;

public class PlugInConfiguration {
	
	public static final PlugInConfiguration EMPTY = new PlugInConfiguration(null, "", PlugInApply.IGNORE, "", "", "", "", PlugInProvider.NONE);
	
	private AradonConfiguration aconfig ;
	private final String id ;
	private final String name ;
	private final String version ;
	private final String description ;
	private final String license ;
	private final PlugInProvider provider ;
	
	private PlugInConfiguration(AradonConfiguration aconfig, String id, PlugInApply plugInApply, String name, String version, String description, String license, PlugInProvider provider) {
		this.aconfig = aconfig ;
		this.id = id ;
		this.name = name ;
		this.version = version ;
		this.description = description ;
		this.license = license ;
		this.provider = provider ;
	}

	public final static PlugInConfiguration fromLoad(XMLConfig config) throws ConfigurationException, InstanceCreationException{
		XMLConfig pconfig = config.firstChild("plugin") ;
		PlugInProvider provider = new PlugInProvider(pconfig.getString("provider.homepage"), StringUtil.join(pconfig.getList("provider.developer"), ","), pconfig.getString("provider.email")) ;
	

		AradonConfiguration aconfig = ConfigurationBuilder.loadPluginConfig(config) ;
		
		return new PlugInConfiguration(aconfig, pconfig.getString("id"), PlugInApply.create(pconfig.getString("plugin[@apply]")) ,pconfig.getString("name"), pconfig.getString("version"), pconfig.getString("description"), pconfig.getString("license"), provider) ;
	}

	public String getId(){
		return id;
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

	private static class PlugInProvider {
		public final static PlugInProvider NONE = new PlugInProvider() ;
		private String homepage;
		private String developer;
		private String email;
		
		private PlugInProvider(){
		}
		
		public PlugInProvider(String homepage, String developer, String email){
			this.homepage = homepage ;
			this.developer = developer ;
			this.email = email ;
		}
	}
	
	public AradonConfiguration getAradonConfig(){
		return aconfig ;
	}

}

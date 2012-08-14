package net.ion.radon.util;

import net.ion.radon.core.config.ConfigurationBuilder;
import net.ion.radon.core.config.SectionConfigurationBuilder;

public class ShortConfigBuilder {

	private ConfigurationBuilder builder ;
	
	public ShortConfigBuilder(){
		this.builder = ConfigurationBuilder.newBuilder() ;
	}
	
	public static ShortConfigBuilder create() {
		return new ShortConfigBuilder();
	}

	public SectionConfigurationBuilder restSection(String sname) {
		return builder.aradon().sections().restSection(sname);
	}

}

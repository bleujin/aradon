package net.ion.radon.core.config;

public interface IConfigurationChildBuilder {
	
	AradonConfigurationBuilder aradon() ;
	
	Configuration build();
	
	ConfigurationBuilder toBuilder() ;
}

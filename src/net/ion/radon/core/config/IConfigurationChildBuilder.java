package net.ion.radon.core.config;

public interface IConfigurationChildBuilder {
	
	AradonConfigurationBuilder aradon() ;
	
	ServerConfigurationBuilder server() ;
	
	Configuration build();
	
	ConfigurationBuilder toBuilder() ;
}

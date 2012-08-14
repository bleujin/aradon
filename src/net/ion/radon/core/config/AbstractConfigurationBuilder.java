package net.ion.radon.core.config;

public abstract class AbstractConfigurationBuilder<T> implements IConfigurationChildBuilder{

	private final IConfigurationChildBuilder builder ;
	
	protected AbstractConfigurationBuilder(IConfigurationChildBuilder builder){
		this.builder = builder ;
	}

	
	public Configuration build(){
		return builder.build() ;
	}

	public ConfigurationBuilder toBuilder(){
		return builder.toBuilder() ;
	}

	// @Override
	public AradonConfigurationBuilder aradon(){
		return builder.aradon() ;
	}
	
	public boolean validate(){
		return true ;
	}
	
	public abstract T create() ;
	
}

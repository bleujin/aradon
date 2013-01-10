package net.ion.radon.core.except;

public class ConfigurationException extends RuntimeException{

	private ConfigurationException(Throwable cause) {
		super(cause) ;
	}

	public static ConfigurationException throwIt(Throwable cause) {
		return new ConfigurationException(cause);
	}

}

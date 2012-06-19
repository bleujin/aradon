package net.ion.radon.core.except;

public class ConfigurationException extends RuntimeException{

	private Exception cause ;
	private ConfigurationException(Exception cause) {
		this.cause = cause ;
	}

	public static ConfigurationException throwIt(Exception cause) {
		return new ConfigurationException(cause);
	}

}

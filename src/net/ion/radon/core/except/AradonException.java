package net.ion.radon.core.except;

public class AradonException extends Exception {

	public AradonException(String message, Exception cause) {
		super(message, cause) ;
	}

	public static AradonException create(Exception e) {
		return new AradonException(e.getMessage(), e);
	}

}

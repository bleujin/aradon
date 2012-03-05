package net.ion.radon.core.except;

public class AradonException extends Exception {

	public AradonException(String message, Exception cause) {
		super(message, cause) ;
	}

	private AradonException(String msg){
		super(msg) ;
	}
	
	public static AradonException create(Exception e) {
		return new AradonException(e.getMessage(), e);
	}

	

	public static AradonException create(String msg) {
		return new AradonException(msg);
	}
}

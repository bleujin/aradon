package net.ion.radon.upgrade;

public class UpgradeException extends Exception {

	private UpgradeException(String s, Throwable cause) {
		super(s, cause) ;
	}

	private UpgradeException(Throwable cause) {
		super(cause) ;
	}

	public static UpgradeException create(String s, Throwable cause){
		return new UpgradeException(s, cause) ;
	}

	public static UpgradeException create(Throwable cause){
		return new UpgradeException(cause) ;
	}
}

package net.ion.nradon.handler.exceptions;

/**
 * Exception handler that does nothing. Exceptions are silently discarded.
 * 
 * @see net.ion.nradon.Radon#connectionExceptionHandler(java.lang.Thread.UncaughtExceptionHandler)
 * @see net.ion.nradon.Radon#uncaughtExceptionHandler(java.lang.Thread.UncaughtExceptionHandler)
 */
public class SilentExceptionHandler implements Thread.UncaughtExceptionHandler {

	public void uncaughtException(Thread t, Throwable e) {
		// Do nothing.
	}

}

package net.ion.nradon.handler.exceptions;

/**
 * Exception handler that does nothing. Exceptions are silently discarded.
 * 
 * @see net.ion.nradon.WebServer#connectionExceptionHandler(java.lang.Thread.UncaughtExceptionHandler)
 * @see net.ion.nradon.WebServer#uncaughtExceptionHandler(java.lang.Thread.UncaughtExceptionHandler)
 */
public class SilentExceptionHandler implements Thread.UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// Do nothing.
	}

}

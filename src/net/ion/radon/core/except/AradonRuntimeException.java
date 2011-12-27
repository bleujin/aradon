package net.ion.radon.core.except;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ExceptionEvent;

/**
 * Marker for any exceptions in the Webbit stack.
 * 
 * This is used to ensure the exceptions we report to {@link Thread.UncaughtExceptionHandler}s are well documented and make it obvious that an error occurred in Webbit. This is particularly useful for projects that make heavy use of Netty in other libraries, since most of our exceptions come out of the Netty stack, and don't include Webbit code in their stack traces.
 */
public class AradonRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 8212455395690231426L;

	public AradonRuntimeException() {
	}

	public AradonRuntimeException(String message) {
		super(message);
	}

	public AradonRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public AradonRuntimeException(Throwable cause) {
		super(cause);
	}

	public static AradonRuntimeException fromExceptionEvent(ExceptionEvent e) {
		return new AradonRuntimeException(e.getCause().getMessage() + " on " + e.getChannel().toString(), e.getCause());
	}

	public static AradonRuntimeException fromException(Throwable t, Channel channel) {
		return new AradonRuntimeException(t.getMessage() + " on " + channel.toString(), t);
	}
}

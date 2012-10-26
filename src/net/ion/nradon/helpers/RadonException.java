package net.ion.nradon.helpers;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ExceptionEvent;


public class RadonException extends RuntimeException {
	private static final long serialVersionUID = 8212455395690231426L;

	public RadonException(String message) {
		super(message);
	}

	public RadonException(String message, Throwable cause) {
		super(message, cause);
	}

	public RadonException(Throwable cause) {
		super(cause);
	}

	public static RadonException fromExceptionEvent(ExceptionEvent e) {
		return fromException(e.getCause(), e.getChannel());
	}

	public static RadonException fromException(Throwable t, Channel channel) {
		String throwableStr = t != null ? t.getMessage() : "[null throwable]";
		String channelStr = channel != null ? channel.toString() : "[null channel]";
		return new RadonException(String.format("%s on %s", throwableStr, channelStr), t);
	}
}

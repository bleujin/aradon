package net.ion.nradon;

import java.util.concurrent.Executor;

import org.jboss.netty.channel.ChannelFuture;

public interface WebSocketConnection extends Executor, DataHolder {
	
	public final static String VAR_USERID = "$userid";
	public final static String VAR_USERNAME = "$username";
	public final static String VAR_USERSESSION = "$session";
	
	HttpRequest httpRequest();

	WebSocketConnection send(String message);
	
	ChannelFuture sendFuture(String message) ;

	WebSocketConnection send(byte[] message);

	WebSocketConnection send(byte[] message, int offset, int length);

	WebSocketConnection ping(byte[] message);
	
	WebSocketConnection pong(byte[] message);

	WebSocketConnection close();

	WebSocketConnection data(String key, Object value); // Override DataHolder to provide more specific return type.

	Executor handlerExecutor();

	String version();

	String getString(String key);
}

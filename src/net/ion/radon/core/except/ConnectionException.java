package net.ion.radon.core.except;

import net.ion.nradon.WebSocketConnection;

import org.jboss.netty.channel.ExceptionEvent;

public class ConnectionException extends RuntimeException{
	
	private static final long serialVersionUID = -574606220478672126L;
	private ExceptionEvent event ;
	private WebSocketConnection conn ;
	public ConnectionException(ExceptionEvent event, WebSocketConnection conn) {
		super(event.getCause()) ;
		this.event = event ;
		this.conn = conn ;
	}

	public ExceptionEvent getEvent(){
		return event ;
	}

	public WebSocketConnection getConnection(){
		return this.conn ;
	}
	
	public static ConnectionException fromExceptionEvent(ExceptionEvent e, WebSocketConnection conn) {
		return new ConnectionException(e, conn);
	}
	
}

package net.ion.nradon.ajax;

import java.util.ArrayList;
import java.util.List;

import net.ion.framework.util.Debug;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;

public class BroadEchoWebSocket implements WebSocketHandler {

	private List<WebSocketConnection> conns = new ArrayList<WebSocketConnection>() ;
	public void onOpen(WebSocketConnection connection) {
		connection.send("Hello! There are " + conns.size() + " other connections active");
		conns.add(connection) ;
		Debug.line(connection + " connected") ;
	}

	public void onClose(WebSocketConnection connection) {
		conns.remove(connection) ;
		Debug.line(connection + " disconnected") ;
	}

	public void onMessage(WebSocketConnection connection, String message) {
		for (final WebSocketConnection conn : conns) {
//			conn.send(message.toUpperCase() + " to " + conns.size()) ;
			conn.sendFuture(message.toUpperCase() + "(conns :" + conns.size() + ")").addListener(new ChannelFutureListener() {
				public void operationComplete(ChannelFuture future) throws Exception {
					Debug.line(conn, future.getCause(), future.isDone(), future.isSuccess(), future.getCause()) ;
				}
			}) ; // echo back message in upper
		}
	}

	public void onMessage(WebSocketConnection connection, byte[] message) {
	}

	public void onPong(WebSocketConnection connection, byte[] message) {
	}
	public void onPing(WebSocketConnection connection, byte[] message) {
	}

	List<WebSocketConnection> getConnList(){
		return conns ;
	}
}
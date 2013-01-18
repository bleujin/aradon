package net.ion.nradon.let;

import java.util.List;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.nradon.AbstractWebSocketResource;
import net.ion.nradon.WebSocketConnection;

public class SampleWebSocket extends AbstractWebSocketResource {
	private List<WebSocketConnection> conns = ListUtil.newList() ;
	public void onMessage(WebSocketConnection conn, String msg) throws Throwable {
		
		Debug.line(msg, conn.data(), getServiceContext()) ;
	}

	public void onMessage(WebSocketConnection conn, byte[] msg) throws Throwable {
		;
	}

	public void onOpen(WebSocketConnection conn) throws Exception {
		conns.add(conn) ;
	}

	public void onClose(WebSocketConnection conn) throws Exception {
		conns.remove(conn) ;
	}

	public void onPong(WebSocketConnection conn, byte[] msg) throws Throwable {
		
	}

	public void onPing(WebSocketConnection conn, byte[] msg) throws Throwable {
		
	}
}

package net.ion.nradon.ajax;

import java.util.List;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.nradon.AbstractWebSocketResource;
import net.ion.nradon.WebSocketConnection;

public class EchoWebSocketResource extends AbstractWebSocketResource {

	private List<WebSocketConnection> conns = ListUtil.newList();

	public void onMessage(WebSocketConnection conn, String msg) throws Throwable {
		conn.send(msg) ;
		Debug.line(msg, conn.data(), getServiceContext());
	}

	public void onMessage(WebSocketConnection conn, byte[] msg) throws Throwable {
		;
	}

	public void onOpen(WebSocketConnection conn) throws Exception {
		conns.add(conn);
	}

	public void onClose(WebSocketConnection conn) throws Exception {
		conns.remove(conn);
	}

	public void onPong(WebSocketConnection conn, byte[] msg) throws Throwable {

	}

	public void onPing(WebSocketConnection conn, byte[] msg) throws Throwable {

	}

	public List<WebSocketConnection> getConnections(){
		return conns ;
	}
	
}

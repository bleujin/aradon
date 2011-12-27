package net.ion.nradon.handler;

import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.framework.util.ListUtil;
import net.ion.nradon.WebServer;
import net.ion.nradon.WebServers;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.handler.StaticFileHandler;
import net.ion.radon.core.Aradon;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;


import junit.framework.TestCase;

public class TestQuick extends TestCase {

	
	public void testRun() throws Exception {
		WebServer webServer = WebServers.createWebServer(8080) ;
		
		Aradon aradon = AradonTester.create().register("", "/test", HelloWorldLet.class).getAradon() ;
		webServer
			.add("/a/.*/.*", new HelloWebSockets())
			.add("/test", new AradonHandler(aradon))
			.add(new StaticFileHandler("./resource/web")).start();
		
		System.out.println("Server running at " + webServer.getUri());

		new InfinityThread().startNJoin() ;
	}

	
	public void testPattern() throws Exception {
		Pattern p = Pattern.compile("/.*/.*/.*") ;
		Debug.line(p.matcher("/hello").matches()) ;
		Debug.line(p.matcher("/bleujin/topicid/timeout=123&abcd=dd").matches()) ;
	}
	
}

class HelloWebSockets implements WebSocketHandler {

	int connectionCount;

	private List<WebSocketConnection> conns = new ArrayList<WebSocketConnection>() ;
	public void onOpen(WebSocketConnection connection) {
		connection.send("Hello! There are " + connectionCount + " other connections active");
		connectionCount++;
		conns.add(connection) ;
	}

	public void onClose(WebSocketConnection connection) {
		connectionCount--;
		conns.remove(connection) ;
	}

	public void onMessage(WebSocketConnection connection, String message) {
		for (WebSocketConnection conn : conns) {
			conn.send(message.toUpperCase()); // echo back message in upper
		}
	}

	public void onMessage(WebSocketConnection connection, byte[] message) {
	}

	public void onPong(WebSocketConnection connection, String message) {
	}

}
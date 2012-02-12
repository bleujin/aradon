package net.ion.nradon.ajax;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import net.ion.bleujin.HelloWorldLet2;
import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.framework.util.ObjectUtil;
import net.ion.nradon.WebServer;
import net.ion.nradon.WebServers;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.handler.StaticFileHandler;
import net.ion.nradon.handler.aradon.AradonHandler;
import net.ion.nradon.handler.aradon.JSONMessagePacket;
import net.ion.nradon.handler.aradon.NradonClient;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;

public class WebSocketToAradon {

	@Test
	public void helloSocket() throws Exception {
		WebServer webServer = WebServers.createWebServer(8080) ;
		
		Aradon aradon = AradonTester.create().register("", "/test", HelloWorldLet2.class).getAradon() ;
		
		webServer
			.add("/a/.*/.*", new HelloWebSockets())
			.add("/test", AradonHandler.create(aradon))
			.add(new StaticFileHandler("./resource/web")).start();
		
		System.out.println("Server running at " + webServer.getUri());
		
		// Desktop.getDesktop().browse(new URI("http://" + InetAddress.getLocalHost().getHostAddress() + ":8080/client/sample.html")) ;

		new InfinityThread().startNJoin() ;
	}

	
	@Test
	public void testPattern() throws Exception {
		Pattern p = Pattern.compile("/.*/.*/.*") ;
		Debug.line(p.matcher("/hello").matches()) ;
		Debug.line(p.matcher("/bleujin/topicid/timeout=123&abcd=dd").matches()) ;
	}
	
	
	@Test
	public void aradonSocket() throws Exception {
		WebServer webServer = WebServers.createWebServer(8080) ;
		
		Aradon aradon = AradonTester.create().register("", "/test", HelloWorldLet2.class).getAradon() ;
		
		webServer
			.add("/aradon", new AradonWebSocket(aradon))
			.add("/test", AradonHandler.create(aradon))
			.add(new StaticFileHandler("./resource/web")).start();
		
		System.out.println("Server running at " + webServer.getUri());

		// Desktop.getDesktop().browse(new URI("http://" + InetAddress.getLocalHost().getHostAddress() + ":8080/client/aradon.html")) ;
		
		new InfinityThread().startNJoin() ;
	}
}

class AradonWebSocket implements WebSocketHandler {

	private NradonClient client ;
	private String prefix ;
	AradonWebSocket(Aradon aradon) throws UnknownHostException{
		this.client = NradonClient.create(aradon) ;
		this.prefix = "http://" +  InetAddress.getLocalHost().getHostAddress() ;
	}
	
	public void onClose(WebSocketConnection conn) throws Exception {
		Debug.line(conn + " close") ;
	}

	// {headers:{}, path:{}, method:{}, params:{key:val,key:val2}}
	public void onMessage(WebSocketConnection conn, String msg) throws Throwable {
		JSONMessagePacket msgPacket = JSONMessagePacket.load(msg) ;
	
		
		String uri = msgPacket.getString("path", "/");
		Method method = Method.valueOf(msgPacket.getString("method", "GET")) ;
		Form form = new Form() ;
		Map<String, ? extends Object> params = msgPacket.inner("params").toMap() ;
		for(Entry<String, ? extends Object> entry : params.entrySet()){
			form.add(entry.getKey(), ObjectUtil.toString(entry.getValue())) ;
		}
		Form headerForm = new Form() ;
		Map<String, ? extends Object> headers = msgPacket.inner("headers").toMap() ;
		for(Entry<String, ? extends Object> entry : headers.entrySet()){
			headerForm.add(entry.getKey(), ObjectUtil.toString(entry.getValue())) ;
		}
		
		Request request = new Request(method, prefix + uri) ;
		request.setEntity(form.getWebRepresentation()) ;
		request.getAttributes().put(RadonAttributeKey.ATTRIBUTE_HEADERS, headerForm) ;
		
		Response response = client.handle(request) ;
		conn.send(response.getEntityAsText()) ;
	}

	public void onMessage(WebSocketConnection conn, byte[] msg) throws Throwable {
		
	}

	public void onOpen(WebSocketConnection conn) throws Exception {
		Debug.line(conn + " open") ;
	}

	public void onPong(WebSocketConnection conn, String msg) throws Throwable {
		
	}
	
}



class HelloWebSockets implements WebSocketHandler {

	private List<WebSocketConnection> conns = new ArrayList<WebSocketConnection>() ;
	public void onOpen(WebSocketConnection connection) {
		connection.send("Hello! There are " + conns.size() + " other connections active");
		conns.add(connection) ;
	}

	public void onClose(WebSocketConnection connection) {
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
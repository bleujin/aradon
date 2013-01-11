package net.ion.nradon.ajax;

import java.lang.Thread.UncaughtExceptionHandler;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.bleujin.HelloWorldLet2;
import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.framework.util.ObjectUtil;
import net.ion.nradon.Radon;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.client.websocket.WebSocketClient;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.StaticFileHandler;
import net.ion.nradon.handler.aradon.JSONMessagePacket;
import net.ion.nradon.handler.aradon.NradonClient;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.except.ConnectionException;
import net.ion.radon.util.AradonTester;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ExceptionEvent;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;

public class WebSocketToAradon {

	@Test
	public void helloSocket() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", HelloWorldLet2.class).getAradon() ;
		final BroadEchoWebSockets handler = new BroadEchoWebSockets();

		Radon webServer = RadonConfiguration.newBuilder(8080)
			.add("/a/.*/.*", handler)
			.add("/test", aradon)
			.add(new StaticFileHandler("./resource/web/client"))
			.connectionExceptionHandler(new UncaughtExceptionHandler() {
				public void uncaughtException(Thread t, Throwable e) {
					ConnectionException ce = ConnectionException.class.cast(e) ;
					ExceptionEvent event = ((ConnectionException)e).getEvent();
					event.getChannel().getRemoteAddress() ;
					
					Debug.linec('#', t, e, event) ;
				}
			}).startRadon()  ;
		
		System.out.println("Server running at " + webServer.getConfig().publicUri());
		
		// Desktop.getDesktop().browse(new URI("http://" + InetAddress.getLocalHost().getHostAddress() + ":8080/client/sample.html")) ;

		
//		new Thread(){
//			public void run(){
//				while(true){
//					try {
//						Thread.sleep(1000) ;
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					Debug.line(handler.getConnList()) ;
//				}
//			}
//		}.start() ;
		
		new Thread(){
			public void run() {
				WebSocketClient client = WebSocketClient.create();
				try {
					client.connect(new URI("ws://127.0.0.1:8080/a/b/c")) ;
					int i = 0 ;
					while(true){
						client.sendMessage("Hi bleujin " + i++) ;
						Thread.sleep(5000) ;
					}
					
				} catch (URISyntaxException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					Debug.line() ;
					try {
						client.disconnect() ;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}.start() ;
		
		
		
		
		
		new InfinityThread().startNJoin() ;
	}

	
	@Test
	public void aradonSocket() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", HelloWorldLet2.class).getAradon() ;
		Radon webServer = RadonConfiguration.newBuilder(8080)
			.add("/aradon", AradonTester.create().register("", "/test", HelloWorldLet2.class).getAradon())
			.add("/test", aradon)
			.add(new StaticFileHandler("./resource/web")).startRadon() ;
		
		System.out.println("Server running at " + webServer.getConfig().publicUri());

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

	public void onPong(WebSocketConnection conn, byte[] msg) throws Throwable {
	}
	public void onPing(WebSocketConnection conn, byte[] msg) throws Throwable {
	}
}



class BroadEchoWebSockets implements WebSocketHandler {

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
			conn.sendFuture(message.toUpperCase() + " to " + conns.size()).addListener(new ChannelFutureListener() {
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
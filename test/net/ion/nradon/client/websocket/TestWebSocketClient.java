package net.ion.nradon.client.websocket;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.TimeoutThread;
import net.ion.nradon.Radon;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.config.RadonConfiguration;

import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestWebSocketClient {

	private Radon radon ;

	@Before
	public void setUp() throws Exception {
		radon = RadonConfiguration.newBuilder(9000).add("/websocket", new DebugHandler()).startRadon() ;
	}

	@After
	public void tearDown() throws Exception {
		if (radon != null) radon.stop() ;
	}
	
	@Test
	public void testWebSocket() throws Exception {
		WebSocketClient wclient = WebSocketClient.create() ;
		wclient.connect(new URI("ws://61.250.201.157:9000/websocket")) ;
		
		wclient.ping() ;
		
		for (int i : ListUtil.rangeNum(10)) {
			wclient.sendMessage("Message #" + i) ;
		}
		
		TimeoutThread.createWithMili(500).startNJoin() ;
		wclient.disconnect() ;
	}
	
	@Test
	public void testPath() throws Exception {
		radon.add("/path/{userId}", new DebugHandler()) ;
		
		WebSocketClient wclient = WebSocketClient.create() ;
		wclient.connect(new URI("ws://61.250.201.157:9000/path/bleujin")) ;
		
		wclient.ping() ;
		
		for (int i : ListUtil.rangeNum(10)) {
			wclient.sendMessage("Message #" + i) ;
		}
		
		TimeoutThread.createWithMili(500).startNJoin() ;
		wclient.disconnect() ;
	}
	
	
	@Test
	public void testBinary() throws Exception {
		
		WebSocketClient wclient = WebSocketClient.create() ;
		wclient.connect(new URI("ws://61.250.201.157:9000/websocket")) ;
		
		File file = new File("./resource/imsi/chart.png");

		FileChannel fc = new FileInputStream(file).getChannel();
		MappedByteBuffer fmbuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		wclient.sendMessage(ChannelBuffers.wrappedBuffer(fmbuffer)) ;
		fc.close() ;
		
		TimeoutThread.createWithMili(1000).startNJoin() ;
		wclient.disconnect() ;
	}
}

class DebugHandler implements WebSocketHandler {

	public void onClose(WebSocketConnection conn) throws Exception {
		Debug.line(conn + " onClose") ;
		conn.close() ;
	}

	public void onMessage(WebSocketConnection conn, String msg) throws Throwable {
		Debug.line(msg + " onMessage " + conn.data("userId")) ;
		conn.send(msg) ; // echo
	}

	public void onMessage(WebSocketConnection conn, byte[] bmsg) throws Throwable {
		Debug.line(conn + " onMessage(b)" + bmsg.length) ;
	}

	public void onOpen(WebSocketConnection conn) throws Exception {
		Debug.line(conn + " onOpen) "  + conn.data("userId")) ;
	}

	public void onPong(WebSocketConnection conn, byte[] msg) throws Throwable {
		Debug.line(conn + " onPong") ;
	}

	public void onPing(WebSocketConnection conn, byte[] msg) throws Throwable {
		Debug.line(conn + " onPing") ;
	}
}

package net.ion.nradon.ajax;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.nradon.Radon;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.radon.core.Aradon;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

public class WhenDisconnect {

	public void xtestNewAradon() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/hello", HelloWorldLet.class).getAradon(); 

		Radon server = RadonConfiguration.newBuilder(9000)
			.add("/echo", new WebSocketHandler() {
			
			public void onPong(WebSocketConnection websocketconnection, String s) throws Throwable {
				
			}
			
			public void onOpen(WebSocketConnection websocketconnection) throws Exception {
				Debug.line(websocketconnection) ;
			}
			
			public void onMessage(WebSocketConnection websocketconnection, byte[] abyte0) throws Throwable {
				
			}
			
			public void onMessage(WebSocketConnection websocketconnection, String s) throws Throwable {
				websocketconnection.send(s) ;
			}
			
			public void onClose(WebSocketConnection websocketconnection) throws Exception {
				
			}
		}).add(aradon).startRadon() ;
		new InfinityThread().startNJoin() ;
	}
	
}

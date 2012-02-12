package net.ion.nradon.handler;

import static org.junit.Assert.assertEquals;
import net.ion.nradon.WebServer;
import net.ion.nradon.WebServers;
import net.ion.nradon.handler.logging.LoggingHandler;
import net.ion.nradon.handler.logging.SimpleLogSink;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;

import org.junit.Test;
import org.restlet.data.Method;

public class TestAradonStaticFileHandler {

	@Test
	public void initCall() throws Exception{
		
		String dir = "./resource";
		WebServer server = WebServers.createWebServer(9000)
			.add(new LoggingHandler(new SimpleLogSink())) 
			.add(new AradonStaticFileHandler(dir)) ;
		server.start() ;
		
		
		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000") ;

		assertEquals(200, ac.createRequest("/ptest.prop").handle(Method.GET).getStatus().getCode()) ;
		assertEquals(200, ac.createRequest("/web/index.html").handle(Method.GET).getStatus().getCode()) ;
		//assertEquals(MediaType.TEXT_PLAIN, res.getEntity().getMediaType()) ;
		server.stop() ;
			
	}
	
}

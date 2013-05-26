package net.ion.radon.impl.let;

import java.io.File;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.nradon.Radon;
import net.ion.nradon.ajax.BroadEchoWebSocket;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.SimpleStaticFileHandler;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestDirLet {

	
	@Test
	public void getFile() throws Exception{
		Aradon aradon = AradonTester.create().register("", "/{file}", DirLet.class).getAradon() ;
		aradon.getServiceContext().putAttribute("base.dir", "./resource") ;
		
		aradon.start() ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		Response response = ac.createRequest("/ptest.prop").handle(Method.GET) ;
		
		Debug.debug(response.getEntityAsText()) ;
		
		aradon.stop() ;
		
	}
	
	@Test
	public void testSlide() throws Exception {
		Radon radon = RadonConfiguration.newBuilder(9000)
			.add("/websocket/{id}", new BroadEchoWebSocket())
			.add("/*", new SimpleStaticFileHandler(new File("./resource/slide/"))).createRadon() ;
		
		radon.start().get() ;
		
//		Aradon aradon = AradonTester.create().register("", "/{file}", "file", IMatchMode.STARTWITH, DirLet.class).getAradon() ;
//		aradon.getServiceContext().putAttribute("base.dir", "./resource/slide") ;
//		
//		aradon.startServer(9000) ;
		new InfinityThread().startNJoin() ;
	}
	
}

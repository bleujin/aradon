package net.ion.nradon.handler.aradon;

import net.ion.framework.util.InfinityThread;
import net.ion.nradon.WebServer;
import net.ion.nradon.WebServers;
import net.ion.radon.core.Aradon;
import net.ion.radon.impl.DownloadLet;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Test;

public class TestBigFile{

	
	@Test
	public void bigFile() throws Exception {
		WebServer server = WebServers.createWebServer(8000) ;
		Aradon aradon  = AradonTester.create().register("file", "/{filename}", DownloadLet.class)
						.register("", "/hello", HelloWorldLet.class).getAradon() ;
		server.add(AradonHandler.create(aradon)).start() ;
		// server.add(new StaticFileHandler("c://setup/download/")).start() ;
		
		new InfinityThread().startNJoin() ;
	}
	
}

package net.ion.nradon.handler;

import net.ion.nradon.WebServer;
import net.ion.nradon.WebServers;
import net.ion.nradon.handler.aradon.AradonHandler;
import net.ion.radon.InfinityThread;
import net.ion.radon.core.Aradon;
import net.ion.radon.util.AradonTester;

import org.junit.Test;

public class TestBigfileLet {
	
	@Test
	public void bigFile() throws Exception {
		WebServer server = WebServers.createWebServer(8000) ;
		Aradon aradon  = AradonTester.create().register("", "/{filename}", TestDownloadLet.class).getAradon() ;
		server.add(new AradonHandler(aradon)).start() ;
		// server.add(new StaticFileHandler("c://setup/download/")).start() ;
		
		new InfinityThread().startNJoin() ;
	}
	
}

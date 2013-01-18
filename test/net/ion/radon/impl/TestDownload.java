package net.ion.radon.impl;

import net.ion.framework.util.InfinityThread;
import net.ion.nradon.Radon;
import net.ion.nradon.handler.AradonStaticFileHandler;
import net.ion.nradon.handler.logging.LoggingHandler;
import net.ion.nradon.handler.logging.SimpleLogSink;
import net.ion.radon.core.Aradon;
import net.ion.radon.util.AradonTester;

import org.junit.Test;

public class TestDownload {

	
	@Test
	public void testNotModified() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/{filename}", DownloadLet.class).getAradon() ;
		
		aradon.startServer(9000) ;
		
		
		new InfinityThread().startNJoin() ;
	}
	
	
	@Test
	public void testStatticHandler() throws Exception {
		Aradon aradon = AradonTester.create().register("aradon", "/{filename}", DownloadLet.class).getAradon() ;
		final Radon radon = aradon.toRadon(9200)
			.add(new LoggingHandler(new SimpleLogSink()))
			.add("/download/.*", new AradonStaticFileHandler("C:/setup"))
//			.add(new SimpleStaticFileHandler("C:/setup/download")) ;
			.start().get() ;
		
		new InfinityThread().startNJoin() ;
	}
	
}

package net.ion.radon.impl;

import net.ion.framework.util.InfinityThread;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.StaticFileHandler;
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
		RadonConfiguration.newBuilder(9200)
			.add(new LoggingHandler(new SimpleLogSink()))
			.add("/aradon/.*", aradon)
			.add(new StaticFileHandler("C:/setup/download")).startRadon() ;
		
		
		new InfinityThread().startNJoin() ;
	}
	
}

package net.ion.radon.core.config;

import net.ion.radon.Options;
import net.ion.radon.core.AradonServer;

import org.junit.Test;

public class TestAradonServerRun {

	@Test
	public void testWhenStopCall() throws Exception {
		Options options = new Options(new String[]{"-config:./resource/config/readonly-config.xml", "-port:9050"});
		AradonServer as = new AradonServer(options) ;
		as.start() ;
		
		synchronized (this) {
			wait(300) ;
		}
		
		as.stop() ;
		// new InfinityThread().startNJoin() ;
	}
	
	@Test
	public void testWhenStart() throws Exception {
		Options options = new Options(new String[]{"-config:./resource/config/readonly-config.xml", "-port:9050"});
		AradonServer as = new AradonServer(options) ;
		as.start() ;
		
		as.stop() ;
	}
}

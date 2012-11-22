package net.ion.radon.server;

import net.ion.framework.util.InfinityThread;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.config.SectionConfiguration;
import net.ion.radon.impl.let.HelloWorldLet;

import org.junit.Test;

public class TestLog {

	@Test
	public void testAradonRun() throws Exception {
		Aradon aradon = Aradon.create() ;
		aradon.attach(SectionConfiguration.createBlank("")).attach(PathConfiguration.create("hello", "/hello", HelloWorldLet.class)) ;
		
		
		aradon.startServer(9000) ;
		
		new InfinityThread().startNJoin() ;
	}
	
	
}

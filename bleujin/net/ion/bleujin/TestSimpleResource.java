package net.ion.bleujin;

import junit.framework.TestCase;
import net.ion.framework.util.InfinityThread;
import net.ion.nradon.Radon;
import net.ion.nradon.handler.SimpleStaticFileHandler;
import net.ion.radon.core.Aradon;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

public class TestSimpleResource extends TestCase {

	
	public void testRun() throws Exception {
		
		Aradon aradon = AradonTester.create().register("test", "/", HelloWorldLet.class).getAradon() ;
		
		Radon radon = aradon.toRadon(9000) ;
		radon.add("/resource/*", new SimpleStaticFileHandler("./")) ;
		
		radon.start().get() ;
		new InfinityThread().startNJoin(); 
	}
}

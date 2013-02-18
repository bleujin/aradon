package net.ion.radon.core;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.Radon;

public class TestToRadon extends TestCase {

	public void testToRadon() throws Exception {
		Aradon aradon = Aradon.create("./test/net/ion/radon/core/test-config.xml") ;
		
		Radon radon = aradon.toRadon() ;
		
		for (HttpHandler handler : radon.getConfig().handlers()) {
			Debug.line(handler) ;
		}
		
//		radon.start().get() ;
//		new InfinityThread().startNJoin() ;
	}
}

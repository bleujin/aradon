package net.ion.radon.core.service;

import groovy.lang.GroovyShell;
import jline.ConsoleRunner;
import junit.framework.TestCase;

public class TestService extends TestCase{
	
	public void testLoad() throws Exception {
		GroovyService s = new GroovyShellService(4567) ;
		s.launch() ;
		
//		new GroovyConsoleService().launch();		
//		Thread.sleep(100000) ;
	}

	public static void main(String[] args) throws Exception {
		ConsoleRunner cs = new ConsoleRunner() ;
		cs.main(new String[0]) ;
	}
}

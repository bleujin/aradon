package net.ion.radon.script;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestScriptAll {

	
	public static Test suite(){
		
		TestSuite suite = new TestSuite("TestScript") ;
		suite.addTestSuite(TestGroovyFilter.class) ;
		suite.addTestSuite(TestRhinoFilter.class) ;
		
		return suite ;
	}
}

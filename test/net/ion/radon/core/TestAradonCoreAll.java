package net.ion.radon.core;

import net.ion.framework.util.Debug;
import net.ion.radon.core.filter.TestAdvanseFilter;
import net.ion.radon.core.filter.TestFilter;
import net.ion.radon.core.filter.TestFilterAll;
import net.ion.radon.script.TestGroovyFilter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class TestAradonCoreAll extends TestSuite{

	public static Test suite(){
		System.setProperty(Debug.PROPERTY_KEY, "off") ;
		TestSuite ts = new TestSuite("Aradon Core All") ;
		
		ts.addTest(TestFilterAll.suite()) ;

		ts.addTestSuite(TestAradonInit.class) ;
		ts.addTestSuite(TestLogging.class) ;
		ts.addTestSuite(TestPlugin.class) ;
		return ts ;
	}
}

package net.ion.radon.core.filter;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.radon.script.TestGroovyFilter;

public class TestAllFilter {

	public static Test suite(){
		System.setProperty(Debug.PROPERTY_KEY, "off") ;
		TestSuite ts = new TestSuite("Test All Filter") ;
		
		ts.addTestSuite(TestFilter.class) ;
		ts.addTestSuite(TestGroovyFilter.class) ;
		ts.addTestSuite(TestAdvanseFilter.class) ;
		return ts ;
	}
}

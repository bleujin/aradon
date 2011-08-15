package net.ion.radon.core.filter;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.radon.script.TestGroovyFilter;

public class TestFilterAll {

	public static Test suite(){
		System.setProperty(Debug.PROPERTY_KEY, "off") ;
		TestSuite ts = new TestSuite("Filter All") ;
		
		ts.addTestSuite(TestAdvanseFilter.class) ;
		ts.addTestSuite(TestFilter.class) ;
		ts.addTestSuite(TestGroovyFilter.class) ;
		return ts ;
	}
}

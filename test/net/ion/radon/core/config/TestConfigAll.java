package net.ion.radon.core.config;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.radon.TestARadonMethod;

public class TestConfigAll {
	
	public static Test suite(){
		System.setProperty(Debug.PROPERTY_KEY, "off") ;
		TestSuite ts = new TestSuite("Config All") ;
		
		ts.addTestSuite(TestConfigFile.class) ;
		ts.addTestSuite(TestContextAttribute.class) ;
		
		ts.addTestSuite(TestARadonMethod.class) ;
		return ts ;
	}
}

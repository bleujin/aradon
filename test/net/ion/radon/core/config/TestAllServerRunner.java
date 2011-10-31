package net.ion.radon.core.config;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.radon.TestARadonMethod;
import net.ion.radon.core.TestAradonInit;

public class TestAllServerRunner {
	
	public static Test suite(){
		System.setProperty(Debug.PROPERTY_KEY, "off") ;
		TestSuite ts = new TestSuite("Test Server Runner") ;
		
		ts.addTestSuite(TestConfigFile.class) ;
		ts.addTestSuite(TestContextAttribute.class) ;
		ts.addTestSuite(TestAradonInit.class) ;

		ts.addTestSuite(TestARadonMethod.class) ;
		return ts ;
	}
}

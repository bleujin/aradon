package net.ion.radon.core.let;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;

public class TestLetAll {
	
	public static Test suite(){
		System.setProperty(Debug.PROPERTY_KEY, "off") ;
		TestSuite ts = new TestSuite("Let All") ;
		
		ts.addTestSuite(TestGeneral.class) ;
		ts.addTestSuite(TestLet.class) ;
		ts.addTestSuite(TestRequest.class) ;
		ts.addTestSuite(TestResponse.class) ;
		ts.addTestSuite(TestPathInfo.class) ;
		
		ts.addTestSuite(TestInboundLet.class) ;
		ts.addTestSuite(TestAbstractLet.class);

		ts.addTestSuite(TestSection.class) ;

		return ts ;
	}
}

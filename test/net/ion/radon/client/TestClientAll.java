package net.ion.radon.client;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestClientAll extends TestCase{

	
	public static TestSuite suite(){
		TestSuite suite = new TestSuite() ;

		suite.addTestSuite(TestAradonClient.class) ;
		suite.addTestSuite(TestHttpClientTest.class) ;
//		suite.addTestSuite(TestSpeed.class) ;
		
		return suite ;
	}
}

package net.ion.radon.client;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestAllClient extends TestCase{

	
	public static TestSuite suite(){
		TestSuite suite = new TestSuite("Test All Client") ;

		suite.addTestSuite(TestAradonClient.class) ;
		suite.addTestSuite(TestRequestParameter.class) ;
		suite.addTestSuite(TestRequestHeader.class) ;
		suite.addTestSuite(TestSerialRequest.class) ;
		suite.addTestSuite(TestMultiPartRequest.class) ;
		
		suite.addTestSuite(TestClientCache.class) ;
		return suite ;
	}
}

package net.ion.radon.param;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.radon.param.request.TestAradonParameter;

public class TestParamAll {
	
	public static Test suite(){
		System.setProperty(Debug.PROPERTY_KEY, "off") ;
		TestSuite ts = new TestSuite("Param All") ;
		
		ts.addTestSuite(TestAradonParameter.class) ;
		ts.addTestSuite(TestParamterKey.class);
		return ts ;
	}
}

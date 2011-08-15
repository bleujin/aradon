package net.ion.radon;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.radon.core.TestAradonCoreAll;
import net.ion.radon.core.TestPlugin;
import net.ion.radon.core.config.TestConfigAll;
import net.ion.radon.core.filter.TestFilterAll;
import net.ion.radon.core.let.TestLetAll;
import net.ion.radon.core.let.TestMultiValueMap;
import net.ion.radon.impl.TestImplLetAll;
import net.ion.radon.param.TestParamAll;

public class TestAllAradon extends TestCase{
	
	public static Test suite(){
		System.setProperty(Debug.PROPERTY_KEY, "off") ;
		TestSuite ts = new TestSuite("Test Aradon All") ;
		
		ts.addTestSuite(TestPlugin.class) ;
		
		ts.addTest(TestConfigAll.suite());
		ts.addTest(TestAradonCoreAll.suite()) ;
		ts.addTest(TestLetAll.suite()) ;
		ts.addTest(TestImplLetAll.suite());
		ts.addTest(TestParamAll.suite());
		// test board
		// ts.addTestSuite(TestBoard.class) ;
		
		ts.addTestSuite(TestMultiValueMap.class) ;
		return ts ;
	}
	
	
}

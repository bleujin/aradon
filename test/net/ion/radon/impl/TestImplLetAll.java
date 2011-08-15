package net.ion.radon.impl;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;
import net.ion.radon.impl.let.TestHelloLet;
import net.ion.radon.impl.let.TestMonitorLet;

public class TestImplLetAll {
	
	public static Test suite(){
		System.setProperty(Debug.PROPERTY_KEY, "off") ;
		TestSuite ts = new TestSuite("Impl let All") ;
		
		ts.addTestSuite(TestHelloLet.class) ;
		ts.addTestSuite(TestMonitorLet.class) ;
		
		return ts ;
	}
}

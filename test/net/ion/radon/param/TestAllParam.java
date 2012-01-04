package net.ion.radon.param;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.ion.framework.util.Debug;

public class TestAllParam {
	
	public static Test suite(){
		System.setProperty(Debug.PROPERTY_KEY, "off") ;
		TestSuite ts = new TestSuite("Test Param All") ;
		
		return ts ;
	}
}

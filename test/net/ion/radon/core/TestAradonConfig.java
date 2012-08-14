package net.ion.radon.core;

import java.io.File;

import junit.framework.TestCase;

public class TestAradonConfig extends TestCase{

	public void testSystemSetting() throws Exception {
		Aradon aradon = Aradon.create() ;
		
		assertEquals("mercury", aradon.getGlobalConfig().server().id()) ;
		assertEquals(new File(".").getCanonicalPath(), System.getProperty("aradon.mercury.home.dir")) ;
		
	}
	
	public void testSystemProperty() throws Exception {
		System.setProperty("aradon.mercury[net.bleujin.sample].dir", "hello") ;
		assertEquals("hello", System.getProperty("aradon.mercury[net.bleujin.sample].dir")) ;
	}
}

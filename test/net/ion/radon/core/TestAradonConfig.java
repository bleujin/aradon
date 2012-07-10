package net.ion.radon.core;

import java.io.File;

import net.ion.framework.util.Debug;
import net.ion.radon.core.config.XMLConfig;
import junit.framework.TestCase;

public class TestAradonConfig extends TestCase{

	public void testSystemSetting() throws Exception {
		Aradon aradon = new Aradon() ;
		aradon.init(XMLConfig.BLANK) ;
		
		assertEquals("mercury", aradon.getConfig().getId()) ;
		assertEquals(new File(".").getCanonicalPath(), System.getProperty("aradon.mercury.home.dir")) ;
		
	}
	
	public void testSystemProperty() throws Exception {
		System.setProperty("aradon.mercury[net.bleujin.sample].dir", "hello") ;
		assertEquals("hello", System.getProperty("aradon.mercury[net.bleujin.sample].dir")) ;
	}
}

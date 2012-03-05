package net.ion.radon.core;

import net.ion.framework.util.Debug;

import org.junit.Test;

public class TestAradon {

	
	private String TEST_CONFIG_FILE = "./resource/config/readonly-config.xml" ;
	
	@Test
	public void setUp() throws Exception {
		Debug.setPrintLevel(Debug.Level.Debug) ;
		Aradon aradon = new Aradon() ;
		aradon.init(TEST_CONFIG_FILE) ;
		aradon.start() ;
	}
	


}


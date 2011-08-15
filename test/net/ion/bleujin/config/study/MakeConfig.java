package net.ion.bleujin.config.study;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.XMLConfiguration;

public class MakeConfig extends TestCase{

	XMLConfiguration config = null ;
	protected void setUp() throws Exception {
		super.setUp();
		config = new XMLConfiguration("./webapps/WEB-INF/employee.xml") ;
	}
	
	public void testXMLAttribute() throws Exception {
		assertEquals("default", config.getString("context[@name]")) ;
		assertEquals("admin", config.getString("context(1)[@name]")) ;
		

		Debug.debug(config.configurationAt("context(1)")) ;

	}
	
	public void testConfigurationAt() throws Exception {
		Configuration defConfig = config.configurationAt("context(0)") ;
		Debug.debug(defConfig.getString("path(1)[@url]")) ;
		
	}
	
	public void testList() throws Exception {
		Debug.debug(config.getMaxIndex("context")) ;
	}
	
	public void testSystem() throws Exception {
		
		Debug.debug(System.getProperty("sun.arch.data.model"), System.getProperty("os.arch")) ;
	}
}

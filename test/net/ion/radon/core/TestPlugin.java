package net.ion.radon.core;

import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.impl.section.PluginConfig;

public class TestPlugin extends TestAradon{

	
	public void testLoad() throws Exception {
		initAradon() ;
		
		Debug.debug(aradon.getChildren());
		SectionService section = aradon.getChildService("plugin") ;
		PluginConfig pconfig =  section.getPluginConfig() ;
		
		assertEquals("Sample Plugin", pconfig.getName());
		assertEquals("0.2", pconfig.getVersion());
		assertEquals("http://www.i-on.net", pconfig.getProviderHomePage());
		assertEquals("bleujin,heeya", pconfig.getProviderDeveloper());
	}
	

}

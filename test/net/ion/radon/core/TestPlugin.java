package net.ion.radon.core;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.impl.section.PluginConfig;

import org.junit.Test;

public class TestPlugin extends TestAradon {

	@Test
	public void testLoad() throws Exception {
		initAradon();

		Debug.debug(aradon.getChildren());
		SectionService section = aradon.getChildService("plugin");
		PluginConfig pconfig = section.getPluginConfig();

		assertEquals("Sample Plugin", pconfig.getName());
		assertEquals("0.2", pconfig.getVersion());
		assertEquals("http://www.i-on.net", pconfig.getProviderHomePage());
		assertEquals("bleujin,heeya", pconfig.getProviderDeveloper());
	}

}

package net.ion.radon.core.config;

import java.io.File;

import net.ion.framework.util.InfinityThread;
import net.ion.nradon.Radon;
import net.ion.radon.core.Aradon;

import org.junit.Before;
import org.junit.Test;

public class TestSectionConfig {

	private ConfigurationBuilder cbuilder;

	@Before
	public void setUp() throws Exception {
		this.cbuilder = ConfigurationBuilder.load(XMLConfig.create(new File("./resource/config/section-config.xml")));
	}

	@Test
	public void running() throws Exception {
		Radon radon = Aradon.create(cbuilder.build()).toRadon(9000).start().get();
		
		
		// Debug.line(IOUtil.toString(new URL("http://61.250.201.157:9000/hello").openStream())) ;
		
		new InfinityThread().startNJoin() ;
	}

	
}

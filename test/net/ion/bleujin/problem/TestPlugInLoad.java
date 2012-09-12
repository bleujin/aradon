package net.ion.bleujin.problem;

import static org.junit.Assert.assertEquals;

import java.io.File;

import net.ion.radon.Options;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.AradonServer;
import net.ion.radon.core.config.Configuration;

import org.junit.Test;

public class TestPlugInLoad {

	
	@Test
	public void loadHelloPlugin() throws Exception {
		Options options = new Options(new String[] { "-config:./resource/config/aradon-config.xml", "-port:9040" });
		AradonServer as = new AradonServer(options);

		Aradon aradon = as.getAradon() ;
		Configuration aconfig = aradon.getGlobalConfig();
		assertEquals("mercury", aconfig.server().id()) ;
		assertEquals(new File(".").getCanonicalPath(), System.getProperty("aradon.mercury.home.dir")) ;
		
		assertEquals(new File("./plugin/hello").getCanonicalPath(), System.getProperty("aradon.mercury[net.bleujin.sample.hello].home.dir")) ;
		
		
		File libDirFile = aradon.getGlobalConfig().plugin().findPlugInFile("net.bleujin.sample.hello", "lib") ;
		assertEquals(new File("./plugin/hello/lib").getCanonicalPath(), libDirFile.getCanonicalPath()) ;
	}
}

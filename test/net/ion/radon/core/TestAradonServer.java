package net.ion.radon.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.radon.Options;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.AradonConfig;
import net.ion.radon.core.AradonServer;
import net.ion.radon.core.config.AradonConstant;
import net.ion.radon.core.config.ConnectorConfig;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.config.ConnectorConfig.Engine;

import org.junit.Test;
import org.restlet.data.Protocol;

public class TestAradonServer extends TestBaseAradon {

	@Test
	public void testParseConfig() throws Exception {
		final File file = new File("./resource/config/readonly-config.xml");
		XMLConfig config = XMLConfig.create(file);
		List<XMLConfig> sections = config.children("section");

		assertEquals(2, sections.size());
		assertTrue(Arrays.equals(new String[] { "", "another" }, new String[] { sections.get(0).getAttributeValue("name"), sections.get(1).getAttributeValue("name") }));
	}

	@Test
	public void testRun() throws Exception {
		Options options = new Options(new String[] { "-config:./resource/config/readonly-config.xml", "-port:9040" });
		AradonServer as = new AradonServer(options);
		Aradon aradon = as.start();

		AradonConfig aconfig =  aradon.getConfig() ;
		assertEquals("jupiter", aconfig.getId()) ;
		
		as.stop();
	}

	@Test
	public void create() throws Exception {
		String configStr = "<connector-config engine='netty' port='8183' protocol='https'>\n" + "<parameter name='keystorePath' description=''>./resource/keystore/.keystore</parameter>\n" + "<parameter name='keystorePassword' description=''>nodara</parameter>\n"
				+ "<parameter name='keyPassword' description=''>kkk</parameter>\n" + "</connector-config>";

		XMLConfig config = XMLConfig.load(configStr);
		ConnectorConfig cc = ConnectorConfig.create(config, 456);
		assertEquals(Protocol.HTTPS, cc.getProtocol());
		assertEquals(Engine.Netty, cc.getEngine());
		assertEquals(8183, cc.getPort());
		assertEquals("kkk", cc.getKeyPassword());
		assertEquals("nodara", cc.getKeyStorePassword());
		assertEquals("./resource/keystore/.keystore", cc.getKeyStorePath());
	}

	public void xtestReStart() throws Exception {
		AradonServer as = new AradonServer(new Options(new String[] { "-port:9002", "-config:resource/config/readonly-config.xml" }));
		Aradon aradon = as.start();

		XMLConfig config = aradon.getConfig().getXMLConfig();

		Debug.debug(config.getString("server-config.log-config-file"));

		XMLConfig cc = config.firstChild("server-config.connector-config");
		Map<String, String> attr = cc.childMap("parameter", "name");
		Debug.debug(attr);
	}

}

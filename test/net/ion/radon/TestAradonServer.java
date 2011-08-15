package net.ion.radon;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.XMLConfig;

public class TestAradonServer extends TestAradon {

	public void testParseConfig() throws Exception {
		final File file = new File("./resource/config/readonly-config.xml");
		XMLConfig config = XMLConfig.create(file);
		List<XMLConfig> sections = config.children("section");

		assertEquals(2, sections.size());
		assertTrue(Arrays.equals(new String[] { "", "another" }, new String[] { sections.get(0).getAttributeValue("name"), sections.get(1).getAttributeValue("name") }));
	}


	public void xtestReStart() throws Exception {
		ARadonServer as = new ARadonServer(new Options(new String[] { "-port:9002", "-config:resource/config/readonly-config.xml" }));
		Aradon aradon = as.start();

		XMLConfig config = aradon.getRootConfig();

		Debug.debug(config.getString("server-config.log-config-file"));

		XMLConfig cc = config.firstChild("server-config.connector-config");
		Map<String, String> attr = cc.childMap("parameter", "name");
		Debug.debug(attr);

	}

}

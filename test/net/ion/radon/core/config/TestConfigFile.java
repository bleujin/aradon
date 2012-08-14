package net.ion.radon.core.config;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TestConfigFile {

	private static String configPath = "./resource/config/readonly-config.xml";

	@Test
	public void testFindAttribute() throws Exception {
		XMLConfig config = XMLConfig.create(configPath);
		XMLConfig fconfig = config.findChild("context.attribute", "id", "let.contact.email");

		assertEquals("bleujin@i-on.net", fconfig.getElementValue());
	}

	@Test
	public void testGetChild() throws Exception {
		XMLConfig config = XMLConfig.create(configPath);
		List<XMLConfig> children = config.firstChild("section").children("path");
		assertEquals("default", children.get(0).getAttributeValue("name"));
	}
}

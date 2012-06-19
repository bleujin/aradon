package net.ion.radon.core.config;

import static org.junit.Assert.assertEquals;

import java.util.List;

import net.ion.radon.core.IService;
import net.ion.radon.core.PathService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.let.FilterUtil;

import org.junit.Test;
import org.restlet.routing.VirtualHost;

public class TestConfigFile {

	private static String configPath = "./resource/config/readonly-config.xml";

	@Test
	public void testCreateFilter() throws Exception {
		XMLConfig config = XMLConfig.create(configPath);
		XMLConfig c = config.findChild("section", "name", "").findChild("path", "name", "hello");

		TreeContext context = TreeContext.createRootContext(new VirtualHost(new org.restlet.Context()));
		final IService service = PathService.ROOT;
		FilterUtil.setFilter(service, c);

		IRadonFilter irf = (IRadonFilter) service.getAfterFilters().get(0);
		assertEquals("net.ion.radon.impl.filter.SayHello", irf.toString());
	}

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

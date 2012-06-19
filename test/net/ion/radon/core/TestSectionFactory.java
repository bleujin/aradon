package net.ion.radon.core;

import java.util.List;

import net.ion.framework.util.Debug;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.section.BasePathInfo;
import net.ion.radon.impl.section.PluginConfig;

public class TestSectionFactory extends TestBaseAradon {

	public void testParser() throws Exception {
		Aradon aradon = testAradon() ;

		String filePath = "resource/config/section-config.xml";
		XMLConfig xconfig = XMLConfig.create(filePath);
		List<XMLConfig> children = xconfig.children("section");
		for (XMLConfig sconfig : children) {
			SectionFactory.parse(aradon, sconfig.getAttributeValue("name"), sconfig);
		}
	}

	public void testOtherSection() throws Exception {
		Aradon aradon = testAradon("resource/config/section-config.xml") ;

		for (SectionService ss : aradon.getChildren()) {
			Debug.debug(ss.getName(), ss);
		}
	}

	public static SectionService parse(Aradon aradon, String sectionName, XMLConfig sconfig) {
		return new MySection(aradon, sectionName, aradon.getServiceContext().createChildContext());
	}
}

class MySection extends SectionService {

	private Aradon aradon;

	MySection(Aradon aradon, String sectionName, TreeContext scontext) {
		super(sectionName, scontext);
		this.aradon = aradon;
	}

	@Override
	public void attach(BasePathInfo pathInfo) {
	}

	@Override
	public void attach(PathService pservice) {
	}

	public Aradon getAradon() {
		return null;
	}

	public void reload() throws Exception {

	}

}

package net.ion.radon.core.config;

import java.util.List;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;

public class SectionsConfigurationBuilder extends AbstractConfigurationBuilder<SectionsConfiguration> implements IConfigurationChildBuilder{

	private List<SectionConfigurationBuilder> sectionConfigBuilders ;
	SectionsConfigurationBuilder(AradonConfigurationBuilder cbuilder) {
		super(cbuilder) ;
		this.sectionConfigBuilders = ListUtil.newList() ;
	}

	@Override
	public SectionsConfiguration create() {
		List<SectionConfiguration> result = ListUtil.newList() ;
		for (SectionConfigurationBuilder builder : sectionConfigBuilders) {
			result.add(builder.create()) ;
		}
		return new SectionsConfiguration(result);
	}

	public SectionConfigurationBuilder restSection(String name) {
		for (SectionConfigurationBuilder scbuilder : sectionConfigBuilders) {
			if (name.equals(scbuilder.name())){
				return scbuilder ;
			}
		}
		
		SectionConfigurationBuilder result = new SectionConfigurationBuilder(this, name);
		sectionConfigBuilders.add(result) ;
		return result;
	}

	public int size() {
		return sectionConfigBuilders.size() ;
	}

	public SectionsConfigurationBuilder fromLoad(XMLConfig config) throws InstanceCreationException {
		List<XMLConfig> sconfigs = config.children("section") ;
		for (XMLConfig sconfig : sconfigs) {
			restSection(sconfig.getAttributeValue("name")).fromLoad(sconfig) ;
		}
		
		return this ;
	}


}

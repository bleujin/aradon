package net.ion.radon.core.config;

import java.util.List;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;


public class SectionConfigurationBuilder extends AbstractLetConfigurationBuilder<SectionConfigurationBuilder, SectionConfiguration> implements IConfigurationChildBuilder{

	private SectionsConfigurationBuilder parent ;
	private String name ;
	private String description ;
	private List<PathConfigurationBuilder> pathConfigBuilders ;
	
	
	SectionConfigurationBuilder(SectionsConfigurationBuilder parent, String name){
		super(parent) ;
		this.parent = parent ;
		this.name = name ;
		this.pathConfigBuilders = ListUtil.newList() ;
	}
	
	public SectionConfigurationBuilder description(String description){
		this.description = description ;
		return this ;
	}
	
	public SectionConfiguration create() {
		List<PathConfiguration> pconfigs = ListUtil.newList() ;
		for (PathConfigurationBuilder pbuilder : pathConfigBuilders) {
			pconfigs.add(pbuilder.create()) ;
		}
		
		return new SectionConfiguration(name, pconfigs, getAttributes(), getPreFilters(), getAfterFilters());
	}

	public PathConfigurationBuilder path(String name) {
		
		for (PathConfigurationBuilder pbuilder : pathConfigBuilders) {
			if (name.equalsIgnoreCase(pbuilder.name())){
				return pbuilder ;
			}
		}
		
		PathConfigurationBuilder result = new PathConfigurationBuilder(this, name);
		pathConfigBuilders.add(result) ;
		return result;
	}
	
	public SectionConfigurationBuilder restSection(String name){
		return parent.restSection(name) ;
	}
	
	public String name(){
		return name ;
	}

	public SectionConfigurationBuilder fromLoad(XMLConfig sconfig) throws InstanceCreationException {
		super.initContextFilter(sconfig) ;
		for (XMLConfig pconfig : sconfig.children("path")) {
			path(pconfig.getAttributeValue("name")).fromLoad(pconfig) ;
		}
		
		return this ;
	}
}

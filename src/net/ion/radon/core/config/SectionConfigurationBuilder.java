package net.ion.radon.core.config;

import java.util.List;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;


public class SectionConfigurationBuilder extends AbstractLetConfigurationBuilder<SectionConfigurationBuilder, SectionConfiguration> implements IConfigurationChildBuilder{

	private SectionsConfigurationBuilder parent ;
	private String name ;
	private String description ;
	private List<PathConfigurationBuilder> pathConfigBuilders ;
	private List<WSPathConfigurationBuilder> wspathConfigBuilders ;
	private List<EPathConfigurationBuilder> epathConfigBuilders ;
	
	
	SectionConfigurationBuilder(SectionsConfigurationBuilder parent, String name){
		super(parent) ;
		this.parent = parent ;
		this.name = name ;
		this.pathConfigBuilders = ListUtil.newList() ;
		this.wspathConfigBuilders = ListUtil.newList() ;
		this.epathConfigBuilders = ListUtil.newList() ;
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

		List<WSPathConfiguration> wsconfigs = ListUtil.newList() ;
		for (WSPathConfigurationBuilder pbuilder : wspathConfigBuilders) {
			wsconfigs.add(pbuilder.create()) ;
		}

		List<EPathConfiguration> econfigs = ListUtil.newList() ;
		for (EPathConfigurationBuilder ebuilder : epathConfigBuilders) {
			econfigs.add(ebuilder.create()) ;
		}
		

		return new SectionConfiguration(name, pconfigs, wsconfigs, econfigs, getAttributes(), getPreFilters(), getAfterFilters());
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

	public WSPathConfigurationBuilder wspath(String name) {
		
		for (WSPathConfigurationBuilder pbuilder : wspathConfigBuilders) {
			if (name.equalsIgnoreCase(pbuilder.name())){
				return pbuilder ;
			}
		}
		
		WSPathConfigurationBuilder result = new WSPathConfigurationBuilder(this, name);
		wspathConfigBuilders.add(result) ;
		return result;
	}
	
	public EPathConfigurationBuilder epath(String name) {
		
		for (EPathConfigurationBuilder ebuilder : epathConfigBuilders) {
			if (name.equalsIgnoreCase(ebuilder.name())){
				return ebuilder ;
			}
		}
		
		EPathConfigurationBuilder result = new EPathConfigurationBuilder(this, name);
		epathConfigBuilders.add(result) ;
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
		for (XMLConfig pconfig : sconfig.children("wspath")) {
			wspath(pconfig.getAttributeValue("name")).fromLoad(pconfig) ;
		}
		for (XMLConfig pconfig : sconfig.children("epath")) {
			epath(pconfig.getAttributeValue("name")).fromLoad(pconfig) ;
		}
		
		
		
		
		return this ;
	}
}

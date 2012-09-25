package net.ion.radon.core.config;

import java.util.List;

public class SectionsConfiguration {

	private List<SectionConfiguration> sconfigs ;
	SectionsConfiguration(List<SectionConfiguration> sectionConfig) {
		this.sconfigs = sectionConfig ;
	}
	
	public List<SectionConfiguration> restSections(){
		return sconfigs ;
	}

	public SectionConfiguration restSection(String name){
		for (SectionConfiguration sconfig : sconfigs) {
			if (sconfig.name().equalsIgnoreCase(name)) return sconfig ;
		}
		throw new IllegalArgumentException("not found section :" + name) ;
	}
	
	public SectionsConfiguration removeSection(String sname) {
		
		SectionConfiguration[] scs = sconfigs.toArray(new SectionConfiguration[0]) ;
		for (SectionConfiguration sc : scs) {
			if (sname.equals(sc.name())) sconfigs.remove(sc) ;
		}
		return this ;
	}

}

package net.ion.radon.core.config;

import java.util.List;
import java.util.Map;

import net.ion.radon.core.filter.IRadonFilter;

import org.restlet.data.Reference;

public class AradonConfiguration extends LetConfiguration<AradonConfiguration> {

	private final SectionsConfiguration sectionConfig ;
	private final String contactEmail ;
	private final Reference homeRef ;
	AradonConfiguration(SectionsConfiguration sectionsConfig, String contactEmail, Reference homeRef, List<IRadonFilter> prefilters, List<IRadonFilter> afterfilters, Map<String, AttributeValue> attributes) {
		super(attributes, prefilters, afterfilters) ;
		this.sectionConfig = sectionsConfig ;
		this.contactEmail = contactEmail ;
		this.homeRef = homeRef ;
	}
	
	public SectionsConfiguration sections() {
		return sectionConfig ;
	}
	
	public String contactEmail(){
		return contactEmail ;
	}
	
	public Reference homeRef(){
		return homeRef ;
	}

}

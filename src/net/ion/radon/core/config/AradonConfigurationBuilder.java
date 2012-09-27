package net.ion.radon.core.config;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.StringUtil;

import org.restlet.data.Reference;



public class AradonConfigurationBuilder extends AbstractLetConfigurationBuilder<AradonConfigurationBuilder, AradonConfiguration> implements IConfigurationChildBuilder{

	private final SectionsConfigurationBuilder sectionsBuilder ;
	private String contactEmail = "bleujin@i-on.net" ;
	private Reference homeRef = new Reference("/help/doc");
	
	AradonConfigurationBuilder(ConfigurationBuilder builder) {
		super(builder) ;
		this.sectionsBuilder = new SectionsConfigurationBuilder(this);
	}

	public AradonConfiguration create() {
		return new AradonConfiguration(sectionsBuilder.create(), contactEmail, homeRef, getAttributes(), getPreFilters(), getAfterFilters(), getFilters()) ;
	}

	public SectionsConfigurationBuilder sections() {
		return sectionsBuilder ;
	}

	
	public AradonConfigurationBuilder contactEmail(String email) {
		this.contactEmail = email ;
		return this ;
	}
	
	public AradonConfigurationBuilder homeRef(String home) {
		if (StringUtil.isBlank(home)) return this;
		
		this.homeRef = new Reference(homeRef) ;
		return this ;
	}
		
	public AradonConfigurationBuilder fromLoad(XMLConfig config) throws InstanceCreationException {
		
		super.initContextFilter(config) ;
		contactEmail(config.findChild("context.attribute", "id", "let.contact.email").getElementValue())
			.homeRef(config.findChild("context.attribute", "id", "let.contact.help.doc").getElementValue()) ;
		
		sections().fromLoad(config) ;
		
		
		return this ;
	}

}

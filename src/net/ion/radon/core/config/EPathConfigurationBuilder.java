package net.ion.radon.core.config;

import java.util.List;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.AbstractEventSourceResource;
import net.ion.radon.core.EnumClass.IMatchMode;

public class EPathConfigurationBuilder extends AbstractLetConfigurationBuilder<EPathConfigurationBuilder, EPathConfiguration>{

	private SectionConfigurationBuilder sectionBuilder ;
	private String name ;
	private String description;
	private final List<String> urlPatterns;
	private Class<? extends AbstractEventSourceResource> handlerClz;
	private IMatchMode matchMode = IMatchMode.EQUALS ;
	
	EPathConfigurationBuilder(SectionConfigurationBuilder sectionBuilder, String name) {
		super(sectionBuilder) ;
		this.sectionBuilder = sectionBuilder ;
		this.name = name ;
		this.urlPatterns = ListUtil.newList() ;
	}

	public String name() {
		return name;
	}
	
	public EPathConfigurationBuilder description(String description) {
		this.description = description;
		return this;
	}
	
	public String description(){
		return description ;
	}

	public EPathConfiguration create() {
		return new EPathConfiguration(sectionBuilder.name(), name, handlerClz, urlPatterns, description, matchMode, getAttributes());
	}
	
	public EPathConfigurationBuilder fromLoad(XMLConfig pconfig) throws InstanceCreationException {
		super.initContextFilter(pconfig);

		try {
			for (Object url : pconfig.getList("urls")) {
				addUrlPattern(ObjectUtil.toString(url));
			}

			Class<? extends AbstractEventSourceResource> clz = (Class<? extends AbstractEventSourceResource>) Class.forName(pconfig.getString("handler[@class]"));
			description(pconfig.getString("description"))
				.handler(clz)
				.matchMode(IMatchMode.fromString( StringUtil.upperCase(pconfig.getString("urls[@matchMode]", "equals")) ));

			return this;
		} catch (ClassNotFoundException ex) {
			throw net.ion.radon.core.except.ConfigurationException.throwIt(ex);
		}
	}

	
	private EPathConfigurationBuilder matchMode(IMatchMode matchMode) {
		this.matchMode = matchMode ;
		return this ;
	}

	public EPathConfigurationBuilder addUrlPattern(String urlPattern) {
		urlPatterns.add(0, urlPattern);
		return this;
	}
	
	private EPathConfigurationBuilder handler(Class<? extends AbstractEventSourceResource> handlerClz) {
		this.handlerClz = handlerClz ;
		return this;
	}

	public AradonConfigurationBuilder aradon() {
		// TODO Auto-generated method stub
		return null;
	}

	public Configuration build() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServerConfigurationBuilder server() {
		// TODO Auto-generated method stub
		return null;
	}

	public ConfigurationBuilder toBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

}

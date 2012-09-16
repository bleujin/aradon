package net.ion.radon.core.config;

import java.util.List;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.AbstractWebSocketResource;
import net.ion.radon.core.EnumClass.IMatchMode;

public class WSPathConfigurationBuilder extends AsyncConfigruationBuilder<WSPathConfigurationBuilder>{

	private SectionConfigurationBuilder sectionBuilder ;
	private String name ;
	private String description;
	private final List<String> urlPatterns;
	private Class<? extends AbstractWebSocketResource> handlerClz;
	private IMatchMode matchMode = IMatchMode.EQUALS ;
	
	WSPathConfigurationBuilder(SectionConfigurationBuilder sectionBuilder, String name) {
		this.sectionBuilder = sectionBuilder ;
		this.name = name ;
		this.urlPatterns = ListUtil.newList() ;
	}

	public String name() {
		return name;
	}
	
	public WSPathConfigurationBuilder description(String description) {
		this.description = description;
		return this;
	}
	
	public String description(){
		return description ;
	}

	public WSPathConfiguration create() {
		return new WSPathConfiguration(sectionBuilder.name(), name, handlerClz, urlPatterns, description, matchMode, getAttributes());
	}
	
	public WSPathConfigurationBuilder fromLoad(XMLConfig pconfig) throws InstanceCreationException {
		parseContextAttribute(pconfig.firstChild("context"));

		try {
			for (Object url : pconfig.getList("urls")) {
				addUrlPattern(ObjectUtil.toString(url));
			}

			Class<? extends AbstractWebSocketResource> clz = (Class<? extends AbstractWebSocketResource>) Class.forName(pconfig.getString("handler[@class]"));
			description(pconfig.getString("description"))
				.handler(clz)
				.matchMode(IMatchMode.fromString( StringUtil.upperCase(pconfig.getString("urls[@matchMode]", "equals")) ));

			return this;
		} catch (ClassNotFoundException ex) {
			throw net.ion.radon.core.except.ConfigurationException.throwIt(ex);
		}
	}

	
	private WSPathConfigurationBuilder matchMode(IMatchMode matchMode) {
		this.matchMode = matchMode ;
		return this ;
	}

	public WSPathConfigurationBuilder addUrlPattern(String urlPattern) {
		urlPatterns.add(0, urlPattern);
		return this;
	}
	
	private WSPathConfigurationBuilder handler(Class<? extends AbstractWebSocketResource> handlerClz) {
		this.handlerClz = handlerClz ;
		return this;
	}

}

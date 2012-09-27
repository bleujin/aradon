package net.ion.radon.core.config;

import java.util.List;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.nradon.AbstractEventSourceResource;
import net.ion.nradon.AbstractWebSocketResource;
import net.ion.nradon.handler.AbstractHttpHandler;

public class SectionConfigurationBuilder extends AbstractLetConfigurationBuilder<SectionConfigurationBuilder, SectionConfiguration> implements IConfigurationChildBuilder {

	private SectionsConfigurationBuilder parent;
	private String name;
	private String description;
	private List<PathConfigurationBuilder> pathConfigBuilders;
	private List<WSPathConfigurationBuilder> wspathConfigBuilders;
	private List<EPathConfigurationBuilder> epathConfigBuilders;
	private List<SPathConfigurationBuilder> spathConfigBuilders;

	SectionConfigurationBuilder(SectionsConfigurationBuilder parent, String name) {
		super(parent);
		this.parent = parent;
		this.name = name;
		this.pathConfigBuilders = ListUtil.newList();
		this.wspathConfigBuilders = ListUtil.newList();
		this.epathConfigBuilders = ListUtil.newList();
		this.spathConfigBuilders = ListUtil.newList();
	}

	public SectionConfigurationBuilder description(String description) {
		this.description = description;
		return this;
	}

	public SectionConfiguration create() {
		List<PathConfiguration> pconfigs = ListUtil.newList();
		for (PathConfigurationBuilder pbuilder : pathConfigBuilders) {
			pconfigs.add(pbuilder.create());
		}

		List<WSPathConfiguration> wsconfigs = ListUtil.newList();
		for (WSPathConfigurationBuilder pbuilder : wspathConfigBuilders) {
			wsconfigs.add(pbuilder.create());
		}

		List<EPathConfiguration> econfigs = ListUtil.newList();
		for (EPathConfigurationBuilder ebuilder : epathConfigBuilders) {
			econfigs.add(ebuilder.create());
		}


		List<SPathConfiguration> sconfigs = ListUtil.newList();
		for (SPathConfigurationBuilder ebuilder : spathConfigBuilders) {
			sconfigs.add(ebuilder.create());
		}

		
		return new SectionConfiguration(name, pconfigs, sconfigs, wsconfigs, econfigs, getAttributes(), getPreFilters(), getAfterFilters(), getFilters());
	}

	public PathConfigurationBuilder path(String pname) {

		for (PathConfigurationBuilder pbuilder : pathConfigBuilders) {
			if (pname.equalsIgnoreCase(pbuilder.name())) {
				return pbuilder;
			}
		}

		PathConfigurationBuilder result = new PathConfigurationBuilder(this, pname);
		pathConfigBuilders.add(result);
		return result;
	}
	

	public SPathConfigurationBuilder spath(String pname) {
		for (SPathConfigurationBuilder pbuilder : spathConfigBuilders) {
			if (pname.equalsIgnoreCase(pbuilder.name())) {
				return pbuilder;
			}
		}

		SPathConfigurationBuilder result = new SPathConfigurationBuilder(this, pname);
		spathConfigBuilders.add(result);
		return result;
	}

	public WSPathConfigurationBuilder wspath(String pname) {

		for (WSPathConfigurationBuilder pbuilder : wspathConfigBuilders) {
			if (pname.equalsIgnoreCase(pbuilder.name())) {
				return pbuilder;
			}
		}

		WSPathConfigurationBuilder result = new WSPathConfigurationBuilder(this, pname);
		wspathConfigBuilders.add(result);
		return result;
	}

	public EPathConfigurationBuilder epath(String pname) {

		for (EPathConfigurationBuilder ebuilder : epathConfigBuilders) {
			if (pname.equalsIgnoreCase(ebuilder.name())) {
				return ebuilder;
			}
		}

		EPathConfigurationBuilder result = new EPathConfigurationBuilder(this, pname);
		epathConfigBuilders.add(result);
		return result;
	}

	public SectionConfigurationBuilder restSection(String name) {
		return parent.restSection(name);
	}

	public String name() {
		return name;
	}

	public SectionConfigurationBuilder fromLoad(XMLConfig sconfig) throws InstanceCreationException {
		super.initContextFilter(sconfig);
		try {
			for (XMLConfig pconfig : sconfig.children("path")) {
				Class<?> clz = Class.forName(pconfig.getString("handler[@class]"));
				if (AbstractWebSocketResource.class.isAssignableFrom(clz)){
					wspath(pconfig.getAttributeValue("name")).fromLoad(pconfig);
				} else if (AbstractEventSourceResource.class.isAssignableFrom(clz)){
					epath(pconfig.getAttributeValue("name")).fromLoad(pconfig);
				} else if (AbstractHttpHandler.class.isAssignableFrom(clz)){
					spath(pconfig.getAttributeValue("name")).fromLoad(pconfig);
				} else {
					path(pconfig.getAttributeValue("name")).fromLoad(pconfig);	
				} 
			}
			for (XMLConfig pconfig : sconfig.children("wspath")) {
				wspath(pconfig.getAttributeValue("name")).fromLoad(pconfig);
			}
			for (XMLConfig pconfig : sconfig.children("epath")) {
				epath(pconfig.getAttributeValue("name")).fromLoad(pconfig);
			}

		} catch (ClassNotFoundException ex) {
			throw new InstanceCreationException(ex);
		}

		return this;
	}

}

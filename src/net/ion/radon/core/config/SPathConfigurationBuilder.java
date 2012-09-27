package net.ion.radon.core.config;

import java.util.List;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.AbstractSingleHttpResource;
import net.ion.radon.core.EnumClass;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.EnumClass.Scope;

public class SPathConfigurationBuilder extends AbstractLetConfigurationBuilder<SPathConfigurationBuilder, SPathConfiguration> implements IConfigurationChildBuilder {

	private final String name;
	private final List<String> urlPatterns;

	private Class<? extends AbstractSingleHttpResource> handlerClz;
	private EnumClass.Scope scope = Scope.Request;
	private String description;
	private IMatchMode matchMode = IMatchMode.EQUALS;

	private SectionConfigurationBuilder sectionBuilder;

	protected SPathConfigurationBuilder(SectionConfigurationBuilder sectionBuilder, String name) {
		super(sectionBuilder);
		this.sectionBuilder = sectionBuilder;
		this.name = name;
		this.urlPatterns = ListUtil.newList();
	}

	@Override
	public SPathConfiguration create() {
		return new SPathConfiguration(name, handlerClz, scope, urlPatterns, description, matchMode, getAttributes(), getFilters());
	}

	public SPathConfigurationBuilder handler(Class<? extends AbstractSingleHttpResource> handlerClz) {
		this.handlerClz = handlerClz;

		return this;
	}

	public SPathConfigurationBuilder scope(Scope scope) {
		this.scope = scope;
		return this;
	}

	public SPathConfigurationBuilder addUrlPattern(String urlPattern) {
		urlPatterns.add(0, urlPattern);
		return this;
	}

	public SPathConfigurationBuilder description(String description) {
		this.description = description;
		return this;
	}

	public SPathConfigurationBuilder path(String name) {
		return sectionBuilder.spath(name);
	}

	public SPathConfigurationBuilder matchMode(IMatchMode matchMode) {
		this.matchMode = matchMode;
		return this;
	}

	public SectionConfigurationBuilder restSection(String name) {
		return sectionBuilder.restSection(name);
	}

	public String name() {
		return this.name;
	}

	public SPathConfigurationBuilder fromLoad(XMLConfig pconfig) throws InstanceCreationException {
		super.initContextFilter(pconfig);

		try {
			for (Object url : pconfig.getList("urls")) {
				addUrlPattern(ObjectUtil.toString(url));
			}

			Class<? extends AbstractSingleHttpResource> clz = (Class<? extends AbstractSingleHttpResource>) Class.forName(pconfig.getString("handler[@class]"));
			description(pconfig.getString("description")).scope(Scope.valueOf(StringUtil.capitalize(pconfig.getString("handler[@scope]", "request")))).handler(clz).matchMode(IMatchMode.fromString(StringUtil.upperCase(pconfig.getString("urls[@matchMode]", "equals"))));

			return this;
		} catch (ClassNotFoundException ex) {
			throw net.ion.radon.core.except.ConfigurationException.throwIt(ex);
		}
	}

}

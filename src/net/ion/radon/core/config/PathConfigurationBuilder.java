package net.ion.radon.core.config;

import java.util.List;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.let.IServiceLet;
import net.ion.radon.core.EnumClass;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.EnumClass.Scope;

import org.restlet.resource.ServerResource;

public class PathConfigurationBuilder extends AbstractLetConfigurationBuilder<PathConfigurationBuilder, PathConfiguration> implements
		IConfigurationChildBuilder {

	private final String name;
	private final List<String> urlPatterns;

	private Class<? extends IServiceLet> handlerClz;
	private EnumClass.Scope scope = Scope.Request;
	private String description;
	private IMatchMode matchMode = IMatchMode.EQUALS ;

	private SectionConfigurationBuilder sectionBuilder;

	protected PathConfigurationBuilder(SectionConfigurationBuilder sectionBuilder, String name) {
		super(sectionBuilder);
		this.sectionBuilder = sectionBuilder;
		this.name = name;
		this.urlPatterns = ListUtil.newList();
	}

	@Override
	public PathConfiguration create() {
		if (ServerResource.class.isAssignableFrom(handlerClz)){
			return new PathConfiguration(name, (Class<? extends ServerResource>)handlerClz, scope, urlPatterns, description, matchMode, getAttributes(), getPreFilters(), getAfterFilters(), getFilters());
		} else if (IServiceLet.class.isAssignableFrom(handlerClz)) {
			return new LetPathConfiguration(name, handlerClz, scope, urlPatterns, description, matchMode, getAttributes(), getPreFilters(), getAfterFilters(), getFilters()).toPathConfig() ;
		}
		throw new IllegalArgumentException("not supported service type ") ;
	}

	public PathConfigurationBuilder handler(Class<? extends IServiceLet> handlerClz) {
		this.handlerClz = handlerClz;

		return this;
	}

	public PathConfigurationBuilder scope(Scope scope) {
		this.scope = scope;
		return this;
	}

	public PathConfigurationBuilder addUrlPattern(String urlPattern) {
		urlPatterns.add(0, urlPattern);
		return this;
	}

	public PathConfigurationBuilder description(String description) {
		this.description = description;
		return this;
	}

	public PathConfigurationBuilder path(String name) {
		return sectionBuilder.path(name);
	}

	public PathConfigurationBuilder matchMode(IMatchMode matchMode){
		this.matchMode = matchMode ;
		return this ;
	}
	
	public SectionConfigurationBuilder parentSection(){
		return sectionBuilder ;
	}
	
	public SectionConfigurationBuilder restSection(String name) {
		return sectionBuilder.restSection(name);
	}

	public String name() {
		return this.name;
	}

	public PathConfigurationBuilder fromLoad(XMLConfig pconfig) throws InstanceCreationException {
		super.initContextFilter(pconfig);

		try {
			for (Object url : pconfig.getList("urls")) {
				addUrlPattern(ObjectUtil.toString(url));
			}

			Class<? extends IServiceLet> clz = (Class<? extends IServiceLet>) Class.forName(pconfig.getString("handler[@class]"));
			description(pconfig.getString("description"))
				.scope(Scope.valueOf(StringUtil.capitalize(pconfig.getString("handler[@scope]", "request"))))
				.handler(clz)
				.matchMode(IMatchMode.fromString( StringUtil.upperCase(pconfig.getString("urls[@matchMode]", "equals")) ));

			return this;
		} catch (ClassNotFoundException ex) {
			throw net.ion.radon.core.except.ConfigurationException.throwIt(ex);
		}
	}

}

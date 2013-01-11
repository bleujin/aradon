package net.ion.radon.core.config;

import java.util.List;
import java.util.Map;

import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.filter.XRadonFilter;
import net.ion.nradon.let.IServiceLet;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.EnumClass.Scope;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.let.OuterServerResource;

public class LetPathConfiguration extends LetConfiguration<PathConfiguration> implements IPathConfiguration {

	private final String name;
	private final Class<? extends IServiceLet> handlerClz;
	private final Scope scope;
	private final List<String> urlPatterns;
	private final String description;
	private final IMatchMode imatchMode;

	LetPathConfiguration(String name, Class<? extends IServiceLet> handlerClz, Scope scope, List<String> urlPatterns, String description, IMatchMode imatchMode, Map<String, AttributeValue> attributes, List<IRadonFilter> prefilters, List<IRadonFilter> afterfilter, List<XRadonFilter> filters) {
		super(attributes, prefilters, afterfilter, filters);
		this.name = name;
		this.handlerClz = handlerClz;
		this.scope = scope;
		this.urlPatterns = urlPatterns;
		this.description = description;
		this.imatchMode = imatchMode;
	}

	public static LetPathConfiguration create(String name, String urls, Class<? extends IServiceLet> handlerClz) {
		return new LetPathConfiguration(name, handlerClz, Scope.Request, ListUtil.toList(StringUtil.split(urls, ", ")), "", IMatchMode.EQUALS, MapUtil.<String, AttributeValue> newMap(), ListUtil.<IRadonFilter> newList(), ListUtil.<IRadonFilter> newList(), ListUtil.<XRadonFilter> newList());
	}

	public static LetPathConfiguration create(String name, String urls, String description, IMatchMode matchMode, Class<? extends IServiceLet> handlerClz) {
		return new LetPathConfiguration(name, handlerClz, Scope.Request, ListUtil.toList(StringUtil.split(urls, ", ")), description, matchMode, MapUtil.<String, AttributeValue> newMap(), ListUtil.<IRadonFilter> newList(), ListUtil.<IRadonFilter> newList(), ListUtil.<XRadonFilter> newList());
	}

	public PathConfiguration toPathConfig() {
		attributes().put(IServiceLet.class.getCanonicalName(), new ApplicationAttributeValue(XMLConfig.BLANK, handlerClz)) ;
		return new PathConfiguration(this.name, OuterServerResource.class, scope, urlPatterns, description, imatchMode, attributes(), prefilters(), afterfilters(), filters()) ;
	}

//	public SPathConfiguration toSinglePathConfig() {
//		return new SPathConfiguration(this.name, OuterHttpResource.class, Scope.Application, urlPatterns, description, imatchMode, attributes(), filters()) ;
//	}

	
	public String name() {
		return name;
	}

	public Class<? extends IServiceLet> handlerClz() {
		return handlerClz;
	}

	public Scope scope() {
		return scope;
	}

	public List<String> urlPatterns() {
		return urlPatterns;
	}

	public String description() {
		return description;
	}

	public int matchMode() {
		return imatchMode.toRouterMode();
	}

	public IMatchMode imatchMode() {
		return imatchMode;
	}

	public String toString() {
		return this.getClass().getSimpleName() + "[" + name() + "]";
	}



}

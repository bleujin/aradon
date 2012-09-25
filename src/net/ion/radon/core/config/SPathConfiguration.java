package net.ion.radon.core.config;

import java.util.List;
import java.util.Map;

import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.AbstractHttpResource;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.EnumClass.Scope;
import net.ion.radon.core.filter.IRadonFilter;

public class SPathConfiguration extends LetConfiguration<SPathConfiguration>  implements IPathConfiguration{

	private final String name;
	private final Class<? extends AbstractHttpResource> handlerClz;
	private final Scope scope;
	private final List<String> urlPatterns;
	private final String description;
	private final IMatchMode imatchMode;

	SPathConfiguration(String name, Class<? extends AbstractHttpResource> handlerClz, Scope scope, List<String> urlPatterns, String description, IMatchMode imatchMode, Map<String, AttributeValue> attributes, List<IRadonFilter> prefilters, List<IRadonFilter> afterfilter) {
		super(attributes, prefilters, afterfilter);
		this.name = name;
		this.handlerClz = handlerClz;
		this.scope = scope;
		this.urlPatterns = urlPatterns;
		this.description = description;
		this.imatchMode = imatchMode;
	}

	public static SPathConfiguration create(String name, String urls, Class<? extends AbstractHttpResource> handlerClz) {
		return new SPathConfiguration(name, handlerClz, Scope.Request, ListUtil.toList(StringUtil.split(urls, ", ")), "", IMatchMode.EQUALS, MapUtil.<String, AttributeValue> newMap(), ListUtil.<IRadonFilter> newList(), ListUtil.<IRadonFilter> newList());
	}

	public static SPathConfiguration create(String name, String urls, String description, IMatchMode matchMode, Class<? extends AbstractHttpResource> handlerClz) {
		return new SPathConfiguration(name, handlerClz, Scope.Request, ListUtil.toList(StringUtil.split(urls, ", ")), description, matchMode, MapUtil.<String, AttributeValue> newMap(), ListUtil.<IRadonFilter> newList(), ListUtil.<IRadonFilter> newList());
	}

	public String name() {
		return name;
	}

	public Class<? extends AbstractHttpResource> handlerClz() {
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

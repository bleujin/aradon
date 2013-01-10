package net.ion.radon.core.config;

import java.util.List;
import java.util.Map;

import net.ion.nradon.AbstractSingleHttpResource;
import net.ion.nradon.filter.XRadonFilter;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.EnumClass.Scope;
import net.ion.radon.core.let.SingleLetPathService;

public class SPathConfiguration extends LetConfiguration<SPathConfiguration>  implements IPathConfiguration{

	private final String name;
	private final Class<? extends AbstractSingleHttpResource> handlerClz;
	private final Scope scope;
	private final List<String> urlPatterns;
	private final String description;
	private final IMatchMode imatchMode;

	
	SPathConfiguration(String name, Class<? extends AbstractSingleHttpResource> handlerClz, Scope scope, List<String> urlPatterns, String description, IMatchMode imatchMode, Map<String, AttributeValue> attributes, List<XRadonFilter> filters) {
		super(attributes, filters);
		this.name = name;
		this.handlerClz = handlerClz;
		this.scope = scope;
		this.urlPatterns = urlPatterns;
		this.description = description;
		this.imatchMode = imatchMode;
	}

	public SingleLetPathService createOuterLet(Aradon aradon, SectionService section, TreeContext sectionContext){
		return SingleLetPathService.create(aradon, section, sectionContext.createChildContext(), this);
	}
	
	
	public String name() {
		return name;
	}

	public Class<? extends AbstractSingleHttpResource> handlerClz() {
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

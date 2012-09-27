package net.ion.radon.core.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.ion.framework.util.ListUtil;
import net.ion.nradon.filter.XRadonFilter;
import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IRadonFilter;

public abstract class LetConfiguration<T> {

	private final Map<String, AttributeValue> attributes;
	private final List<IRadonFilter> prefilters;
	private final List<IRadonFilter> afterfilters;
	
	private final List<XRadonFilter> filters ;

	private IService attachedService ;
	protected LetConfiguration(Map<String, AttributeValue> attributes, List<IRadonFilter> prefilters, List<IRadonFilter> afterfilters, List<XRadonFilter> filters){
		this.attributes = attributes ;
		this.prefilters = prefilters ;
		this.afterfilters = afterfilters ;
		this.filters = filters ;
	}
	
	protected LetConfiguration(Map<String, AttributeValue> attributes, List<XRadonFilter> filters){
		this.attributes = attributes ;
		this.prefilters = ListUtil.EMPTY ;
		this.afterfilters = ListUtil.EMPTY ; 
		this.filters = filters ;
	}

	public void initFilter(IService service) {
		this.attachedService = service ;
		for (IRadonFilter filter : prefilters) {
			filter.init(service) ;
		}
		for (IRadonFilter filter : afterfilters) {
			filter.init(service) ;
		}
		for(XRadonFilter filter : filters){
			filter.init(service) ;
		}
	}
	
	public Map<String, AttributeValue> attributes(){
		return attributes ;
	}

	public List<IRadonFilter> prefilters() {
		return Collections.unmodifiableList(prefilters);
	}

	public List<IRadonFilter> afterfilters() {
		return Collections.unmodifiableList(afterfilters);
	}

	
	public T addPreFilter(IRadonFilter filter) {
		if (!prefilters.contains(filter)) {
			filter.init(attachedService) ;
			prefilters.add(filter) ;
		}
		return (T) this ;
	}
	
	public T addAfterFilter(IRadonFilter filter) {
		if (!afterfilters.contains(filter)) {
			filter.init(attachedService) ;
			afterfilters.add(filter) ;
		}
		return (T) this ;
	}
	
	public T addPreFilter(int index, IRadonFilter filter) {
		if (!prefilters.contains(filter)) {
			filter.init(attachedService) ;
			prefilters.add(index, filter) ;
		}
		return (T) this ;
	}
	
	public T addAfterFilter(int index, IRadonFilter filter) {
		if (!afterfilters.contains(filter)) {
			filter.init(attachedService) ;
			afterfilters.add(index, filter) ;
		}
		return (T) this ;
	}
	
	public T removePreFilter(IRadonFilter filter){
		prefilters.remove(filter) ;
		return (T) this ;
	}


	public T removeAfterFilter(IRadonFilter filter) {
		afterfilters.remove(filter) ;
		return (T) this ;
	}

	public T addFilter(XRadonFilter filter) {
		filter.init(attachedService) ;
		filters.add(filter) ;
		return (T) this ;
	}

	
	public List<XRadonFilter> filters(){
		return filters ;
	}
	

}

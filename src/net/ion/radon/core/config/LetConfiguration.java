package net.ion.radon.core.config;

import java.util.List;
import java.util.Map;

import net.ion.radon.core.filter.IRadonFilter;

public abstract class LetConfiguration<T> {

	private final Map<String, AttributeValue> attributes;
	private final List<IRadonFilter> prefilters;
	private final List<IRadonFilter> afterfilters;

	protected LetConfiguration(Map<String, AttributeValue> attributes, List<IRadonFilter> prefilters, List<IRadonFilter> afterfilters){
		this.attributes = attributes ;
		this.prefilters = prefilters ;
		this.afterfilters = afterfilters ;
	}
	
	
	public Map<String, AttributeValue> attributes(){
		return attributes ;
	}

	public List<IRadonFilter> prefilters() {
		return prefilters;
	}

	public List<IRadonFilter> afterfilters() {
		return afterfilters;
	}

	
	public T addPreFilter(IRadonFilter filter) {
		prefilters.add(filter) ;
		return (T) this ;
	}
	
	public T addAfterFilter(IRadonFilter filter) {
		afterfilters.add(filter) ;
		return (T) this ;
	}
	
	public T addPreFilter(int index, IRadonFilter filter) {
		prefilters.add(index, filter) ;
		return (T) this ;
	}
	
	public T addAfterFilter(int index, IRadonFilter filter) {
		afterfilters.add(index, filter) ;
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


}

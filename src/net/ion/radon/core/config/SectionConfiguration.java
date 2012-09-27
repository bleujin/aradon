package net.ion.radon.core.config;

import java.util.List;
import java.util.Map;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.nradon.filter.XRadonFilter;
import net.ion.radon.core.filter.IRadonFilter;

import org.apache.commons.configuration.ConfigurationException;


public class SectionConfiguration extends LetConfiguration<SectionConfiguration>{

	private final String name ;
	private final List<PathConfiguration> pconfigs ;
	private final List<SPathConfiguration> sconfigs ;
	private final List<WSPathConfiguration> wsconfigs ;
	private final List<EPathConfiguration> econfigs ;
	
	SectionConfiguration(String name, List<PathConfiguration> pconfigs, List<SPathConfiguration> sconfigs, List<WSPathConfiguration> wsconfigs, List<EPathConfiguration> econfigs, Map<String, AttributeValue> attributes, List<IRadonFilter> prefilters, List<IRadonFilter> afterfilters, List<XRadonFilter> filters) {
		super(attributes, prefilters, afterfilters, filters);
		this.name = name;
		this.pconfigs = pconfigs;
		this.sconfigs = sconfigs ;
		this.wsconfigs = wsconfigs ;
		this.econfigs = econfigs ;
	}

	public final static SectionConfiguration createBlank(String name){
		return new SectionConfiguration(name, ListUtil.<PathConfiguration>newList(), ListUtil.<SPathConfiguration>newList(), ListUtil.<WSPathConfiguration>newList(), ListUtil.<EPathConfiguration>newList(), MapUtil.<String, AttributeValue>newMap(), ListUtil.<IRadonFilter>newList(), ListUtil.<IRadonFilter>newList(), ListUtil.<XRadonFilter>newList()) ;
	}
	
	public final static SectionConfiguration create(String name, List<PathConfiguration> paths){
		return new SectionConfiguration(name, paths, ListUtil.<SPathConfiguration>newList(), ListUtil.<WSPathConfiguration>newList(), ListUtil.<EPathConfiguration>newList(), MapUtil.<String, AttributeValue>newMap(), ListUtil.<IRadonFilter>newList(), ListUtil.<IRadonFilter>newList(), ListUtil.<XRadonFilter>newList()) ;
	}
	
	public List<PathConfiguration> pathConfiguration() {
		return pconfigs;
	}
	public List<SPathConfiguration> spathConfiguration() {
		return sconfigs;
	}
	public List<WSPathConfiguration> wspathConfiguration() {
		return wsconfigs;
	}
	public List<EPathConfiguration> epathConfiguration() {
		return econfigs ;
	}
	
	
	public String name() {
		return name;
	}


	public WSPathConfiguration wspath(String name) {
		for (WSPathConfiguration wsconfig : wsconfigs) {
			if (wsconfig.name().equalsIgnoreCase(name)) return wsconfig ;
		}
		throw new IllegalArgumentException("not found path :" + name) ;
	}

	public PathConfiguration path(String name) {
		for (PathConfiguration pconfig : pconfigs) {
			if (pconfig.name().equalsIgnoreCase(name)) return pconfig ;
		}
		throw new IllegalArgumentException("not found path :" + name) ;
	}

	SectionConfiguration addPathConfiguration(PathConfiguration pconfig){
		pconfigs.add(pconfig) ;
		
		return this ;
	}

	public void removePath(String name) {
		PathConfiguration[] pcs = pconfigs.toArray(new PathConfiguration[0]) ;
		for (PathConfiguration pc : pcs) {
			if (name.equals(pc.name())) pconfigs.remove(pc) ;
		}
	}
	
	public static SectionsConfiguration fromLoad(XMLConfig sconfig) throws ConfigurationException, InstanceCreationException {
		return ConfigurationBuilder.load(sconfig).build().aradon().sections() ;
	}
	
	public String toString(){
		return this.getClass().getName() + "[" + name() + "]" ;
	}

}

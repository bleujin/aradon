package net.ion.radon.core;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.ion.radon.core.EnumClass.FilterLocation;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.impl.section.BasePathInfo;
import net.ion.radon.impl.section.PathInfo;

public class PathService implements IService {

	private SectionService section ;
	private TreeContext pathContext ;
	private BasePathInfo pinfo ;
	
	private PathService(SectionService section, TreeContext pathContext, BasePathInfo pinfo) {
		this.section = section ;
		this.pathContext = pathContext ;
		this.pinfo = pinfo ;
	}

	public static PathService create(SectionService section, TreeContext pathContext, BasePathInfo pinfo){
		return new PathService(section, pathContext, pinfo) ;
	}

	public BasePathInfo getPathInfo() {
		return pinfo;
	}
	
	public void restart() {
		pathContext.restart();
	}

	public void suspend() {
		pathContext.suspend();
	}

	public List<IRadonFilter> getPreFilters() {
		return pathContext.getPreFilters();
	}

	public List<IRadonFilter> getAfterFilters() {
		return pathContext.getAfterFilters();
	}

	public TreeContext createChildContext() {
		return pathContext.createChildContext() ;
	}

	public void addPreFilter(IRadonFilter filter) {
		pathContext.addPreFilter(this, filter);
		
	}

	public void addAfterFilter(IRadonFilter filter) {
		pathContext.addAfterFilter(this, filter);
	}

	public void removeAfterFilter(IRadonFilter filter) {
		pathContext.removeFilter(FilterLocation.AFTER, filter);
	}

	public void removePreFilter(IRadonFilter filter) {
		pathContext.removeFilter(FilterLocation.PRE, filter);
	}
	
	public TreeContext getServiceContext() {
		return pathContext;
	}

	public Map getAttributes() {
		return pathContext.getAttributes();
	}

	public void putAttribute(String key, Object value) {
		pathContext.putAttribute(key, value);
	}
	
	public String toString(){
		return getClass().getName() + "[" + pinfo.toString()  + "]";
	}

	public SectionService getParent() {
		return section;
	}

	public void reload() throws Exception {
		// @TODO not impl 
		 getAradon().reload() ;
	}

	public PathService getChildService(String pathName){
		throw new IllegalArgumentException("this is pathservice") ;
	}

	public Collection getChildren() {
		return Collections.EMPTY_LIST ;
	}

	public String getName() {
		return pinfo.getName();
	}
	
	public String getNamePath(){
		return getParent().getNamePath() + getName() + "/" ;
	}

	public Aradon getAradon(){
		return section.getAradon() ;
	}

	public List<String> getRecentLog(int count){
		return getAradon().getRadonLogService().recentLog(this, count) ;
	}

}

package net.ion.radon.core;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.ion.framework.util.MapUtil;
import net.ion.radon.core.EnumClass.FilterLocation;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.impl.section.BasePathInfo;
import net.ion.radon.impl.section.PluginConfig;

import org.restlet.Application;
import org.restlet.data.Reference;

public abstract class SectionService extends Application  implements IService {

	private final String sectionName ;
	private TreeContext context;
	protected Map<String, PathService> pathServices = MapUtil.newMap();

	protected SectionService(String sectionName, TreeContext scontext) {
		super(scontext);
		this.sectionName = sectionName ;
		this.context =  scontext;
	}

	public abstract void attach(BasePathInfo pathInfo)  ;

	
	public String getName() {
		return this.sectionName;
	}

	public String getNamePath(){
		return getParent().getNamePath() + getName() + "/" ;
	}

	public SectionService getOtherSection(String sectionName) {
		return getAradon().getChildService(sectionName);
	}

	public void detach(String pathName){
		PathService pservice = getChildService(pathName) ;
		pathServices.remove(pathName) ;
		// router.detach(pservice.getPathInfo().getHandlerClass()) ;
		
		getLogger().warning(pathName + " path detattached..") ;
	}

	public TreeContext getServiceContext() {
		return this.context;
	}
	
	
	public void addPreFilter(IRadonFilter filter){
		getServiceContext().addPreFilter(this, filter) ;
	}
	public void addAfterFilter(IRadonFilter filter){
		getServiceContext().addAfterFilter(this, filter) ;
	}

	public void restart() {
		context.restart();
	}

	public void suspend() {
		context.suspend();
	}

	public String getPathName(Reference reference) {
		for (PathService pservice : pathServices.values()) {
			if (pservice.getPathInfo().isMatchURL(reference))
				return pservice.getPathInfo().getName();
		}
		throw new IllegalArgumentException(this.sectionName + "{" + pathServices.values() + "} " + reference.toString());
	}

	
	public List<IRadonFilter> getAfterFilters() {
		return context.getAfterFilters();
	}

	public List<IRadonFilter> getPreFilters() {
		return context.getPreFilters();
	}

	public void removeAfterFilter(IRadonFilter filter) {
		context.removeFilter(FilterLocation.AFTER, filter) ;
	}

	public void removePreFilter(IRadonFilter filter) {
		context.removeFilter(FilterLocation.PRE, filter) ;
	}

	public Aradon getParent() {
		return getAradon();
	}
	
	public PathService getChildService(String pathName){
		return pathServices.get(pathName) ;
	}

	public Collection<PathService> getChildren() {
		return Collections.unmodifiableCollection(pathServices.values());
	}


	public String toString(){
		return getClass().getSimpleName() + "[" +  this.sectionName + "]";
	}

	public PathService getPathService(Reference pathReference) {
		return getChildService(getPathName(pathReference)) ;
	}

	public List<String> getRecentLog(int count){
		return getAradon().getRadonLogService().recentLog(this, count) ;
	}

	
}


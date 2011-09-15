package net.ion.radon.core;

import java.util.Collection;
import java.util.List;

import net.ion.framework.util.ListUtil;
import net.ion.radon.core.filter.IRadonFilter;


public interface IService {

	
	public final static IService ROOT = new IService() {
		
		
		private List<IRadonFilter> pres = ListUtil.newList() ;
		private List<IRadonFilter> afters = ListUtil.newList() ;
		public void suspend() {
			throw new IllegalStateException("i am root service") ;
		}
		
		public void restart() {
			throw new IllegalStateException("i am root service") ;
		}
		
		public void removePreFilter(IRadonFilter filter) {
			throw new IllegalStateException("i am root service") ;
		}
		
		public void removeAfterFilter(IRadonFilter filter) {
			throw new IllegalStateException("i am root service") ;
		}
		
		public void reload() throws Exception {
			throw new IllegalStateException("i am root service") ;
		}
		
		public TreeContext getServiceContext() {
			throw new IllegalStateException("i am root service") ;
		}
		
		public List<IRadonFilter> getPreFilters() {
			return pres ;
		}
		
		public IService getParent() {
			throw new IllegalStateException("i am root service") ;
		}
		
		public List<IRadonFilter> getAfterFilters() {
			return afters ;
		}
		
		public void addPreFilter(IRadonFilter filter) {
			pres.add(filter) ;
		}
		
		public void addAfterFilter(IRadonFilter filter) {
			afters.add(filter) ;
		}

		public IService getChildService(String childName) {
			throw new IllegalStateException("i am root service") ;
		}

		public String getName() {
			return "";
		}
		
		public String getNamePath(){
			return "" ;
		}
		
		public Aradon getAradon(){
			throw new IllegalStateException("i am root service") ;
		}
		
		public List<IService> getChildren() {
			throw new IllegalStateException("i am root service") ;
		}
	};

	public TreeContext getServiceContext() ;
	public IService getParent() ;
	
	public void restart() ;
	public void suspend() ;
	// public void handle(Request request, Response response) ;
	
	public void addPreFilter(IRadonFilter filter);
	public void addAfterFilter(IRadonFilter filter);
	
	public void removePreFilter(IRadonFilter filter);
	public void removeAfterFilter(IRadonFilter filter);
	
	public List<IRadonFilter> getPreFilters() ;
	public List<IRadonFilter> getAfterFilters();

	public void reload() throws Exception ;
	public Collection<? extends IService> getChildren() ;
	public IService getChildService(String childName);
	public String getName(); 
	public Aradon getAradon() ;
	public String getNamePath() ;
}

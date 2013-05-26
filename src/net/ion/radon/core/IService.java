package net.ion.radon.core;

import java.util.Collection;
import java.util.List;

import net.ion.radon.core.config.LetConfiguration;


public interface IService<CT> {

	
	public final static IService ROOT = new IService() {

		public void suspend() {
			throw new IllegalStateException("i am root service") ;
		}
		
		public void restart() {
			throw new IllegalStateException("i am root service") ;
		}
		
		public void reload() {
			throw new IllegalStateException("i am root service") ;
		}
		
		public TreeContext getServiceContext() {
			throw new IllegalStateException("i am root service") ;
		}
		
		public IService getParent() {
			throw new IllegalStateException("i am root service") ;
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

		public List<? extends IService> getChildren() {
			throw new IllegalStateException("i am root service") ;
		}

		public void stop()  {
			
		}
		public LetConfiguration getConfig() {
			throw new IllegalStateException("i am root service") ;
		}
	};

	public TreeContext getServiceContext() ;
	public IService getParent() ;
	public LetConfiguration getConfig() ;
	
	public void restart() ;
	public void suspend() ;
	public void stop()  ;
	// public void handle(Request request, Response response) ;
	
	public void reload() ;
	public CT getChildService(String childName);
	public Collection<CT> getChildren() ;
	public String getName(); 
	public Aradon getAradon() ;
	public String getNamePath() ;
	
	
}

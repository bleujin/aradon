package net.ion.nradon;

import net.ion.radon.core.HttpRestSection;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.EPathConfiguration;



public abstract class AbstractEventSourceResource implements EventSourceHandler{

	private HttpRestSection parent;
	private TreeContext context;
	private EPathConfiguration econfig;

	public void init(HttpRestSection parent, TreeContext context, EPathConfiguration econfig){
		this.parent = parent ;
		this.context = context ;
		this.econfig = econfig ;
	}
	
	public TreeContext getServiceContext(){
		return context ;
	}
	
	public EPathConfiguration getConfig(){
		return econfig ;
	} 
	
	public HttpRestSection getParent(){
		return parent ;
	}

}

package net.ion.nradon;

import java.util.Map.Entry;

import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.AttributeValue;
import net.ion.radon.core.config.EPathConfiguration;

public abstract class AbstractEventSourceResource implements EventSourceHandler{

	private SectionService parent;
	private TreeContext context;
	private EPathConfiguration econfig;

	public void init(SectionService parent, TreeContext context, EPathConfiguration econfig){
		this.parent = parent ;
		this.context = context ;
		this.econfig = econfig ;
		
		for (Entry<String, AttributeValue>  entry : econfig.attributes().entrySet()) {
			context.putAttribute(entry.getKey(), entry.getValue()) ;
		}
	}
	
	public TreeContext getServiceContext(){
		return context ;
	}
	
	public EPathConfiguration getConfig(){
		return econfig ;
	} 
	
	public SectionService getParent(){
		return parent ;
	}

}

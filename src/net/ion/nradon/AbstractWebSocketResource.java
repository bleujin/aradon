package net.ion.nradon;

import java.util.Map.Entry;

import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.AttributeValue;
import net.ion.radon.core.config.WSPathConfiguration;

public abstract class AbstractWebSocketResource implements WebSocketHandler{

	private SectionService parent ;
	private TreeContext context ;
	private WSPathConfiguration wsconfig ;
	
	
	public void init(SectionService parent, TreeContext context, WSPathConfiguration wsconfig){
		this.parent = parent ;
		this.context = context ;
		this.wsconfig = wsconfig ;
		
		for (Entry<String, AttributeValue>  entry : wsconfig.attributes().entrySet()) {
			context.putAttribute(entry.getKey(), entry.getValue()) ;
		}
	}

	public SectionService getParent(){
		return parent ;
	}
	
	public TreeContext getServiceContext(){
		return context ;
	}

	public WSPathConfiguration getConfig() {
		return wsconfig;
	}

	
}

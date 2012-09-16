package net.ion.nradon;

import net.ion.radon.core.HttpRestSection;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.WSPathConfiguration;

public abstract class AbstractWebSocketResource implements WebSocketHandler{

	private HttpRestSection parent ;
	private TreeContext context ;
	private WSPathConfiguration wsconfig ;
	public void init(HttpRestSection parent, TreeContext context, WSPathConfiguration wsconfig){
		this.parent = parent ;
		this.context = context ;
		this.wsconfig = wsconfig ;
	}

	public HttpRestSection getParent(){
		return parent ;
	}
	
	public TreeContext getServiceContext(){
		return context ;
	}

	public WSPathConfiguration getConfig() {
		return wsconfig;
	}

	
}

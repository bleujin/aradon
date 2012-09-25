package net.ion.nradon;

import java.util.Map.Entry;

import net.ion.framework.util.StringUtil;
import net.ion.nradon.handler.AbstractHttpHandler;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.AttributeValue;
import net.ion.radon.core.config.SPathConfiguration;

public abstract class AbstractHttpResource extends AbstractHttpHandler{

	private SectionService parent;
	private TreeContext context;
	private SPathConfiguration sconfig;

	public void init(SectionService parent, TreeContext context, SPathConfiguration sconfig){
		this.parent = parent ;
		this.context = context ;
		this.sconfig = sconfig ;
		
		for (Entry<String, AttributeValue>  entry : sconfig.attributes().entrySet()) {
			context.putAttribute(entry.getKey(), entry.getValue()) ;
		}
	}
	
	public void release(){} 
	
	public TreeContext getServiceContext(){
		return context ;
	}
	
	public SPathConfiguration getConfig(){
		return sconfig ;
	} 
	
	public SectionService getParent(){
		return parent ;
	}
	
	protected String withoutTrailingSlashOrQuery(HttpRequest request) {
		String remainPath = StringUtil.substring(request.uri(), parent.getName().length() + 1) ;
		return withoutTrailingSlashOrQuery(remainPath) ;
	}

	protected String withoutTrailingSlashOrQuery(String path) {
		int queryStart = path.indexOf('?');
		if (queryStart > -1) {
			path = path.substring(0, queryStart);
		}
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}
	
	public abstract void handleHttpRequest(final HttpRequest request, final HttpResponse response, HttpControl control) throws Exception ; 
}

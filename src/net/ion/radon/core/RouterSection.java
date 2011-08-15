package net.ion.radon.core;

import static net.ion.radon.core.RadonAttributeKey.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.config.AttributeUtil;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.let.FilterUtil;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.impl.section.BasePathInfo;
import net.ion.radon.impl.section.PluginConfig;

import org.apache.commons.configuration.ConfigurationException;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.routing.Router;

public class RouterSection extends SectionService{

	private final Aradon aradon;
	private final String sectionName ;
	private final PluginConfig pconfig;
	private TreeContext context;
	private Router router  ;
	//private Map<String, PathService> pathServices = Collections.EMPTY_MAP;

	RouterSection(Aradon aradon, String sectionName, TreeContext scontext, PluginConfig pconfig) {
		super(sectionName, scontext);
		this.aradon = aradon ;
		this.sectionName = sectionName ;
		
		this.context =  scontext;
		this.router =  new Router(scontext) ;
		this.pconfig = pconfig ;
	}


	
	public String getName() {
		return this.sectionName;
	}

	public String getNamePath(){
		return getParent().getNamePath() + getName() + "/" ;
	}

	public Aradon getAradon() {
		return aradon;
	}

	@Override
	public Restlet createInboundRoot() {
		return router ;
	}

	public void handle(Request request, Response response) {
		try {
			
			final Reference pathReference = ((InnerRequest)request).getPathReference();
			for (PathService pservice : pathServices.values()) {
				// pservice.getPathInfo()
				if (pservice.getPathInfo().isMatchURL( pathReference)) {
					TreeContext requestContext =  pservice.createChildContext() ;
					request.getAttributes().put(REQUEST_CONTEXT, requestContext);
					
					super.handle(request, response) ;
					return;
				}
			}
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,  "section:" + getName() + ", " + "path:" + pathReference);
		} catch (ResourceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, ex.getMessage());
		}
	}


	public void attach(BasePathInfo pathInfo) {
		attach(pathInfo, PathService.create(this, context.createChildContext(), pathInfo));
	}
	
	void attach(BasePathInfo pathInfo, PathService pservice) {
		if (pathServices.containsKey(pathInfo.getName())) throw new IllegalArgumentException("already exist path name :" + pathInfo.toString()) ;
		pathServices.put(pathInfo.getName(), pservice);
		
		for (String urlPattern : pathInfo.getUrls()) {
			final String path = StringUtil.isBlank(sectionName) ? urlPattern.substring(1) : urlPattern;
			router.attach(path, pathInfo.getHandlerClass(), pathInfo.getMatchMode().toRouterMode()) ;
		}
	}
	
	public void detach(String pathName){
		PathService pservice = getChildService(pathName) ;
		pathServices.remove(pathName) ;
		// router.detach(pservice.getPathInfo().getHandlerClass()) ;
		
		getLogger().warning(pathName + " path detattached..") ;
	}
	



	public String getPathName(Reference reference) {
		for (PathService pservice : pathServices.values()) {
			if (pservice.getPathInfo().isMatchURL(reference))
				return pservice.getPathInfo().getName();
		}
		throw new IllegalArgumentException(this.sectionName + "{" + pathServices.values() + "} " + reference.toString());
	}

	public void reload() throws Exception {
		 // @TODO not impl 
		 getAradon().reload() ;
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

	public PluginConfig getPluginConfig() {
		return pconfig ;
	}

}

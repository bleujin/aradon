package net.ion.radon.core;

import static net.ion.radon.core.RadonAttributeKey.*;

import java.util.Collection;
import java.util.Collections;

import net.ion.framework.util.StringUtil;
import net.ion.radon.core.classloading.PathFinder;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.impl.section.BasePathInfo;

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
	private TreeContext context;
	private Router router  ;
	//private Map<String, PathService> pathServices = Collections.EMPTY_MAP;

	private final PathFinder finder ;
	RouterSection(Aradon aradon, String sectionName, TreeContext scontext) {
		super(sectionName, scontext);
		this.aradon = aradon ;
		this.sectionName = sectionName ;
		
		this.context =  scontext;
		this.router =  new Router(scontext) ;
		this.finder = new PathFinder(scontext) ;
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
					request.getAttributes().put(PATH_SERVICE_KEY, pservice);
					
					super.handle(request, response) ;
					return;
				}
			}
			if (pathReference != null && "/favicon.ico".equals(pathReference.toString())) {
				response.setStatus(Status.SUCCESS_NO_CONTENT) ;
				return ;
			}
			response.setStatus(Status.CLIENT_ERROR_NOT_FOUND) ;
			
			// throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND,  "section:" + getName() + ", " + "path:" + pathReference);
		} catch (ResourceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, ex.getMessage());
		}
	}


	public void attach(BasePathInfo pathInfo) {
		attach(PathService.create(this, context.createChildContext(), pathInfo));
	}
	
	public void attach(PathService pservice) {
		BasePathInfo pathInfo = pservice.getPathInfo() ;
		if (pathServices.containsKey(pathInfo.getName())) throw new IllegalArgumentException("already exist path name :" + pathInfo.toString()) ;
		pathServices.put(pathInfo.getName(), pservice);
		
		for (String urlPattern : pathInfo.getUrls()) {
			final String path = StringUtil.isBlank(sectionName) ? urlPattern.substring(1) : urlPattern;
			// router.attach(path, pathInfo.getHandlerClass(), pathInfo.getMatchMode().toRouterMode()) ;
			router.attach(path, finder, pathInfo.getMatchMode().toRouterMode()) ;
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

}

package net.ion.radon.core.let;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;

import net.ion.nradon.AbstractHttpResource;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.handler.aradon.RadonUtil;
import net.ion.nradon.handler.event.ServerEvent.EventType;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.IService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.SPathConfiguration;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.impl.filter.RevokeServiceFilter;

import org.restlet.Request;
import org.restlet.Response;

public class SPathService implements IService<SPathService>, HttpHandler, IRadonPathService {

	private final Aradon aradon;
	private AbstractHttpResource resource;

	SPathService(Aradon aradon, AbstractHttpResource resource) {
		this.aradon = aradon;
		this.resource = resource ;
		// this.sconfig.attachService(this);
	}

	public static SPathService create(Aradon aradon, SectionService section, TreeContext context, SPathConfiguration sconfig) {
		try {
			Constructor<? extends AbstractHttpResource> cons = sconfig.handlerClz().getDeclaredConstructor();
			cons.setAccessible(true);
			AbstractHttpResource handler = cons.newInstance();

			SPathService result = new SPathService(aradon, handler);
			result.initService(section, context, sconfig);

			return result;
		} catch (Throwable th) {
			throw new IllegalStateException(th);
		}
	}

	private void initService(SectionService parent, TreeContext context, SPathConfiguration econfig) {
		this.resource.init(parent, context, econfig);
	}

	public void handleHttpRequest(HttpRequest hrequest, HttpResponse hresponse, HttpControl control) throws Exception {
		
		
		Request request = RadonUtil.toRequest(hrequest) ;
		Response response = new Response(request) ;
		for (IRadonFilter filter : getConfig().prefilters()) {
			filter.preHandle(this, request, response) ;
		}
		
		this.resource.handleHttpRequest(hrequest, hresponse, control);
		
		
//		request.data(RadonAttributeKey.PATH_SERVICE_KEY, this);
//		request.data(RadonAttributeKey.PATH_CONFIGURATION, getConfig());
//
//		IFilterResult preResult = FilterUtil.preHandle(this, getConfig().prefilters(), request, response);
//		if (preResult.getResult() == Filter.STOP) {
//			response.setStatus(preResult.getCause().getStatus());
//			response.setEntity(preResult.getReplaceRepresentation());
//			return;
//		}
//		try {
//			if (preResult.getResult() != Filter.SKIP) {
//				this.resource.handleHttpRequest(request, response, control);
//			}
//		} catch (Exception e) {
//			response.status(Status.SERVER_ERROR_INTERNAL.getCode());
//			throw new ResourceException(e);
//		} finally {
//			FilterUtil.afterHandle(this, getConfig().afterfilters(), request, response);
//		}
		
		
		
		
	}

	public HttpHandler toHttpHandler(){
		return this ;
	}

	public SPathConfiguration getConfig() {
		return resource.getConfig();
	}

	public TreeContext getContext() {
		return resource.getServiceContext();
	}

	public void restart() {
		resource.getConfig().removePreFilter(RevokeServiceFilter.SELF);
	}

	public void suspend() {
		resource.getConfig().addPreFilter(0, RevokeServiceFilter.SELF);
	}

	public Aradon getAradon() {
		return aradon;
	}

	public SPathService getChildService(String childName) {
		throw new IllegalArgumentException("this is pathservice");
	}

	public Collection<SPathService> getChildren() {
		return Collections.EMPTY_LIST;
	}

	public String getNamePath() {
		return "/" + getParent().getName() + "/" + getName();
	}

	public TreeContext getServiceContext() {
		return resource.getServiceContext();
	}

	public void reload() throws Exception {

	}

	public String toString() {
		return "SPathService[" + resource.getConfig() + "]";
	}

	public void stop() {
		getServiceContext().closeAttribute();
	}

	public String getName() {
		return resource.getConfig().name();
	}

	public IService getParent() {
		return resource.getParent();
	}

	public void onEvent(EventType event, Radon radon) {
		resource.onEvent(event, radon) ;
	}
}
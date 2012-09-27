package net.ion.radon.core.let;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;

import net.ion.nradon.AbstractSingleHttpResource;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.filter.XFilterUtil;
import net.ion.nradon.handler.event.ServerEvent.EventType;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.IService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.SPathConfiguration;
import net.ion.radon.impl.filter.RevokeServiceFilter;

public class SingleLetPathService implements IService<SingleLetPathService>, HttpHandler, IRadonPathService {

	private final Aradon aradon;
	private AbstractSingleHttpResource resource;

	SingleLetPathService(Aradon aradon, AbstractSingleHttpResource resource) {
		this.aradon = aradon;
		this.resource = resource ;
		// this.sconfig.attachService(this);
	}

	public static SingleLetPathService create(Aradon aradon, SectionService section, TreeContext context, SPathConfiguration sconfig) {
		try {
			Constructor<? extends AbstractSingleHttpResource> cons = sconfig.handlerClz().getDeclaredConstructor();
			cons.setAccessible(true);
			AbstractSingleHttpResource handler = cons.newInstance();

			SingleLetPathService result = new SingleLetPathService(aradon, handler);
			result.initService(section, context, sconfig);

			return result;
		} catch (Throwable th) {
			throw new IllegalStateException(th);
		}
	}

	private void initService(SectionService parent, TreeContext context, SPathConfiguration econfig) {
		this.resource.init(parent, context, econfig);
		getConfig().initFilter(this) ;
	}

	public void handleHttpRequest(HttpRequest hrequest, HttpResponse hresponse, HttpControl control) throws Exception {
		
		XFilterUtil.httpStart(this, hrequest, hresponse, control) ;
		this.resource.handleHttpRequest(hrequest, hresponse, control);
		XFilterUtil.httpEnd(this, hrequest, hresponse, control) ;
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

	public SingleLetPathService getChildService(String childName) {
		throw new IllegalArgumentException("this is pathservice");
	}

	public Collection<SingleLetPathService> getChildren() {
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
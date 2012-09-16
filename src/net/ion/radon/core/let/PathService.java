package net.ion.radon.core.let;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map.Entry;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.IService;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.AttributeValue;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.impl.filter.RevokeServiceFilter;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Filter;

public class PathService extends Restlet implements IService {

	private TreeContext context;
	private PathConfiguration pconfig;

	private final Aradon aradon ;
	private final SectionService section ;
	PathService(Aradon aradon, SectionService section, TreeContext context, PathConfiguration config) {
		super(context);
		this.aradon = aradon ;
		this.section = section ;
		this.context = context ;
		this.pconfig = config ;
		for (Entry<String, AttributeValue> entry : config.attributes().entrySet()) {
			context.putAttribute(entry.getKey(), entry.getValue());
		}
		this.pconfig.attachService(this) ;
	}

	public static PathService create(Aradon aradon, SectionService section, TreeContext context, PathConfiguration pconfig) {
		PathService result = new PathService(aradon, section, context, pconfig);
		result.setName(pconfig.name()) ;
		return result;
	}

	public void handle(Request request, Response response) {

		request.getAttributes().put(RadonAttributeKey.PATH_SERVICE_KEY, this);
		request.getAttributes().put(RadonAttributeKey.PATH_CONFIGURATION, pconfig);

		IFilterResult preResult = FilterUtil.preHandle(this, pconfig.prefilters(), request, response);
		if (preResult.getResult() == Filter.STOP) {
			response.setStatus(preResult.getCause().getStatus());
			response.setEntity(preResult.getReplaceRepresentation());
			return;
		}
		try {
			if (preResult.getResult() != Filter.SKIP) {
				handleResource(request, response);
			}
		} catch (Exception e) {
			response.setStatus(Status.SERVER_ERROR_INTERNAL);
			throw new ResourceException(e);
		} finally {
			FilterUtil.afterHandle(this, pconfig.afterfilters(), request, response);
		}
	}

	private void handleResource(Request request, Response response) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Constructor<? extends ServerResource> cons = pconfig.handlerClz().getDeclaredConstructor();
		cons.setAccessible(true);
		ServerResource resource = cons.newInstance();
		resource.init(context, request, response);

		resource.handle();
		
		resource.release() ;
	}

	public PathConfiguration getConfig() {
		return pconfig;
	}

	@Override
	public TreeContext getContext() {
		return context;
	}

	public void restart() {
		pconfig.removePreFilter(RevokeServiceFilter.SELF);
	}

	public void suspend() {
		pconfig.addPreFilter(0, RevokeServiceFilter.SELF);
	}

	public Aradon getAradon() {
		return aradon ;
	}

	public IService getChildService(String childName) {
		throw new IllegalArgumentException("this is pathservice");
	}

	public Collection<? extends IService> getChildren() {
		return Collections.EMPTY_LIST;
	}

	public String getNamePath() {
		return "/" + getParent().getName() + "/" + getName();
	}

	public SectionService getParent() {
		return section;
	}

	public TreeContext getServiceContext() {
		return context;
	}

	public void reload() throws Exception {
		// TODO Auto-generated method stub

	}

	public String toString(){
		return "PathService[" + pconfig + "]" ;
	}
	
	public void stop() {
		try {
			super.stop() ;
		} catch (Exception ignore) {
			ignore.printStackTrace();
		}
		getServiceContext().closeAttribute() ;
	}
}

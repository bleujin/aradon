package net.ion.radon.impl.filter;

import java.util.Map;

import net.ion.framework.rest.CloneableRepresentation;
import net.ion.framework.util.Debug;
import net.ion.radon.core.IService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

import org.apache.commons.collections.map.LRUMap;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.representation.Representation;

public class HttpMethodCacheFilter extends IRadonFilter {

	public final static String CACHE_NAME = HttpMethodCacheFilter.class.getCanonicalName();
	private Map<String, Representation> cacheMap ;

	public HttpMethodCacheFilter() {
		this(100);
	}

	public HttpMethodCacheFilter(int cacheCount) {
		cacheMap = new LRUMap(cacheCount);
	}

	@Override
	public void init(IService service) {
		service.addAfterFilter(this);
	}
	
	@Override
	public IFilterResult preHandle(IService service, Request request,Response response) {
		String url = request.getResourceRef().toString();

		if (request.getMethod() == Method.GET && cacheMap.containsKey(url)) {
			service.getServiceContext().putAttribute(CACHE_NAME, cacheMap.get(url));
			return IFilterResult.SKIP_RESULT;
		}
		return IFilterResult.CONTINUE_RESULT;
	}
	
	@Override
	public IFilterResult afterHandle(IService service, Request request, Response response) {
		String url = request.getResourceRef().toString();
		TreeContext context = service.getServiceContext() ;
		if(request.getMethod() == Method.GET && context.contains(url)){
			Debug.line("AfterHandle get context");
			response.setEntity(context.getSelfAttributeObject(CACHE_NAME,Representation.class));
		}else if((!context.contains(CACHE_NAME))&& (response.getEntity() instanceof CloneableRepresentation)){
			Debug.line("AfterHandle put cacheMap");
			cacheMap.put(url,  ((CloneableRepresentation)response.getEntity()).cloneRepresentation());
		}

		return IFilterResult.CONTINUE_RESULT;
	}
}

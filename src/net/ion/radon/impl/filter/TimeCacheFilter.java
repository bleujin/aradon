package net.ion.radon.impl.filter;

import java.util.Map;

import net.ion.framework.rest.CloneableRepresentation;
import net.ion.radon.core.IService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

import org.apache.commons.collections.map.LRUMap;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.representation.Representation;

public class TimeCacheFilter  extends IRadonFilter{

	private int maxSeconds;
	private final static String CACHE_NAME = TimeCacheFilter.class.getCanonicalName() ;
	
	private Map<String, TimerRepresentation> cacheMap;	
	
	public TimeCacheFilter() {
		this(60);
	}
	
	public TimeCacheFilter(int maxSeconds) {
		this(maxSeconds, 100);
	}
	
	public TimeCacheFilter(int maxSeconds, int limitCount) {
		this.maxSeconds = maxSeconds;
		cacheMap = new LRUMap(limitCount);
	}
	
	@Override
	public void init(IService service) {
		service.addAfterFilter(this) ;
	}
	
	@Override
	public IFilterResult preHandle(IService service, Request request, Response response) {
		String url = request.getResourceRef().toString();
		
		final TimerRepresentation cached = cacheMap.get(url);
		if(cached == null || cached.invalidTime(this.maxSeconds)){
			return IFilterResult.CONTINUE_RESULT;
		}
		
		service.getServiceContext().putAttribute(CACHE_NAME, cached.getClone()) ;
		return IFilterResult.SKIP_RESULT;
	}
	 
	@Override
	public IFilterResult afterHandle(IService service, Request request, Response response) {
		String url = request.getResourceRef().toString();

		TreeContext context = service.getServiceContext() ;
		if (context.contains(CACHE_NAME)){
			response.setEntity(context.getSelfAttributeObject(CACHE_NAME, Representation.class)) ;
		} else if ( (! context.contains(CACHE_NAME)) && response.getEntity() instanceof CloneableRepresentation){
			cacheMap.put(url, TimerRepresentation.create((CloneableRepresentation)response.getEntity()));
		}

		return IFilterResult.CONTINUE_RESULT;
	}
		
}

package net.ion.radon.impl.filter;

import java.util.HashMap;
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

public class CountDownCacheFilter extends IRadonFilter {

	private int countdown;
	public final static String CACHE_NAME = CountDownCacheFilter.class.getCanonicalName();

	private Map<String, CountDownRepresentation> cacheMap;
	private Map<String, Integer> countMap = new HashMap<String, Integer>();

	public CountDownCacheFilter() {
		this(20);
	}

	public CountDownCacheFilter(int countdown) {
		this(countdown, 100);
	}

	public CountDownCacheFilter(int countdown, int cachecount) {
		this.countdown = Math.max(1, countdown);
		cacheMap = new LRUMap(cachecount);
	}

	@Override
	public void init(IService service) {
		service.getConfig().addAfterFilter(this) ;
	}

	@Override
	public IFilterResult preHandle(IService iservice, Request request, Response response) {
		String url = request.getResourceRef().toString();

		final CountDownRepresentation cached = cacheMap.get(url);
		if (cached == null || !cached.isRangeCount(this.countdown)) {
			return IFilterResult.CONTINUE_RESULT;
		}
		iservice.getServiceContext().putAttribute(CACHE_NAME, cached.getClone());
		return IFilterResult.SKIP_RESULT;

	}

	@Override
	public IFilterResult afterHandle(IService iservice, Request request, Response response) {
		String url = request.getResourceRef().toString();
		TreeContext context = iservice.getServiceContext();
		if (context.contains(CACHE_NAME)) {
			response.setEntity(context.getSelfAttributeObject(CACHE_NAME, Representation.class));
		} else if ((!context.contains(CACHE_NAME)) && (response.getEntity() instanceof CloneableRepresentation)) {
			cacheMap.put(url, CountDownRepresentation.create((CloneableRepresentation) response.getEntity()));
		}
		return IFilterResult.CONTINUE_RESULT;
	}

}

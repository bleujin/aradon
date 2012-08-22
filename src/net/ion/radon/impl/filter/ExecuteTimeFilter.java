package net.ion.radon.impl.filter;

import net.ion.framework.util.Debug;
import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

import org.restlet.Request;
import org.restlet.Response;

public class ExecuteTimeFilter extends IRadonFilter{

	public final static String CACHE_NAME = ExecuteTimeFilter.class.getCanonicalName();
	
	@Override
	public IFilterResult afterHandle(IService iservice, Request request, Response response) {
		
		Long startTime = getInnerRequest(request).getAttributes().containsKey(CACHE_NAME) ? 
						getInnerRequest(request).getAttribute(CACHE_NAME, Long.class) : 0 ;
		
		long exectime = System.currentTimeMillis() - startTime;
		
		Debug.debug("Execute time(second) : " +  exectime / 1000.0f);
		return IFilterResult.CONTINUE_RESULT;
	}

	@Override
	public void init(IService service){
		super.init(service) ;
		
		service.getConfig().addPreFilter(this);
		service.getConfig().addAfterFilter(this);
	}
	
	@Override
	public IFilterResult preHandle(IService iservice, Request request, Response response) {
		getInnerRequest(request).putAttribute(CACHE_NAME, System.currentTimeMillis() );

		return IFilterResult.CONTINUE_RESULT;
	}

}

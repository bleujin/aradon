package net.ion.radon.impl.filter;

import java.util.Map;

import net.ion.framework.rest.CloneableRepresentation;
import net.ion.framework.util.ObjectUtil;
import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.let.AbstractLet;

import org.apache.commons.collections.map.LRUMap;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.representation.Representation;

public class HttpResponseFilter extends IRadonFilter {

	private Map<String, Representation> cacheMap = new LRUMap();
	private Status currentStatus = Status.SUCCESS_OK ;
	
	@Override
	public IFilterResult afterHandle(IService service, Request request,Response response) {
		String url = request.getResourceRef().toString();
		
		if(response.getStatus().isSuccess()){
			cacheMap.put(url, ObjectUtil.coalesce(response.getEntity(), AbstractLet.EMPTY_REPRESENTATION));
			return IFilterResult.CONTINUE_RESULT;
			
		} else { // occured exception
			
			if (currentStatus.isSuccess()) { // first..
				service.addPreFilter(this);
			}
			return IFilterResult.CONTINUE_RESULT;
		}
	}

	@Override
	public IFilterResult preHandle(IService service, Request request,Response response) {
		String url = request.getResourceRef().toString();
		
		final Representation cached = cacheMap.get(url);
		if(cached != null && cached instanceof CloneableRepresentation){
			response.setStatus(Status.SUCCESS_OK) ;
			response.setEntity(((CloneableRepresentation)cached).cloneRepresentation()) ;
			return IFilterResult.SKIP_RESULT;
		}
		
		this.currentStatus = Status.REDIRECTION_SEE_OTHER ;
		return IFilterResult.CONTINUE_RESULT;
	}

}

package net.ion.radon.impl.filter;

import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

public class RevokeServiceFilter extends IRadonFilter {

	public final static RevokeServiceFilter SELF = new RevokeServiceFilter() ;
	
	private RevokeServiceFilter(){
		
	}
	
	@Override
	public IFilterResult afterHandle(IService service, Request request, Response response) {
		return IFilterResult.CONTINUE_RESULT;
	}

	@Override
	public IFilterResult preHandle(IService service, Request request, Response response) {
		response.setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
		return IFilterResult.stopResult(new ResourceException(Status.SERVER_ERROR_SERVICE_UNAVAILABLE, " Service does not work."));
	}
}


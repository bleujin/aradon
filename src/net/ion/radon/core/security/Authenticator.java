package net.ion.radon.core.security;

import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.security.Enroler;

public abstract class Authenticator extends IRadonFilter {


	private volatile boolean optional;
	private volatile Enroler enroler;
	
	public Authenticator() {
	}

	@Override
	public void init(IService service){
		this.enroler = service.getServiceContext().getDefaultEnroler() ;
	}
	
	@Override
	public IFilterResult afterHandle(IService iservice, Request request, Response response) {
		return IFilterResult.CONTINUE_RESULT;
	}

	@Override
	public IFilterResult preHandle(IService iservice, Request request, Response response) {
		// TODO Auto-generated method stub

		if (authenticate(request, response))
			return authenticated(request, response);
		if (isOptional()) {
			response.setStatus(Status.SUCCESS_OK);
			return IFilterResult.CONTINUE_RESULT;
		} else {
			return unauthenticated(request, response);
		}
	}

	protected abstract boolean authenticate(Request request, Response response);

	protected IFilterResult authenticated(Request request, Response response) {
		if (request.getClientInfo() != null)
			request.getClientInfo().setAuthenticated(true);
		response.getChallengeRequests().clear();
		if (getEnroler() != null)
			getEnroler().enrole(request.getClientInfo());
		return IFilterResult.CONTINUE_RESULT;
	}

	protected IFilterResult unauthenticated(Request request, Response response) {
		if (isOptional()) {
			response.setStatus(Status.SUCCESS_OK);
			return IFilterResult.CONTINUE_RESULT;
		}
		if (request.getClientInfo() != null)
			request.getClientInfo().setAuthenticated(false);
		return IFilterResult.stopResult(new ResourceException(401));
	}

	public Enroler getEnroler() {
		return enroler;
	}

	public boolean isOptional() {
		return optional;
	}

	public void setEnroler(Enroler enroler) {
		this.enroler = enroler;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

}

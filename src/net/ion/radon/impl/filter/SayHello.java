package net.ion.radon.impl.filter;

import net.ion.framework.util.Debug;
import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

import org.restlet.Request;
import org.restlet.Response;

public class SayHello extends IRadonFilter {

	@Override
	public IFilterResult afterHandle(IService service, Request request, Response response) {
		Debug.debug("Bye");
		
		return IFilterResult.CONTINUE_RESULT;
	}

	@Override
	public IFilterResult preHandle(IService service, Request request, Response response) {
		Debug.debug("Hello", request.getClass());

		return IFilterResult.CONTINUE_RESULT;
	}
	
	public int hashCode(){
		return getClass().getCanonicalName().hashCode() ;
	}
	
	public boolean equals(Object obj){
		if (obj instanceof SayHello) return true ;
		return false ;
	}

}

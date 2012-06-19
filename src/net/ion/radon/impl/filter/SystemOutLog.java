package net.ion.radon.impl.filter;

import java.util.Set;
import java.util.Map.Entry;

import net.ion.framework.util.CollectionUtil;
import net.ion.framework.util.Debug;
import net.ion.radon.core.IService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.let.DefaultLet;
import net.ion.radon.impl.util.DebugPrinter;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ClientInfo;
import org.restlet.data.Form;

public class SystemOutLog extends IRadonFilter{

	public SystemOutLog(){
	}
	
	public IFilterResult preHandle(IService service, Request request, Response response){
		myHandle(service.getServiceContext(), request, response) ;
		return IFilterResult.CONTINUE_RESULT ;
	}
	

	@Override
	public IFilterResult afterHandle(IService service, Request request, Response response) {
		myHandle(service.getServiceContext(), request, response) ;
		return IFilterResult.CONTINUE_RESULT;
	}
	
	private IFilterResult myHandle(TreeContext context, Request request, Response response) {
		ClientInfo ci = request.getClientInfo();
		Debug.line("address:", ci.getAddress(), "agent", ci.getAgentName(), "user", ci.getUser()) ;
		printRequestHeader(request) ;
		
		return IFilterResult.CONTINUE_RESULT;
	}

	private void printRequestHeader(Request request) {
		Form requestHeaderForm = (Form) request.getAttributes().get(DefaultLet.ATTRIBUTE_HEADERS);
		Debug.line(request.getMethod(), request.getResourceRef());
		Set<Entry<String, String>> set = requestHeaderForm.getValuesMap().entrySet();
		CollectionUtil.each(set, new DebugPrinter<Entry<String, String>>());
	}
}

package net.ion.radon.impl.filter;

import net.ion.framework.util.Debug;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

import org.apache.commons.lang.ArrayUtils;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

public class SampleRevokeAddress extends IRadonFilter {

	@Override
	public IFilterResult afterHandle(IService service, Request request, Response response) {
		return IFilterResult.CONTINUE_RESULT;
	}

	@Override
	public IFilterResult preHandle(IService service, Request request, Response response) {
		String[] allows = StringUtil.split(getElementValue("allow.client.address"), ",") ;
		String clientAddress = request.getClientInfo().getAddress() ;
		if (clientAddress.equals("127.0.0.1") || allows == null || allows.length < 1) return IFilterResult.CONTINUE_RESULT ;
		if (ArrayUtils.contains(allows, clientAddress)) return IFilterResult.CONTINUE_RESULT ;

		Debug.line(request.getHostRef(), request.getReferrerRef(), request.getClientInfo().getAddress());
		return IFilterResult.stopResult(new ResourceException(Status.CLIENT_ERROR_NOT_ACCEPTABLE, "not allowed address. contact system admin[" + service.getServiceContext().getAttributeObject("bleujin@i-on.net") + "]"));
	}

}

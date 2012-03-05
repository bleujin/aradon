package net.ion.radon.core.let;

import java.util.List;
import java.util.Map;

import net.ion.framework.rest.IMapListRepresentationHandler;
import net.ion.framework.rest.IRequest;
import net.ion.framework.rest.IResponse;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.PathService;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.EnumClass.IFormat;
import net.ion.radon.core.filter.IFilterResult;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ClientInfo;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Filter;

public class AbstractServerResource extends BaseServerResource {

	public TreeContext getContext() {
		return ((TreeContext) getRequest().getAttributes().get(RadonAttributeKey.REQUEST_CONTEXT));
	}

	protected Aradon getAradon() {
		return getMySectionService().getAradon();
	}

	protected SectionService getMySectionService() {
		return (SectionService) getApplication();
	}

	public InnerRequest getInnerRequest() {
		return (InnerRequest) super.getRequest();
	}

	protected Representation toRepresentation(List<Map<String, ?>> data) throws ResourceException {
		return toRepresentation(IRequest.EMPTY_REQUEST, data, IResponse.EMPTY_RESPONSE);
	}

	protected Representation toRepresentation(IRequest request, List<Map<String, ?>> datas, IResponse response) throws ResourceException {
		return MapListRepresentationHandler.create(newMapListFormatHandler(), request, datas, response, getContext()).toRepresentation();
	}

	protected IMapListRepresentationHandler newMapListFormatHandler() {
		try {
			return newMapListFormatHandler(getInnerRequest().getIFormat());
		} catch (ClassNotFoundException e) {
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e);
		} catch (InstantiationException e) {
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e);
		} catch (IllegalAccessException e) {
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e);
		}
	}

	protected IMapListRepresentationHandler newMapListFormatHandler(IFormat format) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class clz = Class.forName("net.ion.framework.rest." + format.toString() + "Formater");
		return (IMapListRepresentationHandler) clz.newInstance();
	}

	public Representation handle() {
		initRequest();
		// String str = getRequest().getEntityAsText() ;
		// Debug.line(str != null ? str.length() : 0, str) ;

		TreeContext rcontext = getContext();
		Request request = getRequest();
		Response response = getResponse();

		PathService pservice = getPathService();

		IFilterResult preResult = FilterUtil.handlePreFilter(pservice, request, response);
		if (preResult.getResult() == Filter.STOP) {
			response.setStatus(preResult.getCause().getStatus());
			return preResult.getReplaceRepresentation();
			// return EMPTY_REPRESENTATION;
		}

		try {
			if (preResult.getResult() != Filter.SKIP) {
				super.handle();
			}
			return response.getEntity();
		} catch (ResourceException ex) {
			doCatch(ex);
			return AbstractLet.EMPTY_REPRESENTATION;
		} catch (Throwable ex) {
			ex.printStackTrace();
			doCatch(ex);
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, ex.getMessage());
		} finally {
			FilterUtil.handleAfterFilter(pservice, request, response);
		}
	}

	private void initRequest() {

	}

	protected PathService getPathService() {
		return getInnerRequest().getPathService(getMySectionService().getAradon());
	}

	protected boolean isExplorer() {
		String agent = getRequest().getClientInfo().getAgent();
		if (agent == null) return false ;
		return agent.indexOf("MSIE") > -1;
	}

}

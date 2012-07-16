package net.ion.radon.core.let;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ion.framework.rest.IMapListRepresentationHandler;
import net.ion.framework.rest.IRequest;
import net.ion.framework.rest.IResponse;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.PathService;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.EnumClass.IFormat;
import net.ion.radon.core.filter.IFilterResult;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Filter;
import org.restlet.util.Series;

@Deprecated
public abstract class AbstractLet extends ServerResource implements RadonAttributeKey {
	public final static Representation EMPTY_REPRESENTATION = new EmptyRepresentation();
	public final static List<Map<String, ? extends Object>> EMPTY_DATAS = ListUtil.EMPTY_LIST;

	private Set<Method> disAllow_Method;

	public AbstractLet() {
		this(Collections.EMPTY_SET, MediaType.ALL);
	}

	public AbstractLet(MediaType allowMediaType) {
		this(Collections.EMPTY_SET, allowMediaType);
	}

	public AbstractLet(Set<Method> disAllowMethods, MediaType allowMediaType) {
		super();
		this.disAllow_Method = disAllowMethods;
		getVariants().add(new Variant(allowMediaType));
		setConditional(false);
		setNegotiated(false);
	}

	public final Representation doHandle() {
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
				handleLet();
			}
			return response.getEntity();
		} catch (ResourceException ex) {
			doCatch(ex);
			return EMPTY_REPRESENTATION;
		} catch (Throwable ex) {
			ex.printStackTrace();
			doCatch(ex);
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, ex.getMessage());
		} finally {
			FilterUtil.handleAfterFilter(pservice, request, response);
		}

	}

	protected PathService getPathService() {
		return getInnerRequest().getPathService(getMySectionService().getAradon());
	}

	protected Representation doNormalHandle() throws Exception {
		Representation result = null;
		Method method = getRequest().getMethod();
		allowOtherHost();

		if (method == null) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "No method specified");
		} else if (isDisAllowMethod(method)) {
			setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		} else if (method.equals(Method.PUT)) {
			result = myPut(getRequestEntity());
		} else if (isExisting()) {
			if (method.equals(Method.GET))
				result = myGet();
			else if (method.equals(Method.POST))
				result = myPost(getRequestEntity());
			else if (method.equals(Method.DELETE))
				result = myDelete();
			else if (method.equals(Method.PUT))
				result = myPut(getRequestEntity());
			else if (method.equals(Method.HEAD))
				result = myHead();
			else if (method.equals(Method.OPTIONS)) {
				result = myOptions();
			} else {
				result = myMethodHandle(method, getRequestEntity());

			}
		} else {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		}

		return result;
	}

	protected Representation myMethodHandle(Method method, Representation entity) throws Exception {
		setStatus(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		return EMPTY_REPRESENTATION;
	}

	protected abstract Representation myPut(Representation entity) throws Exception;

	protected Representation myHead() throws Exception {
		return notImpl();
	}

	protected Representation notImpl(Representation entity) {
		throw new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED);
	}

	protected Representation notImpl() {
		throw new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED);
	}

	protected abstract Representation myDelete() throws Exception;

	protected abstract Representation myPost(Representation entity) throws Exception;

	protected abstract Representation myGet() throws Exception;

	private Representation handleLet() throws Exception {
		Representation result = null;
		if (!isExisting() && getMethod().isSafe())
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		else {
			if (isConditional())
				result = doConditionalHandle();
			else if (isNegotiated())
				result = doNegotiatedHandle();
			else
				result = doNormalHandle();
			if (getResponse() != null)
				getResponse().setEntity(result);
			if (Status.CLIENT_ERROR_METHOD_NOT_ALLOWED.equals(getStatus()))
				updateAllowedMethods();
			else if (Method.GET.equals(getMethod()) && Status.SUCCESS_OK.equals(getStatus()) && (getResponseEntity() == null || !getResponseEntity().isAvailable())) {
				getLogger().fine("A response with a 200 (Ok) status should have an entity. Changing the status to 204 (No content).");
				setStatus(Status.SUCCESS_NO_CONTENT);
			}
		}

		return result;
	}

	public InnerRequest getInnerRequest() {
		return (InnerRequest) super.getRequest();
	}

	public InnerResponse getInnerResponse() {
		return (InnerResponse) super.getResponse();
	}

	protected void allowOtherHost() {

		Series<Header> resHeader = getResponseHeader();
		resHeader.add("Access-Control-Allow-Origin", "*");
		// responseHeaders.add("Access-Control-Allow-Method", "*");
		resHeader.add("Access-Control-Request-Method", "POST,GET,OPTIONS");
		resHeader.add("XDomainRequestAllowed", "1");
		resHeader.add("Access-Control-Allow-Credentials", "1");
		resHeader.add("Access-Control-Max-Age", "1728000");

		resHeader.add("Access-Control-Allow-Headers", "X-ARADONUNER");

		// for (Entry<String, Object> attr : getResponse().getAttributes().entrySet()) {
		// Debug.line(attr.getKey(), attr.getValue(), attr.getValue().getClass());
		// }
		// Debug.line(result) ;
	}

	protected Representation myOptions() throws Exception {
		return EMPTY_REPRESENTATION;
	}

	protected Series<Header> getResponseHeader() {
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get(ATTRIBUTE_HEADERS);
		if (responseHeaders == null) {
			responseHeaders = new Series(Header.class);
			getResponse().getAttributes().put(ATTRIBUTE_HEADERS, responseHeaders);
		}
		return responseHeaders;
	}

	protected boolean isDisAllowMethod(Method method) {
		return disAllow_Method.contains(method);
	}

	public TreeContext getContext() {
		return (TreeContext) getRequest().getAttributes().get(REQUEST_CONTEXT);
	}

	private void initRequest() {
		// getFormParameter();
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

	// private Map<String, Object> toFlatMap(Map<String, Object> map) {
	// Map<String, Object> properties = new HashMap<String, Object>();
	// for (Entry<String, Object> entry : map.entrySet()) {
	// properties.put(entry.getKey(), (entry.getValue() instanceof ObjectId) ? StringUtil.toString(entry.getValue()) : entry.getValue());
	// }
	// return properties;
	// };

//	protected InboundLet lookupLet(String sectionName, String path) {
//		return InboundLet.create(getInnerRequest(), getMySectionService().getOtherSection(sectionName), path);
//	}

	protected boolean isExplorer() {
		String agent = getRequest().getClientInfo().getAgent();
		if (StringUtil.isBlank(agent)) return false ;
		return agent.indexOf("MSIE") > -1;
	}

	public SectionService getMySectionService() {
		return (SectionService) getApplication();
	}

	@Override
	public String getAttribute(String key) {
		Map<String, Object> attrs = getRequestAttributes();
		if (attrs == null) return null ;
		return (attrs.get(key) != null) ? attrs.get(key).toString() : null ;
	}

	@Override
	public void setAttribute(String name, Object value) {
		 getRequestAttributes().put(name, value);
	}
}

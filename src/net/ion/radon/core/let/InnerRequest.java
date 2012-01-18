package net.ion.radon.core.let;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.EnumClass.IFormat;
import net.ion.radon.core.IService;
import net.ion.radon.core.PageBean;
import net.ion.radon.core.PathService;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.TreeContext;
import net.ion.radon.impl.section.BasePathInfo;
import net.ion.radon.param.MyParameter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang.SystemUtils;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Uniform;
import org.restlet.data.CacheDirective;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ClientInfo;
import org.restlet.data.Conditions;
import org.restlet.data.Cookie;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.data.Range;
import org.restlet.data.RecipientInfo;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.data.Warning;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.util.Series;

public class InnerRequest extends Request {

	private final static int MAX_THRESHOLD_BYTE = 1024 * 1024 * 1; // 1M
	private final static long MAX_FILESIZE = MAX_THRESHOLD_BYTE * 100; // 100M

	private String sectionName ;
	private Request real;
	private InnerRequest caller;

	private InnerRequest(String sectionName, Request request) {
		this.sectionName = sectionName ;
		this.real = request;
		initMethod();
	}

	public void putAttribute(String key, Object value){
		getAttributes().put(key, value) ;
	}
	
	public final static InnerRequest create(String sectionName, Request request) {
		final InnerRequest innerRequest = new InnerRequest(sectionName, request);
		innerRequest.getHeaders().set("X-Aradon-Version", "0.6");
		return innerRequest;
	}
	
	public static InnerRequest create(Request request) {
		return create(getSectionName(request.getResourceRef()), request);
	}
	
	private static String getSectionName(Reference resourceRef) {
		List<String> segs = resourceRef.getSegments() ;
		if(segs.size() <= 1) {
			return "" ;
		}
		return segs.get(0);
	}

	public Series getHeaders() {
		return getHeaders(this);
	}

	public String getHeader(String key) {
		return getHeaders(this).getFirstValue(key);
	}

	public String getParameter(String name) {
		Object value = getFormParameter().get(name);
		return (value == ObjectUtil.NULL) ? "" : StringUtil.toString(value);
	}

	public int getParameterAsInteger(String name) {
		String str = getParameter(name);
		return (StringUtil.isBlank(str) || (!StringUtil.isNumeric(str))) ? 0 : Integer.parseInt(str);
	}

	public MultiValueMap getFormParameter() {
		MultiValueMap params = (MultiValueMap) getAttributes().get(RadonAttributeKey.FORM_ATTRIBUTE_KEY);
		if (params != null) {
			return params;
		} else {
			final Map<String, Object> result = makeFormParam(this);
			getAttributes().put(RadonAttributeKey.FORM_ATTRIBUTE_KEY, result);
			return getFormParameter();
		}
	}

	public PageBean getAradonPage() {
		return getAradonPage(this);
	}

	public Map<String, Object> getAradonParameter() {
		return getAradonParameter(this);
	}

	public Map<String, Object> getGeneralParameter() {
		return getGeneralParameter(this);
	}

	// format ex : json[.tplId]
	public IFormat getIFormat() {
		return getResultFormat(this).getFormat();
	}

	// format ex : json[.tplId]
	public ResultFormat getResultFormat() {
		return getResultFormat(this);
	}

	public void setResultFormat(ResultFormat format) {
		getFormParameter().remove(RadonAttributeKey.ARADON_RESULT_FORMAT);
		getFormParameter().put(RadonAttributeKey.ARADON_RESULT_FORMAT, format.toStringExpression());
	}

	public String getRemainPath() {
		return getResourceRef().getRemainingPart(true, false);
	}

	public boolean hasAttribute(String key) {
		return getAttributes().containsKey(key);
	}

	public BasePathInfo getPathInfo(IService service) {
		for (PathService pservice :service.getAradon().getChildService(sectionName).getChildren()) {
			//if (pathInfo.getPathInfo().isMatchURL(getResourceRef())) {
			if (pservice.getPathInfo().isMatchURL(getPathReference())) {
				return pservice.getPathInfo();
			}
		}
		throw new IllegalAccessError("not found path info");
	}

	private final void initMethod() {
		// getRequest().getMethod() ; // super.getMethod() ;
		final String overrideMethod = getParameter(RadonAttributeKey.ARADON_HTTP_METHOD);

		if (overrideMethod != null && overrideMethod.trim().length() > 0) {
			if (getMethod().equals(Method.POST) && overrideMethod.trim().equalsIgnoreCase(Method.PUT.getName())) {
				setMethod(Method.PUT);
			} else if (getMethod().equals(Method.POST) && overrideMethod.trim().equalsIgnoreCase(Method.DELETE.getName())) {
				setMethod(Method.DELETE);
			} else if (getMethod().equals(Method.GET) && StringUtil.isNotBlank(overrideMethod)) {
				setMethod(Method.valueOf(overrideMethod));
			}
		}
	}

	public String getAttribute(String key) {
		return getAttribute(key, String.class);
	}

	public <T> T getAttribute(String key, Class<T> clz) {
		return (T) getAttributes().get(key);
	}

	public <T> T getAttribute(String key, Class<T> clz, T dftValue) {
		Object value = getAttributes().get(key);
		return (value == null && value == ObjectUtil.NULL && clz.isInstance(value)) ? (T) value : dftValue;
	}

	public int getAttributeAsInteger(String key, int dftNum) {
		final String val = getAttribute(key);
		return StringUtil.isBlank(val) ? dftNum : Integer.parseInt(val);
	}

	public TreeContext getContext() {
		return (TreeContext) getAttributes().get(RadonAttributeKey.REQUEST_CONTEXT);
	}

	public String[] getParameterValues(String key) {
		Object value = getFormParameter().get(key);
		if (value instanceof Collection) {
			return (String[]) ((Collection) value).toArray(new String[0]);
		} else {
			return new String[] { ObjectUtil.toString(value) };
		}
	}

	public String getParameter(String key, String defaultString) {
		Object obj = getFormParameter().get(key);
		return (obj == null) ? defaultString : obj.toString();
	}

	public boolean isBlankParameter(String name) {
		return StringUtil.isBlank(getParameter(name));
	}

	private static Map<String, Object> getFormParameter(Request request) {
		Map<String, Object> params = (Map<String, Object>) request.getAttributes().get(RadonAttributeKey.FORM_ATTRIBUTE_KEY);
		if (params != null) {
			return params;
		} else {
			final Map<String, Object> result = makeFormParam(request);
			request.getAttributes().put(RadonAttributeKey.FORM_ATTRIBUTE_KEY, result);
			return getFormParameter(request);
		}
	}

	private static Map<String, Object> getAradonParameter(Request request) {
		Set<Entry<String, Object>> set = getFormParameter(request).entrySet();
		Map<String, Object> result = new HashMap<String, Object>();
		for (Entry<String, Object> entry : set) {
			if (entry.getKey().startsWith(RadonAttributeKey.ARADON_PREFIX)) {
				result.put(entry.getKey(), entry.getValue());
			}
		}
		return result;
	}

	private static Map<String, Object> getGeneralParameter(Request request) {
		Set<Entry<String, Object>> set = getFormParameter(request).entrySet();
		Map<String, Object> result = new HashMap<String, Object>();
		for (Entry<String, Object> entry : set) {
			if (entry.getKey().startsWith(RadonAttributeKey.ARADON_PREFIX))
				continue;
			result.put(entry.getKey(), entry.getValue());
		}
		return Collections.unmodifiableMap(result) ;
	}

	private static PageBean getAradonPage(Request request) {

		Map<String, Object> params = new HashMap<String, Object>(getFormParameter(request));
		MyParameter myparam = MyParameter.create(Collections.EMPTY_MAP);
		for (Entry<String, Object> entry : params.entrySet()) {
			myparam.addParam(entry.getKey(), entry.getValue());
		}

		final PageBean result = (PageBean) myparam.childParameter(RadonAttributeKey.ARADON_PAGE).toBean(PageBean.class);
		return (result == null) ? PageBean.TEN : result;
	}

	private static Map<String, Object> makeFormParam(Request request) {
		MultiValueMap params = new MultiValueMap();

		for (Parameter p : request.getResourceRef().getQueryAsForm()) { // GET
			params.putParameter(p.getName(), StringUtil.defaultIfEmpty(p.getValue(), ""));
		}

		Representation entity = request.getEntity();
		if (hasEntityBody(entity) && isMultipartRequest(entity)) {
			try {
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(MAX_THRESHOLD_BYTE);
				factory.setRepository(SystemUtils.getJavaHome());

				RestletFileUpload upload = new RestletFileUpload(factory);
				upload.setHeaderEncoding("utf-8");
				upload.setFileSizeMax(MAX_FILESIZE);

				List<FileItem> items = upload.parseRequest(request);
				for (FileItem fitem : items) {
					if (fitem.isFormField()) {
						params.putParameter(fitem.getFieldName(), fitem.getString());
					} else {
						params.putParameter(fitem.getFieldName(), fitem);
					}
				}

			} catch (FileUploadException ex) {
				throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, ex.getMessage());
			}
		} else if (isFormParameter(entity)) {
			if (entity == null || entity.getSize() == 0) return params ;
			
			final Form form = new Form(entity);
			Set<String> names = form.getNames();
			for (String name : names) { // POST or PUT or DELETE
				params.putParameter(name, form.getValuesArray(name));
			}
		} else { // not form parameter
			// Debug.debug("NOT FORM PARAM", entity, entity == null, (entity != null) ? entity.getSize() : 0) ;
		}

		return params;
	}

	private static boolean isFormParameter(Representation entity) {
		return entity != null && MediaType.APPLICATION_WWW_FORM.equals(entity.getMediaType());
	}

	private static boolean hasEntityBody(Representation entity) {
		return entity != null && entity.getSize() >= 1;
	}

	private static boolean isMultipartRequest(Representation entity) {
		return MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(), true);
	}

	private static ResultFormat getResultFormat(Request request) {
		String result = getParameterAsString(request, RadonAttributeKey.ARADON_RESULT_FORMAT);
		IFormat iformat = null;
		if (StringUtil.isEmpty(result)) {
			iformat = StringUtil.isEmpty(getHeader(request, "Referer", true)) ? IFormat.XML : IFormat.JSON;
		} else {
			String type = StringUtil.substringBefore(result, ".");
			iformat = IFormat.valueOf((StringUtil.isBlank(type) ? result : type).toUpperCase());
		}

		return ResultFormat.create(iformat, StringUtil.substringAfterLast(result, "."));
	}

	private static Series getHeaders(Request request) {
		Series result = (Series) request.getAttributes().get(RadonAttributeKey.ATTRIBUTE_HEADERS);
		if (result == null) {
			result = new Form();
			request.getAttributes().put(RadonAttributeKey.ATTRIBUTE_HEADERS, result);
		}
		return result;
	}

	private static String getHeader(Request request, String headerName, boolean ignoreCase) {
		return getHeaders(request).getFirstValue(headerName, ignoreCase);
	}

	private static String getParameterAsString(Request request, String name) {
		return StringUtil.toString(getFormParameter(request).get(name));
	}

	private static int getParameterAsInteger(Request request, String name) {
		String str = getParameterAsString(request, name);
		return (StringUtil.isBlank(str) || (!StringUtil.isNumeric(str))) ? 0 : Integer.parseInt(str);
	}

	public void setCookies(Series<Cookie> cookies) {
		real.setCookies(cookies);
	}

	public void setRanges(List<Range> ranges) {
		real.setRanges(ranges);
	}

	public boolean abort() {
		return real.abort();
	}

	public void commit(Response response) {
		real.commit(response);
	}

	public ChallengeResponse getChallengeResponse() {
		return real.getChallengeResponse();
	}

	public ClientInfo getClientInfo() {
		return real.getClientInfo();
	}

	public Conditions getConditions() {
		return real.getConditions();
	}

	public Series<Cookie> getCookies() {
		return real.getCookies();
	}

	public Reference getHostRef() {
		return real.getHostRef();
	}

	public int getMaxForwards() {
		return real.getMaxForwards();
	}

	public Method getMethod() {
		return real.getMethod();
	}

	public Uniform getOnResponse() {
		return real.getOnResponse();
	}

	public Reference getOriginalRef() {
		return real.getOriginalRef();
	}

	public Protocol getProtocol() {
		return real.getProtocol();
	}

	public ChallengeResponse getProxyChallengeResponse() {
		return real.getProxyChallengeResponse();
	}

	public List<Range> getRanges() {
		return real.getRanges();
	}

	public Reference getReferrerRef() {
		return real.getReferrerRef();
	}

	public Reference getResourceRef() {
		return real.getResourceRef();
	}

	public Reference getRootRef() {
		return real.getRootRef();
	}

	public boolean isConfidential() {
		return real.isConfidential();
	}

	public boolean isExpectingResponse() {
		return real.isExpectingResponse();
	}

	public void setChallengeResponse(ChallengeResponse challengeResponse) {
		real.setChallengeResponse(challengeResponse);
	}

	public void setClientInfo(ClientInfo clientInfo) {
		real.setClientInfo(clientInfo);
	}

	public void setConditions(Conditions conditions) {
		real.setConditions(conditions);
	}

	public void setHostRef(Reference hostRef) {
		real.setHostRef(hostRef);
	}

	public void setHostRef(String hostUri) {
		real.setHostRef(hostUri);
	}

	public void setMaxForwards(int maxForwards) {
		real.setMaxForwards(maxForwards);
	}

	public void setMethod(Method method) {
		real.setMethod(method);
	}

	public void setOnResponse(Uniform onResponseCallback) {
		real.setOnResponse(onResponseCallback);
	}

	public void setOriginalRef(Reference originalRef) {
		real.setOriginalRef(originalRef);
	}

	public void setProtocol(Protocol protocol) {
		real.setProtocol(protocol);
	}

	public void setProxyChallengeResponse(ChallengeResponse challengeResponse) {
		real.setProxyChallengeResponse(challengeResponse);
	}

	public void setReferrerRef(Reference referrerRef) {
		real.setReferrerRef(referrerRef);
	}

	public void setReferrerRef(String referrerUri) {
		real.setReferrerRef(referrerUri);
	}

	public void setResourceRef(Reference resourceRef) {
		real.setResourceRef(resourceRef);
	}

	public void setResourceRef(String resourceUri) {
		real.setResourceRef(resourceUri);
	}

	public void setRootRef(Reference rootRef) {
		real.setRootRef(rootRef);
	}

	public String toString() {
		return real.toString();
	}

	public void setAttributes(Map<String, Object> attributes) {
		real.setAttributes(attributes);
	}

	public void setCacheDirectives(List cacheDirectives) {
		real.setCacheDirectives(cacheDirectives);
	}

	public void setRecipientsInfo(List recipientsInfo) {
		real.setRecipientsInfo(recipientsInfo);
	}

	public void setWarnings(List<Warning> warnings) {
		real.setWarnings(warnings);
	}

	public ConcurrentMap<String, Object> getAttributes() {
		return real.getAttributes();
	}

	public List<CacheDirective> getCacheDirectives() {
		return real.getCacheDirectives();
	}

	public Date getDate() {
		return real.getDate();
	}

	public void setEntity(Representation entity) {
		real.setEntity(entity);
	}

	public Representation getEntity() {
		return real.getEntity();
	}

	public String getEntityAsText() {
		return real.getEntityAsText();
	}

	public Uniform getOnSent() {
		return real.getOnSent();
	}

	public List<RecipientInfo> getRecipientsInfo() {
		return real.getRecipientsInfo();
	}

	public List<Warning> getWarnings() {
		return real.getWarnings();
	}

	public boolean isEntityAvailable() {
		return real.isEntityAvailable();
	}

	public void release() {
		real.release();
	}

	public void setDate(Date date) {
		real.setDate(date);
	}

	public void setEntity(String value, MediaType mediaType) {
		real.setEntity(value, mediaType);
	}

	public void setOnSent(Uniform onSentCallback) {
		real.setOnSent(onSentCallback);
	}

	public void setCaller(InnerRequest caller) {
		this.caller = caller ;
	}

	public InnerRequest getCaller() {
		return this.caller ;		
	}
	
	public Reference getPathReference(){

		final Reference resourceRef = getResourceRef();
		
		if (StringUtil.isBlank(sectionName)) {
			return new Reference(resourceRef.getPath()) ;
		} else {
			String path = StringUtil.substringAfter(resourceRef.getPath(), "/" + sectionName) ;
			if (StringUtil.isEmpty(path)) 
				return new Reference(resourceRef.getPath()) ;
			return new Reference(path);
		}
	}

	public PathService getPathService(IService service) {
		return service.getAradon().getChildService(sectionName).getPathService(getPathReference());
	}

	public String getSectionName(){
		return sectionName ;
	}


}

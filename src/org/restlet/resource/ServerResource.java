/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   ServerResource.java

package org.restlet.resource;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import net.ion.nradon.let.IServiceLet;

import org.restlet.Uniform;
import org.restlet.data.Dimension;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.data.ServerInfo;
import org.restlet.data.Status;
import org.restlet.engine.resource.AnnotationInfo;
import org.restlet.engine.resource.AnnotationUtils;
import org.restlet.engine.resource.VariantInfo;
import org.restlet.representation.Representation;
import org.restlet.representation.RepresentationInfo;
import org.restlet.representation.Variant;
import org.restlet.util.Series;

// Referenced classes of package org.restlet.resource:
//            UniformResource, ResourceException

public abstract class ServerResource extends UniformResource implements IServiceLet {

	private volatile boolean annotated;
	private volatile boolean conditional;
	private volatile boolean existing;
	private volatile boolean negotiated;
	private volatile List variants;

	public ServerResource() {
		annotated = true;
		conditional = true;
		existing = true;
		negotiated = true;
		variants = null;
	}

	public void abort() {
		getResponse().abort();
	}

	public void commit() {
		getResponse().commit();
	}

	protected Representation delete() throws ResourceException {
		Representation result = null;
		AnnotationInfo annotationInfo = getAnnotation(Method.DELETE);
		if (annotationInfo != null)
			result = doHandle(annotationInfo, null);
		else
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		return result;
	}

	protected Representation delete(Variant variant) throws ResourceException {
		Representation result = null;
		if (variant instanceof VariantInfo)
			result = doHandle(((VariantInfo) variant).getAnnotationInfo(), variant);
		else
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		return result;
	}

	protected Representation describeVariants() {
		Representation result = null;
		return result;
	}

	protected void doCatch(Throwable throwable) {
		Level level = Level.INFO;
		Status status = getStatusService().getStatus(throwable, this);
		if (status.isServerError())
			level = Level.WARNING;
		else if (status.isConnectorError())
			level = Level.INFO;
		else if (status.isClientError())
			level = Level.FINE;
		getLogger().log(level, "Exception or error caught in server resource", throwable);
		if (getResponse() != null)
			getResponse().setStatus(status);
	}

	protected Representation doConditionalHandle() throws ResourceException {
		Representation result = null;
		if (getConditions().hasSome()) {
			RepresentationInfo resultInfo = null;
			if (existing) {
				if (isNegotiated())
					resultInfo = doGetInfo(getPreferredVariant(getVariants(Method.GET)));
				else
					resultInfo = doGetInfo();
				if (resultInfo == null) {
					if (getStatus() == null || getStatus().isSuccess() && !Status.SUCCESS_NO_CONTENT.equals(getStatus()))
						doError(Status.CLIENT_ERROR_NOT_FOUND);
				} else {
					Status status = getConditions().getStatus(getMethod(), resultInfo);
					if (status != null)
						if (status.isError())
							doError(status);
						else
							setStatus(status);
				}
			} else {
				Status status = getConditions().getStatus(getMethod(), resultInfo);
				if (status != null)
					if (status.isError())
						doError(status);
					else
						setStatus(status);
			}
			if ((Method.GET.equals(getMethod()) || Method.HEAD.equals(getMethod())) && (resultInfo instanceof Representation))
				result = (Representation) resultInfo;
			else if (getStatus() != null && getStatus().isSuccess())
				if (isNegotiated()) {
					getVariants().clear();
					result = doNegotiatedHandle();
				} else {
					result = doHandle();
				}
		} else if (isNegotiated())
			result = doNegotiatedHandle();
		else
			result = doHandle();
		return result;
	}

	protected void doError(Status errorStatus) {
		setStatus(errorStatus);
	}

	private RepresentationInfo doGetInfo() throws ResourceException {
		RepresentationInfo result = null;
		AnnotationInfo annotationInfo = getAnnotation(Method.GET);
		if (annotationInfo != null)
			result = doHandle(annotationInfo, null);
		else
			result = getInfo();
		return result;
	}

	private RepresentationInfo doGetInfo(Variant variant) throws ResourceException {
		RepresentationInfo result = null;
		if (variant != null) {
			if (variant instanceof VariantInfo)
				result = doHandle(((VariantInfo) variant).getAnnotationInfo(), variant);
			else if (variant instanceof RepresentationInfo)
				result = (RepresentationInfo) variant;
			else
				result = getInfo(variant);
		} else {
			result = doGetInfo();
		}
		return result;
	}

	protected Representation doHandle() throws ResourceException {
		Representation result = null;
		Method method = getMethod();
		if (method == null)
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "No method specified");
		else if (method.equals(Method.PUT))
			result = put(getRequestEntity());
		else if (isExisting()) {
			if (method.equals(Method.GET))
				result = get();
			else if (method.equals(Method.POST))
				result = post(getRequestEntity());
			else if (method.equals(Method.DELETE))
				result = delete();
			else if (method.equals(Method.HEAD))
				result = head();
			else if (method.equals(Method.OPTIONS))
				result = options();
			else
				result = doHandle(method, getQuery(), getRequestEntity());
		} else {
			doError(Status.CLIENT_ERROR_NOT_FOUND);
		}
		return result;
	}

	private Representation doHandle(AnnotationInfo annotationInfo, Variant variant) throws ResourceException {
		Representation result = null;
		Class parameterTypes[] = annotationInfo.getJavaInputTypes();
		Object resultObject = null;
		try {
			if (parameterTypes.length > 0) {
				List parameters = new ArrayList();
				Object parameter = null;
				Class arr$[] = parameterTypes;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++) {
					Class parameterType = arr$[i$];
					if (Variant.class.equals(parameterType)) {
						parameters.add(variant);
						continue;
					}
					if (getRequestEntity() != null && getRequestEntity().isAvailable() && getRequestEntity().getSize() != 0L) {
						parameter = toObject(getRequestEntity(), parameterType);
						if (parameter == null)
							throw new ResourceException(Status.CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE);
					} else {
						parameter = null;
					}
					parameters.add(parameter);
				}

				resultObject = annotationInfo.getJavaMethod().invoke(this, parameters.toArray());
			} else {
				resultObject = annotationInfo.getJavaMethod().invoke(this, new Object[0]);
			}
		} catch (IllegalArgumentException e) {
			throw new ResourceException(e);
		} catch (IllegalAccessException e) {
			throw new ResourceException(e);
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof ResourceException)
				throw (ResourceException) e.getTargetException();
			else
				throw new ResourceException(e.getTargetException());
		}
		if (resultObject != null)
			result = toRepresentation(resultObject, variant);
		return result;
	}

	private Representation doHandle(Method method, Form query, Representation entity) {
		Representation result = null;
		if (getAnnotation(method) != null) {
			AnnotationInfo annotationInfo = getAnnotation(method, query, entity);
			if (annotationInfo != null)
				result = doHandle(annotationInfo, null);
			else
				doError(Status.CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE);
		} else {
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		}
		return result;
	}

	protected Representation doHandle(Variant variant) throws ResourceException {
		Representation result = null;
		Method method = getMethod();
		if (method == null)
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST, "No method specified");
		else if (method.equals(Method.PUT))
			result = put(getRequestEntity(), variant);
		else if (isExisting()) {
			if (method.equals(Method.GET)) {
				if (variant instanceof Representation)
					result = (Representation) variant;
				else
					result = get(variant);
			} else if (method.equals(Method.POST))
				result = post(getRequestEntity(), variant);
			else if (method.equals(Method.DELETE))
				result = delete(variant);
			else if (method.equals(Method.HEAD)) {
				if (variant instanceof Representation)
					result = (Representation) variant;
				else
					result = head(variant);
			} else if (method.equals(Method.OPTIONS)) {
				if (variant instanceof Representation)
					result = (Representation) variant;
				else
					result = options(variant);
			} else if (variant instanceof VariantInfo)
				result = doHandle(((VariantInfo) variant).getAnnotationInfo(), variant);
			else
				doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		} else {
			doError(Status.CLIENT_ERROR_NOT_FOUND);
		}
		return result;
	}

	protected Representation doNegotiatedHandle() throws ResourceException {
		Representation result = null;
		if (getVariants() != null && !getVariants().isEmpty()) {
			Variant preferredVariant = getPreferredVariant(getVariants());
			if (preferredVariant == null) {
				doError(Status.CLIENT_ERROR_NOT_ACCEPTABLE);
				result = describeVariants();
			} else {
				updateDimensions();
				result = doHandle(preferredVariant);
			}
		} else {
			result = doHandle();
		}
		return result;
	}

	protected Representation get() throws ResourceException {
		Representation result = null;
		AnnotationInfo annotationInfo = getAnnotation(Method.GET);
		if (annotationInfo != null)
			result = doHandle(annotationInfo, null);
		else
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		return result;
	}

	protected Representation get(Variant variant) throws ResourceException {
		Representation result = null;
		if (variant instanceof VariantInfo)
			result = doHandle(((VariantInfo) variant).getAnnotationInfo(), variant);
		else
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		return result;
	}

	private AnnotationInfo getAnnotation(Method method) {
		return getAnnotation(method, getQuery(), null);
	}

	private AnnotationInfo getAnnotation(Method method, Form query, Representation entity) {
		if (isAnnotated())
			return AnnotationUtils.getAnnotation(getAnnotations(), method, query, entity, getMetadataService(), getConverterService());
		else
			return null;
	}

	private List getAnnotations() {
		return isAnnotated() ? AnnotationUtils.getAnnotations(getClass()) : null;
	}

	protected RepresentationInfo getInfo() throws ResourceException {
		return get();
	}

	protected RepresentationInfo getInfo(Variant variant) throws ResourceException {
		return get(variant);
	}

	public Uniform getOnSent() {
		return getResponse().getOnSent();
	}

	protected Variant getPreferredVariant(List variants) {
		Variant result = null;
		if (variants != null && !variants.isEmpty())
			result = getConnegService().getPreferredVariant(variants, getRequest(), getMetadataService());
		return result;
	}

	public List getVariants() {
		return getVariants(getMethod());
	}

	protected List getVariants(Method method) {
		List result = variants;
		if (result == null) {
			result = new ArrayList();
			if (isAnnotated() && hasAnnotations()) {
				List annoVariants = null;
				method = Method.HEAD.equals(method) ? Method.GET : method;
				Iterator i$ = getAnnotations().iterator();
				do {
					if (!i$.hasNext())
						break;
					AnnotationInfo annotationInfo = (AnnotationInfo) i$.next();
					if (annotationInfo.isCompatible(method, getQuery(), getRequestEntity(), getMetadataService(), getConverterService())) {
						annoVariants = annotationInfo.getResponseVariants(getMetadataService(), getConverterService());
						if (annoVariants != null) {
							Iterator it = annoVariants.iterator();
							while (it.hasNext()) {
								Variant v = (Variant) it.next();
								result.add(new VariantInfo(v, annotationInfo));
							}
						}
					}
				} while (true);
			}
			variants = result;
		}
		return result;
	}

	public Representation handle() {
		Representation result = null;
		if (!isExisting() && getMethod().isSafe())
			doError(Status.CLIENT_ERROR_NOT_FOUND);
		else
			try {
				if (isConditional())
					result = doConditionalHandle();
				else if (isNegotiated())
					result = doNegotiatedHandle();
				else
					result = doHandle();
				if (!getResponse().isEntityAvailable())
					getResponse().setEntity(result);
				if (Status.CLIENT_ERROR_METHOD_NOT_ALLOWED.equals(getStatus()))
					updateAllowedMethods();
				else if (Method.GET.equals(getMethod()) && Status.SUCCESS_OK.equals(getStatus()) && (getResponseEntity() == null || !getResponseEntity().isAvailable())) {
					getLogger().fine("A response with a 200 (Ok) status should have an entity. Changing the status to 204 (No content).");
					setStatus(Status.SUCCESS_NO_CONTENT);
				}
			} catch (Throwable t) {
				doCatch(t);
			}
		return result;
	}

	protected boolean hasAnnotations() {
		return getAnnotations() != null && !getAnnotations().isEmpty();
	}

	protected Representation head() throws ResourceException {
		return get();
	}

	protected Representation head(Variant variant) throws ResourceException {
		return get(variant);
	}

	public boolean isAnnotated() {
		return annotated;
	}

	public boolean isAutoCommitting() {
		return getResponse().isAutoCommitting();
	}

	public boolean isCommitted() {
		return getResponse().isCommitted();
	}

	public boolean isConditional() {
		return conditional;
	}

	public boolean isExisting() {
		return existing;
	}

	public boolean isInRole(String roleName) {
		return getClientInfo().getRoles().contains(getApplication().getRole(roleName));
	}

	public boolean isNegotiated() {
		return negotiated;
	}

	protected Representation options() throws ResourceException {
		Representation result = null;
		AnnotationInfo annotationInfo = getAnnotation(Method.OPTIONS);
		updateAllowedMethods();
		if (annotationInfo != null)
			result = doHandle(annotationInfo, null);
		else
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		return result;
	}

	protected Representation options(Variant variant) throws ResourceException {
		Representation result = null;
		updateAllowedMethods();
		if (variant instanceof VariantInfo)
			result = doHandle(((VariantInfo) variant).getAnnotationInfo(), variant);
		else
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		return result;
	}

	protected Representation post(Representation entity) throws ResourceException {
		return doHandle(Method.POST, getQuery(), entity);
	}

	protected Representation post(Representation entity, Variant variant) throws ResourceException {
		Representation result = null;
		if (variant instanceof VariantInfo)
			result = doHandle(((VariantInfo) variant).getAnnotationInfo(), variant);
		else
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		return result;
	}

	protected Representation put(Representation entity) throws ResourceException {
		return doHandle(Method.PUT, getQuery(), entity);
	}

	protected Representation put(Representation representation, Variant variant) throws ResourceException {
		Representation result = null;
		if (variant instanceof VariantInfo)
			result = doHandle(((VariantInfo) variant).getAnnotationInfo(), variant);
		else
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		return result;
	}

	public void redirectPermanent(Reference targetRef) {
		if (getResponse() != null)
			getResponse().redirectPermanent(targetRef);
	}

	public void redirectPermanent(String targetUri) {
		if (getResponse() != null)
			getResponse().redirectPermanent(targetUri);
	}

	public void redirectSeeOther(Reference targetRef) {
		if (getResponse() != null)
			getResponse().redirectSeeOther(targetRef);
	}

	public void redirectSeeOther(String targetUri) {
		if (getResponse() != null)
			getResponse().redirectSeeOther(targetUri);
	}

	public void redirectTemporary(Reference targetRef) {
		if (getResponse() != null)
			getResponse().redirectTemporary(targetRef);
	}

	public void redirectTemporary(String targetUri) {
		if (getResponse() != null)
			getResponse().redirectTemporary(targetUri);
	}

	public void setAllowedMethods(Set allowedMethods) {
		if (getResponse() != null)
			getResponse().setAllowedMethods(allowedMethods);
	}

	public void setAnnotated(boolean annotated) {
		this.annotated = annotated;
	}

	public void setAutoCommitting(boolean autoCommitting) {
		getResponse().setAutoCommitting(autoCommitting);
	}

	public void setChallengeRequests(List requests) {
		if (getResponse() != null)
			getResponse().setChallengeRequests(requests);
	}

	public void setCommitted(boolean committed) {
		getResponse().setCommitted(committed);
	}

	public void setConditional(boolean conditional) {
		this.conditional = conditional;
	}

	public void setCookieSettings(Series cookieSettings) {
		if (getResponse() != null)
			getResponse().setCookieSettings(cookieSettings);
	}

	public void setDimensions(Set dimensions) {
		if (getResponse() != null)
			getResponse().setDimensions(dimensions);
	}

	public void setExisting(boolean exists) {
		existing = exists;
	}

	public void setLocationRef(Reference locationRef) {
		if (getResponse() != null)
			getResponse().setLocationRef(locationRef);
	}

	public void setLocationRef(String locationUri) {
		if (getResponse() != null)
			getResponse().setLocationRef(locationUri);
	}

	public void setNegotiated(boolean negotiateContent) {
		negotiated = negotiateContent;
	}

	public void setOnSent(Uniform onSentCallback) {
		getResponse().setOnSent(onSentCallback);
	}

	public void setServerInfo(ServerInfo serverInfo) {
		if (getResponse() != null)
			getResponse().setServerInfo(serverInfo);
	}

	public void setStatus(Status status) {
		if (getResponse() != null)
			getResponse().setStatus(status);
	}

	public void setStatus(Status status, String message) {
		if (getResponse() != null)
			getResponse().setStatus(status, message);
	}

	public void setStatus(Status status, Throwable throwable) {
		if (getResponse() != null)
			getResponse().setStatus(status, throwable);
	}

	public void setStatus(Status status, Throwable throwable, String message) {
		if (getResponse() != null)
			getResponse().setStatus(status, throwable, message);
	}

	public void updateAllowedMethods() {
		getAllowedMethods().clear();
		List annotations = getAnnotations();
		if (annotations != null) {
			Iterator i$ = annotations.iterator();
			do {
				if (!i$.hasNext())
					break;
				AnnotationInfo annotationInfo = (AnnotationInfo) i$.next();
				if (!getAllowedMethods().contains(annotationInfo.getRestletMethod()))
					getAllowedMethods().add(annotationInfo.getRestletMethod());
			} while (true);
		}
	}

	protected void updateDimensions() {
		getDimensions().add(Dimension.CHARACTER_SET);
		getDimensions().add(Dimension.ENCODING);
		getDimensions().add(Dimension.LANGUAGE);
		getDimensions().add(Dimension.MEDIA_TYPE);
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

/*
 * DECOMPILATION REPORT
 * 
 * Decompiled from: C:\Users\bleujin\workspace\ARadon\aradon_lib\aradon\embed\rest_fat.jar Total time: 43 ms Jad reported messages/errors: Exit status: 0 Caught exceptions:
 */
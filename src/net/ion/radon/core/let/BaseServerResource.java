package net.ion.radon.core.let;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.ion.framework.util.ObjectUtil;

import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.engine.resource.AnnotationInfo;
import org.restlet.engine.resource.AnnotationUtils;
import org.restlet.engine.resource.VariantInfo;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

@SuppressWarnings("deprecation")
public abstract class BaseServerResource extends ServerResource {

	public BaseServerResource() {
	}

	protected Representation delete() throws ResourceException {
		Representation result = null;
		AnnotationInfo annotationInfo = getAnnotation(Method.DELETE);

		if (annotationInfo != null) {
			result = doHandle(annotationInfo, null);
		} else {
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		}
		return result;
	}

	protected Representation delete(Variant variant) throws ResourceException {
		Representation result = null;

		if (variant instanceof VariantInfo) {
			result = doHandle(((VariantInfo) variant).getAnnotationInfo(), variant);
		} else {
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
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
		Class<?>[] parameterTypes = annotationInfo.getJavaInputTypes();

		// Invoke the annotated method and get the resulting object.
		Object resultObject = null;
		try {
			java.lang.reflect.Method mtd = annotationInfo.getJavaMethod();
			mtd.setAccessible(true);
			if (parameterTypes.length > 0) {
				List<Object> parameters = new ArrayList<Object>();
				Object parameter = null;

				for (Class<?> parameterType : parameterTypes) {
					if (Variant.class.equals(parameterType)) {
						parameters.add(variant);
					} else {
						if (getRequestEntity() != null && getRequestEntity().isAvailable() && getRequestEntity().getSize() != 0) {
							// Assume there is content to be read.
							// NB: it does not handle the case where the size is unknown, but there is no content.
							parameter = toObject(getRequestEntity(), parameterType);

							if (parameter == null) {
								throw new ResourceException(Status.CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE);
							}
						} else {
							parameter = null;
						}

						parameters.add(parameter);
					}
				}

				resultObject = mtd.invoke(this, parameters.toArray());
			} else {
				resultObject = mtd.invoke(this);
			}
		} catch (IllegalArgumentException e) {
			throw new ResourceException(e);
		} catch (IllegalAccessException e) {
			throw new ResourceException(e);
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof ResourceException) {
				throw (ResourceException) e.getTargetException();
			}

			throw new ResourceException(e.getTargetException());
		}

		if (resultObject != null) {
			result = toRepresentation(resultObject, variant);
		}

		return result;
	}

	protected Representation get() throws ResourceException {
		Representation result = null;
		AnnotationInfo annotationInfo = getAnnotation(Method.GET);

		if (annotationInfo != null) {
			result = doHandle(annotationInfo, null);
		} else {
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		}

		return result;
	}

	protected Representation get(Variant variant) throws ResourceException {
		Representation result = null;

		if (variant instanceof VariantInfo) {
			result = doHandle(((VariantInfo) variant).getAnnotationInfo(), variant);
		} else {
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		}

		return result;
	}

	private AnnotationInfo getAnnotation(Method method) {
		return getAnnotation(method, getQuery(), null);
	}

	private AnnotationInfo getAnnotation(Method method, Form query, Representation entity) {
		if (isAnnotated()) {
			return AnnotationUtils.getAnnotation(getAnnotations(), method, query, entity, getMetadataService(), getConverterService());
		}

		return null;
	}

	private List<AnnotationInfo> getAnnotations() {
		return isAnnotated() ? AnnotationUtils.getAnnotations(getClass()) : null;
	}

	protected Representation head() throws ResourceException {
		return get();
	}

	protected Representation head(Variant variant) throws ResourceException {
		return get(variant);
	}

	protected Representation options() throws ResourceException {
		Representation result = null;
		AnnotationInfo annotationInfo = getAnnotation(Method.OPTIONS);

		// Updates the list of allowed methods
		updateAllowedMethods();

		if (annotationInfo != null) {
			result = doHandle(annotationInfo, null);
		} else {
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		}

		return result;
	}

	protected Representation options(Variant variant) throws ResourceException {
		Representation result = null;

		// Updates the list of allowed methods
		updateAllowedMethods();

		if (variant instanceof VariantInfo) {
			result = doHandle(((VariantInfo) variant).getAnnotationInfo(), variant);
		} else {
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		}

		return result;
	}

	protected Representation post(Representation entity) throws ResourceException {
		return doHandle(Method.POST, getQuery(), entity);
	}

	private Representation doHandle(Method method, Form query, Representation entity) {
		Representation result = null;

		if (getAnnotation(method) != null) {
			// We know the method is supported, let's check the entity.
			AnnotationInfo annotationInfo = ObjectUtil.coalesce(getAnnotation(method, query, entity), getAnnotation(method));

			if (annotationInfo != null) {
				result = doHandle(annotationInfo, null);
			} else {
				// The request entity is not supported.
				doError(Status.CLIENT_ERROR_UNSUPPORTED_MEDIA_TYPE);
			}
		} else {
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		}
		return result;
	}

	protected Representation post(Representation entity, Variant variant) throws ResourceException {
		Representation result = null;

		if (variant instanceof VariantInfo) {
			result = doHandle(((VariantInfo) variant).getAnnotationInfo(), variant);
		} else {
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		}

		return result;
	}

	protected Representation put(Representation entity) throws ResourceException {
		return doHandle(Method.PUT, getQuery(), entity);
	}

	protected Representation put(Representation representation, Variant variant) throws ResourceException {
		Representation result = null;

		if (variant instanceof VariantInfo) {
			result = doHandle(((VariantInfo) variant).getAnnotationInfo(), variant);
		} else {
			doError(Status.CLIENT_ERROR_METHOD_NOT_ALLOWED);
		}

		return result;
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

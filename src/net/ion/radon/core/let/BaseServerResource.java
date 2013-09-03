package net.ion.radon.core.let;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.annotation.AnContext;
import net.ion.radon.core.annotation.AnRequest;
import net.ion.radon.core.annotation.AnResponse;
import net.ion.radon.core.annotation.ContextParam;
import net.ion.radon.core.annotation.CookieParam;
import net.ion.radon.core.annotation.DefaultValue;
import net.ion.radon.core.annotation.FormBean;
import net.ion.radon.core.annotation.FormDataParam;
import net.ion.radon.core.annotation.FormParam;
import net.ion.radon.core.annotation.FormParams;
import net.ion.radon.core.annotation.HeaderParam;
import net.ion.radon.core.annotation.MatrixParam;
import net.ion.radon.core.annotation.PathParam;
import net.ion.radon.core.config.PathConfiguration;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.fileupload.FileItem;
import org.restlet.data.Cookie;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.engine.resource.AnnotationInfo;
import org.restlet.engine.resource.AnnotationUtils;
import org.restlet.engine.resource.VariantInfo;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

@SuppressWarnings("deprecation")
public abstract class BaseServerResource extends ServerResource {

	public BaseServerResource() {
		getVariants().add(new Variant(MediaType.ALL));
		setConditional(false);
		setNegotiated(false);
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
		return doHandle(annotationInfo, variant, this);
	}

	protected Representation doHandle(AnnotationInfo annotationInfo, Variant variant, Object target) throws ResourceException{
		Representation result = null;
		if (annotationInfo == null) throw new ResourceException(405) ;
		
		Class<?>[] parameterTypes = annotationInfo.getJavaInputTypes();

		// Invoke the annotated method and get the resulting object.
		Object resultObject = null;
		try {
			java.lang.reflect.Method mtd = annotationInfo.getJavaMethod();
			mtd.setAccessible(true);

			if (mtd.getParameterAnnotations().length > 0 && mtd.getParameterAnnotations()[0].length > 0) {
				int idx = 0;
				List<Object> parameters = new ArrayList<Object>();
				for (Annotation[] paramAnnos : mtd.getParameterAnnotations()) {
					final Class<?> parameterType = parameterTypes[idx++];
					parameters.add(ParamAnnotation.create(getRequestAttributes(), getMatrix(), getCookies(), (TreeContext)getContext(), (InnerRequest)getRequest(), (InnerResponse)getResponse(), paramAnnos, parameterType).paramValue());
				}

				resultObject = MethodUtils.invokeMethod(target, mtd.getName(), parameters.toArray(), parameterTypes);

				// resultObject = mtd.invoke(this, parameters.toArray());

			} else { // old
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

					resultObject = mtd.invoke(target, parameters.toArray());
				} else {
					resultObject = mtd.invoke(target, new Object[0]);
				}
			}
		} catch (IllegalArgumentException e) {
			throw new ResourceException(e);
		} catch (IllegalAccessException e) {
			throw new ResourceException(e);
		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof ResourceException) {
				throw (ResourceException) e.getTargetException();
			}
			
			e.printStackTrace();
			throw new ResourceException(e.getTargetException());
		} catch (NoSuchMethodException e) {
			throw new ResourceException(e);
		} catch (Throwable e) {
			throw new ResourceException(e);
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
		if (attrs == null)
			return null;
		return (attrs.get(key) != null) ? attrs.get(key).toString() : null;
	}

	@Override
	public void setAttribute(String name, Object value) {
		getRequestAttributes().put(name, value);
	}

}

class ParamAnnotation {

	private final Map<String, Object> requestAttributes;
	private final Annotation[] paramAnnos;
	private final Class<?> parameterType;
	private final Form matrix;
	private final Series<Cookie> cookies;
	private final TreeContext context;
	private final InnerRequest request ;
	private final InnerResponse response ;

	private ParamAnnotation(Map<String, Object> requestAttributes, Form matrix, Series<Cookie> cookies, TreeContext context, InnerRequest irequest, InnerResponse iresponse, Annotation[] paramAnnos, Class<?> parameterType) {
		this.requestAttributes = requestAttributes;
		this.matrix = matrix;
		this.cookies = cookies;
		this.context = context;
		this.request = irequest ;
		this.response = iresponse ;
		this.paramAnnos = paramAnnos;
		this.parameterType = parameterType;
	}

	public static ParamAnnotation create(Map<String, Object> requestAttributes, Form matrix, Series<Cookie> cookies, TreeContext context, InnerRequest irequest, InnerResponse iresponse, Annotation[] paramAnnos, Class<?> parameterType) {
		return new ParamAnnotation(requestAttributes, matrix, cookies, context, irequest, iresponse, paramAnnos, parameterType);
	}

	public Object paramValue() throws IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {

		MultiValueMap form = (MultiValueMap) requestAttributes.get(Form.class.getCanonicalName());
		PathConfiguration pconfig = (PathConfiguration) requestAttributes.get(PathConfiguration.class.getCanonicalName());
		Series headers = (Series) requestAttributes.get("org.restlet.http.headers");

		Object defaultValue = null;
		Object resultValue = null;

		for (Annotation an : paramAnnos) {
			if (an instanceof AnContext) {
				resultValue = context ;
			} else if (an instanceof AnRequest) {
				resultValue = request ;
			} else if (an instanceof AnResponse) {
				resultValue = response ;
			} else if (an instanceof FormBean) {
				resultValue = JsonParser.fromMap(form).getAsObject(parameterType);
			} else {
				final String valueName = an.getClass().getMethod("value").invoke(an).toString();
				if (an instanceof PathParam) {
					resultValue = typeValue(parameterType, requestAttributes.get(valueName)) ;
				} else if (parameterType.isArray() || an instanceof FormParams) {
					// List values = form.getAsList(((FormParams) an).value());
					List values = form.getAsList(valueName);
					Object oarray = Array.newInstance(parameterType.getComponentType(), values.size());
					int i = 0;
					for (Object v : values) {
						Array.set(oarray, i++, stringToPrimitiveBoxType(parameterType.getComponentType(), ObjectUtil.toString(v)));
					}
					resultValue = oarray;
				} else if (parameterType.isAssignableFrom(FileItem.class) || an instanceof FormDataParam) {
					return (FileItem) form.getFirstValue(valueName);
				} else if (an instanceof FormParam) {
					resultValue = typeValue(parameterType, form.getFirstValue(valueName)) ;
				} else if (an instanceof HeaderParam) {
					resultValue = typeValue(parameterType, headers.getFirstValue(valueName));
				} else if (an instanceof CookieParam) {
					resultValue = typeValue(parameterType, cookies.getValues(valueName, (String) null, true));
				} else if (an instanceof MatrixParam) {
					resultValue = typeValue(parameterType, matrix.getValues(valueName, (String) null, true));
				} else if (an instanceof ContextParam) {
					resultValue = context.getAttributeObject(valueName);
				} else if (an instanceof DefaultValue) {
					defaultValue = ((DefaultValue) an).value();
				} else {
					resultValue = null;
				}
			}

		}

		resultValue = ObjectUtil.coalesce(resultValue, typeValue(parameterType, defaultValue));

		return resultValue;
	}
	
	private static Object typeValue(Class parameterType, Object value){
		if (! parameterType.isPrimitive()) return value ;
		return stringToPrimitiveBoxType(parameterType, ObjectUtil.toString(value)) ;
	}

	private static Object stringToPrimitiveBoxType(Class primitiveType, String value) {
		if (primitiveType.equals(String.class))
			return value;
		if (primitiveType.equals(boolean.class)) {
			if (value == null)
				return Boolean.FALSE;
			return Boolean.valueOf(value);
		}
		if (value == null || StringUtil.isBlank(value))
			return null;
		if (primitiveType.equals(int.class))
			return Integer.valueOf(value).intValue();
		if (primitiveType.equals(long.class))
			return Long.valueOf(value).longValue();
		if (primitiveType.equals(double.class))
			return Double.valueOf(value).doubleValue();
		if (primitiveType.equals(float.class))
			return Float.valueOf(value).floatValue();
		if (primitiveType.equals(byte.class))
			return Byte.valueOf(value).byteValue();
		if (primitiveType.equals(short.class))
			return Short.valueOf(value).shortValue();
		if (primitiveType.equals(boolean.class))
			return Boolean.valueOf(value).booleanValue();
		return null;

	}

}
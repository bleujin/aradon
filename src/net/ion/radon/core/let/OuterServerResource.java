package net.ion.radon.core.let;

import java.lang.reflect.Constructor;
import java.util.List;

import net.ion.nradon.let.IServiceLet;
import net.ion.nradon.let.IServiceLetWithInit;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.except.ConfigurationException;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.engine.resource.AnnotationInfo;
import org.restlet.engine.resource.AnnotationUtils;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

public class OuterServerResource extends AbstractServerResource {

	private IServiceLet inner;

	public void init(Context context, Request request, Response response) {
		super.init(context, request, response);

		TreeContext treecontext = (TreeContext) context;
		if (treecontext.contains(OuterServerResource.class.getCanonicalName())){
			this.inner = treecontext.getSelfAttributeObject(OuterServerResource.class.getCanonicalName(), IServiceLet.class) ;
		} else {
			Class<? extends IServiceLet> innerClz = treecontext.getAttributeObject(IServiceLet.class.getCanonicalName(), Class.class);
			this.inner = newInstance(innerClz);
			treecontext.putAttribute(OuterServerResource.class.getCanonicalName(), this.inner) ;
			if (IServiceLetWithInit.class.isAssignableFrom(innerClz)) {
				((IServiceLetWithInit)inner).init(treecontext) ;
			}
		}
	}

	private IServiceLet newInstance(Class<? extends IServiceLet> clz) {
		try {
			List<AnnotationInfo> list = AnnotationUtils.getAnnotations(clz);
			if (list == null || list.size() == 0) ConfigurationException.throwIt(new RuntimeException("exception.let.notsupported"));
			
			Constructor<? extends IServiceLet> con = clz.getDeclaredConstructor();
			con.setAccessible(true);
			return con.newInstance();
		} catch (Throwable ex) {
			throw ConfigurationException.throwIt(ex);
		}
	}

	@Override
	protected Representation doHandle() throws ResourceException {
		Method method = getMethod();
		final AnnotationInfo ainfo = getAnnotation(method, getQuery(), null);
		
		return doHandle(ainfo, null, inner);
	}

	private AnnotationInfo getAnnotation(Method method, Form query, Representation entity) {
		return AnnotationUtils.getAnnotation(AnnotationUtils.getAnnotations(inner.getClass()), method, query, entity, getMetadataService(), getConverterService());
	}

}
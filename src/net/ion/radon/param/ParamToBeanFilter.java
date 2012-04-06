package net.ion.radon.param;

import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.CaseInsensitiveHashMap;
import net.ion.radon.core.IService;
import net.ion.radon.core.EnumClass.IConvertType;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
public class ParamToBeanFilter extends IRadonFilter {

	private String beanName ;
	private String className ; 

	public ParamToBeanFilter(String beanName, String className) {
		this.beanName = beanName  ;
		this.className = className ;
	}

	public final static ParamToBeanFilter create(String name, Class clz){
		return new ParamToBeanFilter(name, clz.getCanonicalName()) ;
	}
	
	@Override
	public IFilterResult preHandle(IService service, Request request, Response response) {
		Map<String, Object> params = getInnerRequest(request).getGeneralParameter() ;
		
		try {
			Object beanObj = MyParameter.create(params).toBean(Class.forName(className)) ;
			getInnerRequest(request).getContext().putAttribute(this.beanName, beanObj) ;
		} catch (ClassNotFoundException e) {
			return IFilterResult.stopResult(new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED, e.getMessage())) ;
		}
		return IFilterResult.CONTINUE_RESULT;
	}

	@Override
	public IFilterResult afterHandle(IService service, Request request, Response response) {
		return IFilterResult.CONTINUE_RESULT ;
	}

}

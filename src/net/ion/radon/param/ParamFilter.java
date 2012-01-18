package net.ion.radon.param;

import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.CaseInsensitiveHashMap;
import net.ion.radon.core.EnumClass.IConvertType;
import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
public class ParamFilter extends IRadonFilter {

	private String beanName ;
	private IConvertType ctype ;
	private String className ; 

	public ParamFilter(String beanName, String ctypeStr, String className) {
		this.beanName = beanName  ;
		this.ctype = IConvertType.valueOf(ctypeStr) ;
		this.className = className ;
	}

	public final static ParamFilter create(String name, String ctype, String className){
		return new ParamFilter(name, ctype, className) ;
	}
	
	private Object convert(Map<String, Object> params) throws ClassNotFoundException{
		if (ctype == IConvertType.MAP) {
			Map<String, Object> result = new CaseInsensitiveHashMap<Object>() ;
			for (Entry<String, Object> param : params.entrySet()) {
				result.put(param.getKey(), param.getValue()) ;
			}
			return result ;
		} else if (ctype == IConvertType.BEAN) {
			Object result = MyParameter.create(params).toBean(Class.forName(className)) ;
			return result ;
		}
		
		throw new IllegalArgumentException("cypte: unknown" + ctype);
	}

	@Override
	public IFilterResult preHandle(IService service, Request request, Response response) {
		Map<String, Object> params = getInnerRequest().getGeneralParameter() ;
		
		try {
			final Object beanObj = convert(params);
			getInnerRequest().getContext().putAttribute(this.beanName, beanObj) ;
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

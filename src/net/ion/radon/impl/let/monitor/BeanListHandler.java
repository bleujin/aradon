package net.ion.radon.impl.let.monitor;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import net.ion.framework.rest.IMapListRepresentationHandler;
import net.ion.framework.rest.IRequest;
import net.ion.framework.rest.IResponse;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.let.MapListRepresentationHandler;

import org.apache.commons.beanutils.PropertyUtils;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

public class BeanListHandler {

	public static <T> Representation toRepresentation(IMapListRepresentationHandler handler, TreeContext context, List<T> datas, String key){
		return toRepresentation(handler, context, IRequest.EMPTY_REQUEST, datas, key, IResponse.EMPTY_RESPONSE);
	}
	
	private static <T> Representation toRepresentation(IMapListRepresentationHandler handler, TreeContext context,
														IRequest request, List<T> datas, String key,  IResponse response){
		try {
			Map<String, Object> result = MapUtil.newOrdereddMap();
			for(T item : datas){
				Map map = beanToMap(item);
				result.put((String)PropertyUtils.getProperty(item, key), map);
			}
			return MapListRepresentationHandler.create(handler, request, ListUtil.create(result), response, context).toRepresentation() ;
		} catch (Exception e) {
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e) ;
		}
	}
	
	private static Map beanToMap(java.lang.Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Map map = MapUtil.newOrdereddMap();
		map = PropertyUtils.describe(bean);
		map.remove("class");
		map.putAll(map);
		return map;
	}
	
}	

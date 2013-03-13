package net.ion.nradon.let;

import java.util.List;
import java.util.Map;

import net.ion.framework.rest.IMapListRepresentationHandler;
import net.ion.framework.rest.IRequest;
import net.ion.framework.rest.IResponse;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.EnumClass.IFormat;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.MapListRepresentationHandler;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

public class ServiceLetUtil {

	public static Representation toRepresentation(TreeContext context, InnerRequest request, List<Map<String, ?>> data) throws ResourceException {
		return toRepresentation(context, request, IRequest.EMPTY_REQUEST, data, IResponse.EMPTY_RESPONSE);
	}
	
	public static Representation toRepresentation(TreeContext context, InnerRequest request, IRequest req, List<Map<String, ?>> datas, IResponse res) throws ResourceException {
		return MapListRepresentationHandler.create(newMapListFormatHandler(request), req, datas, res, context).toRepresentation();
	}
	
	private static IMapListRepresentationHandler newMapListFormatHandler(InnerRequest request) {
		try {
			return newMapListFormatHandler(request.getIFormat());
		} catch (ClassNotFoundException e) {
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e);
		} catch (InstantiationException e) {
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e);
		} catch (IllegalAccessException e) {
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e);
		}
	}

	
	private static IMapListRepresentationHandler newMapListFormatHandler(IFormat format) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class clz = Class.forName("net.ion.framework.rest." + format.toString() + "Formater");
		return (IMapListRepresentationHandler) clz.newInstance();
	}


	public static boolean isExplorer(InnerRequest request) {
		String agent = request.getClientInfo().getAgent();
		if (agent == null) return false ;
		return agent.indexOf("MSIE") > -1;
	}

}

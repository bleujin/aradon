package net.ion.radon.core.let;

import java.util.List;
import java.util.Map;

import net.ion.framework.rest.IMapListRepresentationHandler;
import net.ion.framework.rest.IRequest;
import net.ion.framework.rest.IResponse;
import net.ion.framework.rest.StdObject;
import net.ion.radon.core.TreeContext;

import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

public class MapListRepresentationHandler {

	private IMapListRepresentationHandler handler ;
	private IRequest request ;
	private List<Map<String, ?>> datas ; 
	private IResponse response ;
	private MapListRepresentationHandler(IMapListRepresentationHandler handler, IRequest request, List<Map<String, ?>> datas, IResponse response, TreeContext context) {
		this.handler = handler ;
		this.request = request ;
		this.response = response ;
		this.datas = datas ;
		initContext(context) ;
	}

	public static MapListRepresentationHandler create(IMapListRepresentationHandler handler, IRequest request, List<Map<String, ?>> datas, IResponse response, TreeContext context) {
		return new MapListRepresentationHandler(handler, request, datas, response, context);
	}


	private void initContext(TreeContext context) {
		final StdObject stdObject = StdObject.create(request, datas, response);
		context.putAttribute(StdObject.class.getCanonicalName(), stdObject) ;
	}

	public Representation toRepresentation() throws ResourceException {
		return handler.toRepresentation(request, datas, response);
	}

}

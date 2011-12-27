package net.ion.radon.impl.let;

import org.restlet.Request;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.representation.Variant;
import org.restlet.resource.ServerResource;

public class UserResourceItem extends ServerResource{

	
	private String userName ;
	private Object user ;
	
	public UserResourceItem(){
		super() ;
		
		// getVariants().add(new Variant(MediaType.TEXT_ALL)) ;
		getVariants().add(new Variant(MediaType.ALL)) ;
	}
	public Representation get(Variant variant){
		Request request = getRequest() ;
		String message = "Resource URI  : " + request.getResourceRef() + '\n' + 
		 "UserId        : " + request.getAttributes().get("user") + '\n' + 
		 "Root URI      : " + request.getRootRef() + '\n' + 
		 "Routed part   : " + request.getResourceRef().getBaseRef() + '\n' +
		 "Remaining part: " + request.getResourceRef().getRemainingPart();
		Representation entity = new StringRepresentation(message) ;
		return entity ;
	}

	
}

package net.ion.radon.impl.let;

import net.ion.radon.param.TestBean;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class ObjectPolyLet  extends ServerResource{

	
	public ObjectPolyLet(){
		// Debug.debug(getMetadataService().get) ;
		getMetadataService().setDefaultMediaType(MediaType.APPLICATION_JAVA) ;
	}
	
	@Get("xml")
	public Representation toXML(TestBean tb){
		return new StringRepresentation("<root>Hello</root>") ;
	}
	
	
	@Get("json")
	public Representation toJSON(){
		return new StringRepresentation("{root:'Hello'}") ;
	}

	@Get("java")
	public Representation toObject(){
		return new StringRepresentation("{root:'Hello'}") ;
	}

}
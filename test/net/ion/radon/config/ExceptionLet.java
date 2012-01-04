package net.ion.radon.config;

import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

public class ExceptionLet extends AbstractServerResource {
	
	
	@Get
	public String getName(){
		throw new ResourceException(404) ;
	}

}

package net.ion.radon.config;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;

import net.ion.radon.core.let.AbstractServerResource;

public class ExceptionLet extends AbstractServerResource {
	
	
	@Get
	public String getName(){
		throw new ResourceException(404) ;
	}

}

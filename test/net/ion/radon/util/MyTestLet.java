package net.ion.radon.util;

import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.resource.Get;

public class MyTestLet extends AbstractServerResource {
	
	@Get
	public String hello(){
		return "Hello " + getInnerRequest().getAttribute("name") ;
	}
	
}


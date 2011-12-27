package net.ion.radon.impl.let;

import org.restlet.resource.Get;
import org.restlet.resource.Put;

import net.ion.radon.core.let.AbstractServerResource;

public class HelloWorldResource extends AbstractServerResource{

	
	@Get
	public String hello(){
		return "Hello " + getInnerRequest().getParameter("name") ;
	}
	
	@Put
	public String putContext(String value){
		getInnerRequest().getContext().putAttribute("context.id", value) ;
		return "Hello " + value ;
	}
	
}

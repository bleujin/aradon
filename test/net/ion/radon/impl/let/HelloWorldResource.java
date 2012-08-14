package net.ion.radon.impl.let;

import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.resource.Get;
import org.restlet.resource.Put;

public class HelloWorldResource extends AbstractServerResource{

	
	@Get
	public String hello(){
		return "Hello " + getInnerRequest().getParameter("name") ;
	}
	
	@Put
	public String putContext(String value){
		getInnerRequest().putAttribute("context.id", value) ;
		return "Hello " + value ;
	}
	
}

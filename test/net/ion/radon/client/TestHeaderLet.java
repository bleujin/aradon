package net.ion.radon.client;

import org.restlet.resource.Get;

import net.ion.framework.util.Debug;
import net.ion.radon.core.let.AbstractServerResource;

public class TestHeaderLet extends AbstractServerResource {
	
	@Get
	public String confirmHeader(){
		
		Debug.line(getInnerRequest().getHeaders()) ;
		
		String value = getInnerRequest().getHeader("name") ;
		return "bleujin".equals(value) ? "1" : "0" ; 
	}
}

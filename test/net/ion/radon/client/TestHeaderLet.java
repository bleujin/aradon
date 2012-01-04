package net.ion.radon.client;

import net.ion.framework.util.Debug;
import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.resource.Get;

public class TestHeaderLet extends AbstractServerResource {
	
	@Get
	public String confirmHeader(){
		
		Debug.line(getInnerRequest().getHeaders()) ;
		
		String value = getInnerRequest().getHeader("name") ;
		return "bleujin".equals(value) ? "1" : "0" ; 
	}
}

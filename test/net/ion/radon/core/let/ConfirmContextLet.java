package net.ion.radon.core.let;

import net.ion.framework.util.Debug;
import net.ion.radon.core.EnumClass.IZone;

import org.restlet.resource.Get;

public class ConfirmContextLet extends AbstractServerResource{

	
	@Get("?level=1")
	public String hello(){
		Debug.line(1, getInnerRequest().getParameterValues("level")) ;
		return getContext().getAttributeObject(IZone.class.getCanonicalName(), IZone.class).toString() ; 
	}

	@Get("?level=2")
	public String hello2(){
		Debug.line(2, getInnerRequest().getParameterValues("level")) ;
		return getContext().getAttributeObject(IZone.class.getCanonicalName(), IZone.class).toString() ; 
	}
	

}

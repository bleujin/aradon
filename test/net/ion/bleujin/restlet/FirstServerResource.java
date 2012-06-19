package net.ion.bleujin.restlet;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class FirstServerResource extends ServerResource{

	@Get
	public String viewName(){
		return "hello" ;
	}
}

package net.ion.radon.impl.let;

import net.ion.nradon.let.IServiceLet;
import net.ion.radon.core.annotation.FormParam;

import org.restlet.resource.Get;

public class HiLet implements IServiceLet{

	@Get
	public String sayHello(@FormParam("name") String name){
		return "Hello " + name ;
	}
}

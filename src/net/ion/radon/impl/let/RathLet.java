package net.ion.radon.impl.let;

import net.ion.radon.core.TreeContext;
import net.ion.radon.core.let.AbstractLet;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;

public class RathLet extends Restlet{

	private AbstractLet alet ;
	private RathLet(AbstractLet alet){
		this.alet = alet ;
	}

	public void handle(TreeContext context, Request request, Response response) {
		super.handle(request, response) ;
		
		response.setEntity(alet.handle()) ;
	}
	
}

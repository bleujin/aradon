package net.ion.radon.impl.let;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;


public abstract class ApplicationLet extends Restlet{

	public void handle(Request request, Response response){
		myHandle(request, response) ;
	}
	
	public abstract void myHandle(Request request, Response response) ;
}

package net.ion.radon.client;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;

import net.ion.radon.core.let.AbstractServerResource;

public class RequestLet extends AbstractServerResource{
	
	@Post
	public String getName(Representation rep){
		return getInnerRequest().getParameter("name") ;
	}
}

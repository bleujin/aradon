package net.ion.bleujin.restlet.let;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class  HelloPath extends ServerResource {
	
	@Get
	public String viewName(){
		return HelloPath.class.getCanonicalName() + getRequest().getAttributes().get("name");
	}
	
	@Post
	public String postName(){
		return HelloPath.class.getCanonicalName() + "/POST" ;
	}
}

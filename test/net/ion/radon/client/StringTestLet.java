package net.ion.radon.client;

import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

/**
 * <p>Title: StringTestLet.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: I-ON Communications</p>
 * <p>Date : 2011. 10. 12.</p>
 * @author novision
 * @version 1.0
 */

public class StringTestLet extends AbstractServerResource{
	@Get
	public String hello(){
		return "Hi " + getInnerRequest().getParameter("name") ;
	}
	
	@Post
	public MyUser user(){
		return new MyUser(getInnerRequest().getParameter("name")) ;
	}
}

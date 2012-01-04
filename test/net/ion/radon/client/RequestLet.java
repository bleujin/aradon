package net.ion.radon.client;

import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;

public class RequestLet extends AbstractServerResource {

	@Post
	public String getName(Representation rep) {
		return getInnerRequest().getParameter("name");
	}
}

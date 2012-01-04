package net.ion.radon.core.let;

import org.restlet.resource.Get;

public class ContextLet extends AbstractServerResource {

	@Get
	public String loadGet() {
		return getContext().getAttributeObject("context.id", String.class);
	}

}

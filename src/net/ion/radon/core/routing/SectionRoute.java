package net.ion.radon.core.routing;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.routing.Filter;

public abstract class SectionRoute extends Filter {

	private volatile SectionRouter router;

	public SectionRoute(Restlet next) {
		this(null, next);
	}

	public SectionRoute(SectionRouter router, Restlet next) {
		super((router != null) ? router.getContext() : (next != null) ? next.getContext() : null, next);
		this.router = router;
	}

	public SectionRouter getRouter() {
		return this.router;
	}

	public abstract float score(Request request, Response response);

	public void setRouter(SectionRouter router) {
		this.router = router;
	}

}

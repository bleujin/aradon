package net.ion.radon.core.filter;

import org.restlet.Request;
import org.restlet.Response;

public interface IPathFilter {

	public IFilterResult handle(Request request, Response response) ;

}

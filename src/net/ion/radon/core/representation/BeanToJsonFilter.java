package net.ion.radon.core.representation;

import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.util.Debug;
import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.representation.JsonObjectRepresentation;
import net.ion.radon.core.representation.PlainObjectRepresentation;

import org.restlet.Request;
import org.restlet.Response;

public class BeanToJsonFilter extends IRadonFilter{

	public static IRadonFilter create() {
		return new BeanToJsonFilter();
	}

	@Override
	public IFilterResult afterHandle(IService iservice, Request request, Response response) {
		Object result = ((PlainObjectRepresentation)response.getEntity()).getSourceObject();
		response.setEntity(new JsonObjectRepresentation(JsonParser.fromObject(result))) ;
		return IFilterResult.CONTINUE_RESULT ;
	}

	@Override
	public IFilterResult preHandle(IService iservice, Request request, Response response) {
		return IFilterResult.CONTINUE_RESULT ;
	}

}

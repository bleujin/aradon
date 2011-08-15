package net.ion.radon.impl.let;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.ion.framework.rest.IRequest;
import net.ion.framework.rest.IResponse;
import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.let.DefaultLet;

import org.apache.commons.lang.RandomStringUtils;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.representation.Representation;
import org.restlet.security.User;

public class HelloWorldLet extends DefaultLet {

	@Override
	protected Representation myGet() throws Exception {

		User user = getRequest().getClientInfo().getUser();
		if (user != null)
			Debug.debug(user.getName(), user.getIdentifier(), user.getEmail());

		return toRepresentation(IRequest.EMPTY_REQUEST, makeHelloList(), IResponse.EMPTY_RESPONSE);
	}

	@Override
	protected Representation myPut(Representation entity) throws Exception {

		Representation re = getRequest().getEntity();
		final List<Map<String, ?>> data = ListUtil.newList();
		if (re != null) {
			String str = IOUtil.toString(re.getStream());
			Map<String, Object> rmap = MapUtil.create("req", (Object) StringUtil.defaultIfEmpty(str, "EMPTY"));
			data.add(rmap);
		}
		

		return toRepresentation(IRequest.EMPTY_REQUEST, data, IResponse.EMPTY_RESPONSE);
	}

	private List<Map<String, ?>> makeHelloList() {
		
		Map<String, Object> map = MapUtil.create("greeting", (Object) ("MY... Hello World Aradon !!" + getRequest().getMethod() + " " + RandomStringUtils.randomAlphabetic(5) + "," + getInnerRequest().getParameter("abcd")));
		return ListUtil.create(map);
	}

	@Override
	protected Representation myPost(Representation entity) throws Exception {
		String message = getContext().getAttributeObject("message", String.class);
		
		
		

		return toRepresentation(IRequest.EMPTY_REQUEST, makeHelloList(), IResponse.EMPTY_RESPONSE);
		// return new StringRepresentation("Hello World Aradon !!<br/>" + message + "<br/> "+ getOverideMethod() + " " + RandomStringUtils.randomAlphabetic(5) + "," + getParameterAsString("abcd"));
	}

}

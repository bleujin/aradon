package net.ion.radon.core.let;

import java.util.Map;

import net.ion.framework.rest.StdObject;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Reference;

public class InboundLet {

	private SectionService section;
	private String path;
	private InnerRequest caller;
	private TreeContext reqContext;

	//private static Form EMPTY_MAP = new Form() ;
	static {
		// EMPTY_MAP.put("aradon.result.format", "json") ;
	}

	private InboundLet(SectionService section, String path, InnerRequest caller, TreeContext reqContext) {
		this.section = section;
		this.caller = caller;
		this.path = path;
		this.reqContext = reqContext;
	}


	public static InboundLet create(InnerRequest caller, SectionService targetSection, String pathURL) {
		// final Reference target = new Reference("/" + targetSection.getName() + pathURL);
		String pname = targetSection.getPathName(new Reference(pathURL));
		return new InboundLet(targetSection, pathURL, caller, targetSection.getChildService(pname).createChildContext());
	}
	
	public LetResponse post() {
		return post(new Form());
	}

	public LetResponse get() {
		return get(new Form());
	}

	public LetResponse put() {
		return put(new Form());
	}

	public LetResponse delete() {
		return delete(new Form());
	}

	private InnerRequest createRequest(InnerRequest caller, Method method, String path, Form form) {
		final InnerRequest newRequest = InnerRequest.create(section.getName(), new Request(method,  path));
		newRequest.setCaller(caller);

		newRequest.setAttributes(caller.getAttributes());
		// newRequest.setEntity(form.getWebRepresentation()) ;
		newRequest.getAttributes().put(RadonAttributeKey.REQUEST_CONTEXT, this.reqContext);
		newRequest.getAttributes().remove(RadonAttributeKey.FORM_ATTRIBUTE_KEY);
		newRequest.getFormParameter().putAll(form.getValuesMap());

		return newRequest;
	}

	public LetResponse post(Form params) {
		return sendInboundRequest(Method.POST, params);
	}

	public LetResponse get(Form params) {
		return sendInboundRequest(Method.GET, params);
	}

	public LetResponse put(Form params) {
		return sendInboundRequest(Method.PUT, params);
	}

	public LetResponse delete(Form params) {
		return sendInboundRequest(Method.DELETE, params);
	}

	public LetResponse handle(Method method, Form params) {

		if (Method.GET == method) {
			return get(params);
		} else if (Method.POST == method) {
			return post(params);
		} else if (Method.DELETE == method) {
			return delete(params);
		} else if (Method.PUT == method) {
			return put(params);
		} else {
			throw new IllegalArgumentException(method + " not supported method");
		}
	}

	private LetResponse sendInboundRequest(Method method, Form form) {
		
		InnerRequest newRequest = createRequest(caller, method, path, form) ;
		final InnerResponse newResponse = InnerResponse.create(new Response(newRequest), newRequest);
		
		section.getInboundRoot().handle(newRequest, newResponse) ;
		
		InnerRequest current = newRequest;

		Map map = current.getContext().getAttributes();
		for (Object obj : map.values()) {
			if (obj instanceof StdObject) {
				newResponse.setResponseData((StdObject) obj);
				// current.getCaller().getContext().putAttribute(current.getCaller().getResourceRef().getPath(), obj);
				break;
			}
		}

		return LetResponse.create(newRequest, newResponse) ;
	}


	public String getPath() {
		return this.path;
	}

	public TreeContext getContext() {
		return this.reqContext;
	}

}

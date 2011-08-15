package org.restlet.engine;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Protocol;
import org.restlet.routing.Template;

public class TemplateDispatcher extends Client {

	public TemplateDispatcher(Context context) {
		super(null, (Protocol) null);
		this.context = context;
	}

	protected void doHandle(Request request, Response response) {
		request.setOriginalRef(request.getResourceRef().getTargetRef());
	}

	public Context getContext() {
		return context;
	}

	public void handle(Request request, Response response) {
		Response.setCurrent(response);
		Protocol protocol = request.getProtocol();
		if (protocol == null)
			throw new UnsupportedOperationException("Unable to determine the protocol to use for this call.");
		String targetUri = request.getResourceRef().toString(true, false);
		if (targetUri.contains("{")) {
			Template template = new Template(targetUri);
			request.setResourceRef(template.format(request, response));
		}
		doHandle(request, response);
		if (response.getEntity() != null && response.getEntity().getLocationRef() == null)
			response.getEntity().setLocationRef(request.getResourceRef().getTargetRef().toString());
	}

	public void setContext(Context context) {
		this.context = context;
	}

	private volatile Context context;
}

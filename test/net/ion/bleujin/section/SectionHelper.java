package net.ion.bleujin.section;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.routing.Filter;
import org.restlet.service.Service;

// [excludes gwt]
public class SectionHelper extends ChainLetHelper<BaseSection> {
	/**
	 * Constructor.
	 * 
	 * @param application
	 *            The application to help.
	 */
	public SectionHelper(BaseSection application) {
		super(application);
	}

	/**
	 * In addition to the default behavior, it saves the current application instance into the current thread.
	 * 
	 * @param request
	 *            The request to handle.
	 * @param response
	 *            The response to update.
	 */
	@Override
	public void handle(Request request, Response response) {
		// Save the current application
		BaseSection.setCurrent(getHelped());

		// Actually handle call
		super.handle(request, response);
	}

	/** Start hook. */
	@Override
	public synchronized void start() throws Exception {
		// Attach the service inbound filters
		Filter inboundFilter = null;

		for (Service service : getHelped().getServices()) {
			if (service.isEnabled()) {
				inboundFilter = service.createInboundFilter(getContext());

				if (inboundFilter != null) {
					addFilter(inboundFilter);
				}
			}
		}

		// Attach the Application's server root Restlet
		setNext(getHelped().getInboundRoot());
	}

	@Override
	public synchronized void stop() throws Exception {
		clear();
	}

	@Override
	public void update() throws Exception {
	}

}

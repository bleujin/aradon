package net.ion.radon.core.routing;

import java.util.logging.Level;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.routing.Template;
import org.restlet.routing.Variable;

public class SectionTemplateRoute extends SectionRoute {

	private volatile boolean matchingQuery;

	private volatile Template template;
	public SectionTemplateRoute(Restlet next) {
		this(null, (Template) null, next);
	}

	public SectionTemplateRoute(SectionRouter router, String uriTemplate, Restlet next) {
		this(router, new Template(uriTemplate, Template.MODE_STARTS_WITH, Variable.TYPE_URI_SEGMENT, "", true, false), next);
	}

	public SectionTemplateRoute(SectionRouter router, Template template, Restlet next) {
		super(router, next);
		this.matchingQuery = (router == null) ? true : router.getDefaultMatchingQuery();
		this.template = template;
	}

	@Override
	protected int beforeHandle(Request request, Response response) {
		// 1 - Parse the template variables and adjust the base reference
		if (getTemplate() != null) {
			String remainingPart = request.getResourceRef().getRemainingPart(false, isMatchingQuery());
			int matchedLength = getTemplate().parse(remainingPart, request);

			if (matchedLength == 0) {
				if (request.isLoggable() && getLogger().isLoggable(Level.FINER)) {
					getLogger().finer("No characters were matched");
				}
			} else if (matchedLength > 0) {
				if (request.isLoggable() && getLogger().isLoggable(Level.FINER)) {
					getLogger().finer("" + matchedLength + " characters were matched");
				}

				// Updates the context
				String matchedPart = remainingPart.substring(0, matchedLength);
				Reference baseRef = request.getResourceRef().getBaseRef();

				if (baseRef == null) {
					baseRef = new Reference(matchedPart);
				} else {
					baseRef = new Reference(baseRef.toString(false, false) + matchedPart);
				}

				request.getResourceRef().setBaseRef(baseRef);

				if (request.isLoggable()) {
					if (getLogger().isLoggable(Level.FINE)) {
						remainingPart = request.getResourceRef().getRemainingPart(false, isMatchingQuery());

						if ((remainingPart != null) && (!"".equals(remainingPart))) {
							getLogger().fine("New base URI: \"" + request.getResourceRef().getBaseRef() + "\". New remaining part: \"" + remainingPart + "\"");
						} else {
							getLogger().fine("New base URI: \"" + request.getResourceRef().getBaseRef() + "\". No remaining part to match");
						}
					}

					if (getLogger().isLoggable(Level.FINER)) {
						getLogger().finer("Delegating the call to the target Restlet");
					}
				}
			} else {
				if (request.isLoggable() && getLogger().isLoggable(Level.FINE)) {
					getLogger().fine("Unable to match this pattern: " + getTemplate().getPattern());
				}

				response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			}
		}

		return CONTINUE;
	}

	public int getMatchingMode() {
		return getTemplate().getMatchingMode();
	}

	public Template getTemplate() {
		return this.template;
	}

	public boolean isMatchingQuery() {
		return this.matchingQuery;
	}

	public float score(Request request, Response response) {
		float result = 0F;

		if ((getRouter() != null) && (request.getResourceRef() != null) && (getTemplate() != null)) {
			final String remainingPart = request.getResourceRef().getRemainingPart(false, isMatchingQuery());
			if (remainingPart != null) {
				final int matchedLength = getTemplate().match(remainingPart);

				if (matchedLength != -1) {
					final float totalLength = remainingPart.length();

					if (totalLength > 0.0F) {
						result = getRouter().getRequiredScore() + (1.0F - getRouter().getRequiredScore()) * (matchedLength / totalLength);
					} else {
						result = 1.0F;
					}
				}
			}

			if (request.isLoggable() && getLogger().isLoggable(Level.FINER)) {
				getLogger().finer("Call score for the \"" + getTemplate().getPattern() + "\" URI pattern: " + result);
			}
		}

		return result;
	}

	public void setMatchingMode(int matchingMode) {
		getTemplate().setMatchingMode(matchingMode);
	}

	public void setMatchingQuery(boolean matchingQuery) {
		this.matchingQuery = matchingQuery;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	@Override
	public String toString() {
		return "\"" + ((getTemplate() == null) ? super.toString() : getTemplate().getPattern()) + "\" -> "
				+ ((getNext() == null) ? "null" : getNext().toString());
	}
}

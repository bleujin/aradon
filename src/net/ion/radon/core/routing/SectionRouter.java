package net.ion.radon.core.routing;

import java.util.logging.Level;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Status;
import org.restlet.resource.Directory;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;
import org.restlet.routing.Filter;
import org.restlet.routing.Template;

public class SectionRouter extends Restlet {

	public static final int MODE_BEST_MATCH = 1;
	public static final int MODE_CUSTOM = 6;
	public static final int MODE_FIRST_MATCH = 2;
	public static final int MODE_LAST_MATCH = 3;
	public static final int MODE_NEXT_MATCH = 4;
	public static final int MODE_RANDOM_MATCH = 5;

	private volatile int defaultMatchingMode;
	private volatile boolean defaultMatchingQuery;
	private volatile SectionRoute defaultRoute;
	private volatile int maxAttempts;
	private volatile float requiredScore;
	private volatile long retryDelay;
	private volatile SectionRouteList routes;
	private volatile int routingMode;

	public SectionRouter() {
		this(null);
	}

	public SectionRouter(Context context) {
		super(context);
		this.routes = new SectionRouteList();
		this.defaultMatchingMode = Template.MODE_EQUALS;
		this.defaultMatchingQuery = false;
		this.defaultRoute = null;
		this.routingMode = MODE_FIRST_MATCH;
		this.requiredScore = 0.5F;
		this.maxAttempts = 1;
		this.retryDelay = 500L;
	}

	public SectionTemplateRoute attach(Restlet target) {
		return attach(target, getMatchingMode(target));
	}

	public SectionTemplateRoute attach(Restlet target, int matchingMode) {
		return attach("", target, matchingMode);
	}

	public SectionTemplateRoute attach(String pathTemplate, Class<? extends ServerResource> targetClass) {
		return attach(pathTemplate, createFinder(targetClass));
	}

	public SectionTemplateRoute attach(String pathTemplate, Class<? extends ServerResource> targetClass, int matchingMode) {
		return attach(pathTemplate, createFinder(targetClass), matchingMode);
	}

	public SectionTemplateRoute attach(String pathTemplate, Restlet target) {
		return attach(pathTemplate, target, getMatchingMode(target));
	}

	public SectionTemplateRoute attach(String pathTemplate, Restlet target, int matchingMode) {
		SectionTemplateRoute result = createRoute(pathTemplate, target, matchingMode);
		getRoutes().add(result);
		return result;
	}

	public SectionTemplateRoute attachDefault(Class<? extends ServerResource> defaultTargetClass) {
		return attachDefault(createFinder(defaultTargetClass));
	}

	public SectionTemplateRoute attachDefault(Restlet defaultTarget) {
		SectionTemplateRoute result = createRoute("", defaultTarget);
		result.setMatchingMode(Template.MODE_STARTS_WITH);
		setDefaultRoute(result);
		return result;
	}

	protected SectionTemplateRoute createRoute(String uriPattern, Restlet target) {
		return createRoute(uriPattern, target, getMatchingMode(target));
	}

	protected SectionTemplateRoute createRoute(String uriPattern, Restlet target, int matchingMode) {
		SectionTemplateRoute result = new SectionTemplateRoute(this, uriPattern, target);
		result.getTemplate().setMatchingMode(matchingMode);
		result.setMatchingQuery(getDefaultMatchingQuery());
		return result;
	}

	public void detach(Class<?> targetClass) {
		for (int i = getRoutes().size() - 1; i >= 0; i--) {
			Restlet target = getRoutes().get(i).getNext();

			if (target != null && Finder.class.isAssignableFrom(target.getClass())) {
				Finder finder = (Finder) target;

				if (finder.getTargetClass().equals(targetClass)) {
					getRoutes().remove(i);
				}
			}
		}

		if (getDefaultRoute() != null) {
			Restlet target = getDefaultRoute().getNext();

			if (target != null && Finder.class.isAssignableFrom(target.getClass())) {
				Finder finder = (Finder) target;

				if (finder.getTargetClass().equals(targetClass)) {
					setDefaultRoute(null);
				}
			}
		}
	}

	public void detach(Restlet target) {
		getRoutes().removeAll(target);
		if ((getDefaultRoute() != null) && (getDefaultRoute().getNext() == target)) {
			setDefaultRoute(null);
		}
	}

	protected void doHandle(Restlet next, Request request, Response response) {
		next.handle(request, response);
	}

	protected SectionRoute getCustom(Request request, Response response) {
		return null;
	}

	public int getDefaultMatchingMode() {
		return this.defaultMatchingMode;
	}

	public boolean getDefaultMatchingQuery() {
		return this.defaultMatchingQuery;
	}

	public SectionRoute getDefaultRoute() {
		return this.defaultRoute;
	}

	protected int getMatchingMode(Restlet target) {
		int result = getDefaultMatchingMode();

		if ((target instanceof Directory) || (target instanceof SectionRouter)) {
			result = Template.MODE_STARTS_WITH;
		} else if (target instanceof Filter) {
			result = getMatchingMode(((Filter) target).getNext());
		}

		return result;
	}

	public int getMaxAttempts() {
		return this.maxAttempts;
	}

	public Restlet getNext(Request request, Response response) {
		SectionRoute result = null;

		for (int i = 0; (result == null) && (i < getMaxAttempts()); i++) {
			if (i > 0) {
				// Before attempting another time, let's
				// sleep during the "retryDelay" set.
				try {
					Thread.sleep(getRetryDelay());
				} catch (InterruptedException e) {
				}
			}

			if (this.routes != null) {
				// Select the routing mode
				switch (getRoutingMode()) {
				case MODE_BEST_MATCH:
					result = getRoutes().getBest(request, response, getRequiredScore());
					break;

				case MODE_FIRST_MATCH:
					result = getRoutes().getFirst(request, response, getRequiredScore());
					break;

				case MODE_LAST_MATCH:
					result = getRoutes().getLast(request, response, getRequiredScore());
					break;

				case MODE_NEXT_MATCH:
					result = getRoutes().getNext(request, response, getRequiredScore());
					break;

				case MODE_RANDOM_MATCH:
					result = getRoutes().getRandom(request, response, getRequiredScore());
					break;

				case MODE_CUSTOM:
					result = getCustom(request, response);
					break;
				}
			}
		}

		if (result == null) {
			// If nothing matched in the routes list,
			// check the default route
			if ((getDefaultRoute() != null) && (getDefaultRoute().score(request, response) >= getRequiredScore())) {
				result = getDefaultRoute();
			} else {
				// No route could be found
				response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			}
		}

		if (request.isLoggable()) {
			logRoute(result);
		}

		return result;
	}

	public float getRequiredScore() {
		return this.requiredScore;
	}

	public long getRetryDelay() {
		return this.retryDelay;
	}

	public SectionRouteList getRoutes() {
		return this.routes;
	}

	public int getRoutingMode() {
		return this.routingMode;
	}

	@Override
	public void handle(Request request, Response response) {
		super.handle(request, response);
		Restlet next = getNext(request, response);

		if (next != null) {
			doHandle(next, request, response);
		} else {
			response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		}
	}

	protected void logRoute(SectionRoute route) {
		if (getLogger().isLoggable(Level.FINE)) {
			if (getDefaultRoute() == route) {
				getLogger().fine("The default route was selected");
			} else {
				getLogger().fine("Selected route: " + route);
			}
		}
	}

	public void setDefaultMatchingMode(int defaultMatchingMode) {
		this.defaultMatchingMode = defaultMatchingMode;
	}

	public void setDefaultMatchingQuery(boolean defaultMatchingQuery) {
		this.defaultMatchingQuery = defaultMatchingQuery;
	}

	public void setDefaultRoute(SectionRoute defaultRoute) {
		this.defaultRoute = defaultRoute;
	}

	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	public void setRequiredScore(float score) {
		this.requiredScore = score;
	}

	public void setRetryDelay(long retryDelay) {
		this.retryDelay = retryDelay;
	}

	public void setRoutes(SectionRouteList routes) {
		this.routes = routes;
	}

	public void setRoutingMode(int routingMode) {
		this.routingMode = routingMode;
	}

	@Override
	public synchronized void start() throws Exception {
		if (isStopped()) {
			super.start();

			for (SectionRoute route : getRoutes()) {
				route.start();
			}

			if (getDefaultRoute() != null) {
				getDefaultRoute().start();
			}
		}
	}

	@Override
	public synchronized void stop() throws Exception {
		if (isStarted()) {
			if (getDefaultRoute() != null) {
				getDefaultRoute().stop();
			}

			for (SectionRoute route : getRoutes()) {
				route.stop();
			}

			super.stop();
		}
	}

}

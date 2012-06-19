package net.ion.bleujin.restlet;

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
import org.restlet.routing.Route;
import org.restlet.routing.Router;
import org.restlet.routing.Template;
import org.restlet.routing.TemplateRoute;
import org.restlet.util.RouteList;

public class AradonRouter extends Router {

	public static final int MODE_BEST_MATCH = 1;
	public static final int MODE_CUSTOM = 6;
	public static final int MODE_FIRST_MATCH = 2;
	public static final int MODE_LAST_MATCH = 3;
	public static final int MODE_NEXT_MATCH = 4;
	public static final int MODE_RANDOM_MATCH = 5;

	private volatile int defaultMatchingMode;

	private volatile boolean defaultMatchingQuery;
	private volatile Route defaultRoute;
	private volatile int maxAttempts;

	private volatile float requiredScore;
	private volatile long retryDelay;
	private volatile RouteList routes;
	private volatile int routingMode;

	public AradonRouter(Context context) {
		super(context);
		this.routes = new RouteList();
		this.defaultMatchingMode = Template.MODE_EQUALS;
		this.defaultMatchingQuery = false;
		this.defaultRoute = null;
		this.routingMode = MODE_FIRST_MATCH;
		this.requiredScore = 0.5F;
		this.maxAttempts = 1;
		this.retryDelay = 500L;
	}


	public TemplateRoute attach(Restlet target) {
		return attach(target, getMatchingMode(target));
	}


	public TemplateRoute attach(Restlet target, int matchingMode) {
		return attach("", target, matchingMode);
	}

	public TemplateRoute attach(String pathTemplate, Class<? extends ServerResource> targetClass) {
		return attach(pathTemplate, createFinder(targetClass));
	}

	public TemplateRoute attach(String pathTemplate, Class<? extends ServerResource> targetClass, int matchingMode) {
		return attach(pathTemplate, createFinder(targetClass), matchingMode);
	}

	public TemplateRoute attach(String pathTemplate, Restlet target) {
		return attach(pathTemplate, target, getMatchingMode(target));
	}

	public TemplateRoute attach(String pathTemplate, Restlet target, int matchingMode) {
		TemplateRoute result = createRoute(pathTemplate, target, matchingMode);
		getRoutes().add(result);
		return result;
	}

	public TemplateRoute attachDefault(Class<? extends ServerResource> defaultTargetClass) {
		return attachDefault(createFinder(defaultTargetClass));
	}

	public TemplateRoute attachDefault(Restlet defaultTarget) {
		TemplateRoute result = createRoute("", defaultTarget);
		result.setMatchingMode(Template.MODE_STARTS_WITH);
		setDefaultRoute(result);
		return result;
	}

	protected TemplateRoute createRoute(String uriPattern, Restlet target) {
		return createRoute(uriPattern, target, getMatchingMode(target));
	}

	protected TemplateRoute createRoute(String uriPattern, Restlet target, int matchingMode) {
		TemplateRoute result = new TemplateRoute(this, uriPattern, target);
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

	protected Route getCustom(Request request, Response response) {
		return null;
	}

	public int getDefaultMatchingMode() {
		return this.defaultMatchingMode;
	}

	public boolean getDefaultMatchingQuery() {
		return this.defaultMatchingQuery;
	}

	public Route getDefaultRoute() {
		return this.defaultRoute;
	}

	protected int getMatchingMode(Restlet target) {
		int result = getDefaultMatchingMode();

		if ((target instanceof Directory) || (target instanceof Router)) {
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
		Route result = null;

		for (int i = 0; (result == null) && (i < getMaxAttempts()); i++) {
			if (i > 0) {
				// Before attempting another time, let's sleep during the "retryDelay" set.
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

	// Returns the minimum score required to have a match. By default, it returns {@code 0.5}.
	public float getRequiredScore() {
		return this.requiredScore;
	}

	// Returns the delay in milliseconds before a new attempt is made. The default value is {@code 500}.
	public long getRetryDelay() {
		return this.retryDelay;
	}

	// Returns the modifiable list of routes. Creates a new instance if no one has been set.
	public RouteList getRoutes() {
		return this.routes;
	}

	// Returns the routing mode. By default, it returns the {@link #MODE_FIRST_MATCH} mode.
	public int getRoutingMode() {
		return this.routingMode;
	}

	// Handles a call by invoking the next Restlet if it is available.
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

	// Logs the route selected.
	protected void logRoute(Route route) {
		if (getLogger().isLoggable(Level.FINE)) {
			if (getDefaultRoute() == route) {
				getLogger().fine("The default route was selected");
			} else {
				getLogger().fine("Selected route: " + route);
			}
		}
	}

	// Sets the default matching mode to use when selecting routes based on URIs. By default it is set to {@link Template#MODE_EQUALS}.
	public void setDefaultMatchingMode(int defaultMatchingMode) {
		this.defaultMatchingMode = defaultMatchingMode;
	}

	// Sets the default setting for whether the routing should be done on URIs with or without taking into account query string. By default, it is set to false.
	public void setDefaultMatchingQuery(boolean defaultMatchingQuery) {
		this.defaultMatchingQuery = defaultMatchingQuery;
	}

	// Sets the default route tested if no other one was available.
	public void setDefaultRoute(Route defaultRoute) {
		this.defaultRoute = defaultRoute;
	}

	// Sets the maximum number of attempts if no attachment could be matched on the first attempt. This is useful when the attachment scoring is dynamic and therefore could change on a retry.
	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	// Sets the score required to have a match. By default, it is set to {@code 0.5}.
	public void setRequiredScore(float score) {
		this.requiredScore = score;
	}

	// Sets the delay in milliseconds before a new attempt is made. By default, it is set to {@code 500}.
	public void setRetryDelay(long retryDelay) {
		this.retryDelay = retryDelay;
	}

	// Sets the modifiable list of routes.
	public void setRoutes(RouteList routes) {
		this.routes = routes;
	}

	// Sets the routing mode. By default, it is set to the {@link #MODE_FIRST_MATCH} mode.
	public void setRoutingMode(int routingMode) {
		this.routingMode = routingMode;
	}

	// Starts the filter and the attached routes.
	@Override
	public synchronized void start() throws Exception {
		if (isStopped()) {
			super.start();

			for (Route route : getRoutes()) {
				route.start();
			}

			if (getDefaultRoute() != null) {
				getDefaultRoute().start();
			}
		}
	}

	// Stops the filter and the attached routes.
	@Override
	public synchronized void stop() throws Exception {
		if (isStarted()) {
			if (getDefaultRoute() != null) {
				getDefaultRoute().stop();
			}

			for (Route route : getRoutes()) {
				route.stop();
			}

			super.stop();
		}
	}

}

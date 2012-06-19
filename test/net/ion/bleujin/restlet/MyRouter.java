package net.ion.bleujin.restlet;

import java.util.Iterator;
import java.util.logging.Level;

import net.ion.framework.util.Debug;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Status;
import org.restlet.resource.Directory;
import org.restlet.resource.Finder;
import org.restlet.routing.Filter;
import org.restlet.routing.Route;
import org.restlet.routing.Router;
import org.restlet.routing.TemplateRoute;
import org.restlet.util.RouteList;

// Referenced classes of package org.restlet.routing:
//            TemplateRoute, Route, Filter, Template

public class MyRouter extends Router {

	public MyRouter() {
		this(null);
	}

	public MyRouter(Context context) {
		super(context);
		routes = new RouteList();
		defaultMatchingMode = 2;
		defaultMatchingQuery = false;
		defaultRoute = null;
		routingMode = 2;
		requiredScore = 0.5F;
		maxAttempts = 1;
		retryDelay = 500L;
	}

	public TemplateRoute attach(Restlet target) {
		return attach(target, getMatchingMode(target));
	}

	public TemplateRoute attach(Restlet target, int matchingMode) {
		return attach("", target, matchingMode);
	}

	public TemplateRoute attach(String pathTemplate, Class targetClass) {
		return attach(pathTemplate, ((Restlet) (createFinder(targetClass))));
	}

	public TemplateRoute attach(String pathTemplate, Class targetClass, int matchingMode) {
		return attach(pathTemplate, ((Restlet) (createFinder(targetClass))), matchingMode);
	}

	public TemplateRoute attach(String pathTemplate, Restlet target) {
		return attach(pathTemplate, target, getMatchingMode(target));
	}

	public TemplateRoute attach(String pathTemplate, Restlet target, int matchingMode) {
		TemplateRoute result = createRoute(pathTemplate, target, matchingMode);
		getRoutes().add(result);
		return result;
	}

	public TemplateRoute attachDefault(Class defaultTargetClass) {
		return attachDefault(((Restlet) (createFinder(defaultTargetClass))));
	}

	public TemplateRoute attachDefault(Restlet defaultTarget) {
		TemplateRoute result = createRoute("", defaultTarget);
		result.setMatchingMode(1);
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

	public void detach(Class targetClass) {
		for (int i = getRoutes().size() - 1; i >= 0; i--) {
			Restlet target = ((Route) getRoutes().get(i)).getNext();
			if (target == null || !Finder.class.isAssignableFrom(target.getClass()))
				continue;
			Finder finder = (Finder) target;
			if (finder.getTargetClass().equals(targetClass))
				getRoutes().remove(i);
		}

		if (getDefaultRoute() != null) {
			Restlet target = getDefaultRoute().getNext();
			if (target != null && Finder.class.isAssignableFrom(target.getClass())) {
				Finder finder = (Finder) target;
				if (finder.getTargetClass().equals(targetClass))
					setDefaultRoute(null);
			}
		}
	}

	public void detach(Restlet target) {
		getRoutes().removeAll(target);
		if (getDefaultRoute() != null && getDefaultRoute().getNext() == target)
			setDefaultRoute(null);
	}

	protected void doHandle(Restlet next, Request request, Response response) {
		next.handle(request, response);
	}

	protected Route getCustom(Request request, Response response) {
		return null;
	}

	public int getDefaultMatchingMode() {
		return defaultMatchingMode;
	}

	public boolean getDefaultMatchingQuery() {
		return defaultMatchingQuery;
	}

	public Route getDefaultRoute() {
		return defaultRoute;
	}

	protected int getMatchingMode(Restlet target) {
		int result = getDefaultMatchingMode();
		if ((target instanceof Directory) || (target instanceof Router))
			result = 1;
		else if (target instanceof Filter)
			result = getMatchingMode(((Filter) target).getNext());
		return result;
	}

	public int getMaxAttempts() {
		return maxAttempts;
	}

	public Restlet getNext(Request request, Response response) {
		Route result = null;
		for (int i = 0; result == null && i < getMaxAttempts(); i++) {
			if (i > 0)
				try {
					Thread.sleep(getRetryDelay());
				} catch (InterruptedException e) {
				}
			if (routes != null)
				switch (getRoutingMode()) {
				case 1: // '\001'
					result = getRoutes().getBest(request, response, getRequiredScore());
					break;

				case 2: // '\002'
					result = getFirst(getRoutes(), request, response, getRequiredScore());
					break;

				case 3: // '\003'
					result = getRoutes().getLast(request, response, getRequiredScore());
					break;

				case 4: // '\004'
					result = getRoutes().getNext(request, response, getRequiredScore());
					break;

				case 5: // '\005'
					result = getRoutes().getRandom(request, response, getRequiredScore());
					break;

				case 6: // '\006'
					result = getCustom(request, response);
					break;
				}
		}

		if (result == null)
			if (getDefaultRoute() != null && getDefaultRoute().score(request, response) >= getRequiredScore())
				result = getDefaultRoute();
			else
				response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		if (request.isLoggable())
			logRoute(result);
		return result;
	}

	public Route getFirst(RouteList rlist, Request request, Response response, float requiredScore) {
		for (Iterator i$ = rlist.iterator(); i$.hasNext();) {
			Route current = (Route) i$.next();
			Debug.line(current, current.getClass(), current.score(request, response)) ;
			if (current.score(request, response) >= requiredScore)
				return current;
		}

		return null;
	}

	public float getRequiredScore() {
		return requiredScore;
	}

	public long getRetryDelay() {
		return retryDelay;
	}

	public RouteList getRoutes() {
		return routes;
	}

	public int getRoutingMode() {
		return routingMode;
	}

	public void handle(Request request, Response response) {
		super.handle(request, response);
		Restlet next = getNext(request, response);
		if (next != null)
			doHandle(next, request, response);
		else
			response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
	}

	protected void logRoute(Route route) {
		if (getLogger().isLoggable(Level.FINE))
			if (getDefaultRoute() == route)
				getLogger().fine("The default route was selected");
			else
				getLogger().fine((new StringBuilder()).append("Selected route: ").append(route).toString());
	}

	public void setDefaultMatchingMode(int defaultMatchingMode) {
		this.defaultMatchingMode = defaultMatchingMode;
	}

	public void setDefaultMatchingQuery(boolean defaultMatchingQuery) {
		this.defaultMatchingQuery = defaultMatchingQuery;
	}

	public void setDefaultRoute(Route defaultRoute) {
		this.defaultRoute = defaultRoute;
	}

	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	public void setRequiredScore(float score) {
		requiredScore = score;
	}

	public void setRetryDelay(long retryDelay) {
		this.retryDelay = retryDelay;
	}

	public void setRoutes(RouteList routes) {
		this.routes = routes;
	}

	public void setRoutingMode(int routingMode) {
		this.routingMode = routingMode;
	}

	public synchronized void start() throws Exception {
		if (isStopped()) {
			super.start();
			Route route;
			for (Iterator i$ = getRoutes().iterator(); i$.hasNext(); route.start())
				route = (Route) i$.next();

			if (getDefaultRoute() != null)
				getDefaultRoute().start();
		}
	}

	public synchronized void stop() throws Exception {
		if (isStarted()) {
			if (getDefaultRoute() != null)
				getDefaultRoute().stop();
			Route route;
			for (Iterator i$ = getRoutes().iterator(); i$.hasNext(); route.stop())
				route = (Route) i$.next();

			super.stop();
		}
	}

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
}

/*
 * DECOMPILATION REPORT
 * 
 * Decompiled from: C:\Users\bleujin\workspace\ARadon\aradon_lib\aradon\embed\rest_fat.jar Total time: 78 ms Jad reported messages/errors: Exit status: 0 Caught exceptions:
 */
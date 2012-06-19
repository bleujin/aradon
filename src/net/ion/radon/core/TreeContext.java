package net.ion.radon.core;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.radon.core.EnumClass.FilterLocation;
import net.ion.radon.core.EnumClass.IZone;
import net.ion.radon.core.config.ReferencedObject;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.context.IParentContext;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.impl.filter.RevokeServiceFilter;

import org.apache.commons.configuration.ConfigurationException;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.routing.VirtualHost;
import org.restlet.security.Enroler;
import org.restlet.security.Verifier;
import org.restlet.util.Series;

public final class TreeContext extends Context {

	private Context context;

	private TreeContext(Context context) {
		this.context = context;
	}

	public final static TreeContext createRootContext(VirtualHost vhost) {
		return new TreeContext(vhost.getContext());
	}

	public TreeContext createChildContext() {
		TreeContext newChild = new TreeContext(context.createChildContext());
		newChild.putAttribute(IParentContext.class.getCanonicalName(), this);
		IZone zone = getSelfAttributeObject(IZone.class.getCanonicalName(), IZone.class);
		if (zone == null) {
			putAttribute(IZone.class.getCanonicalName(), IZone.Application);
			newChild.putAttribute(IZone.class.getCanonicalName(), IZone.Section);
		} else {
			newChild.putAttribute(IZone.class.getCanonicalName(), zone.getChildZone());
		}

		return newChild;
	}

	public IZone getZone() {
		return getSelfAttributeObject(IZone.class.getCanonicalName(), IZone.class);
	}

	public TreeContext getParentContext() {
		return getSelfAttributeObject(IParentContext.class.getCanonicalName(), TreeContext.class);
	}

	public Object getAttributeObject(String key) {
		return getAttributeObject(key, Object.class);
	}

	public <T> T getAttributeObject(String key, Class<T> T) {
		return getAttributeObject(key, null, T);
	}

	public <T> T getAttributeObject(String key, T defaultValue, Class<T> T) {
		TreeContext current = this;
		while (current != null) {
			Object value = current.getSelfAttributeObject(key, Object.class);
			if (value != null && T.isInstance(value)) {
				return (T) value;
			}
			current = current.getParentContext();
		}
		return defaultValue;
	}

	public <T> T getSelfAttributeObject(String key, Class<T> T) {
		return getSelfAttributeObject(key, T, null);
	}

	public <T> T getSelfAttributeObject(String key, Class<T> T, T defaultValue) {
		try {
			Object value = context.getAttributes().get(key);
			if (value != null && value instanceof ReferencedObject) {
				return (T) ((ReferencedObject) value).valueObject();
			}
			return (T.isInstance(value)) ? (T) value : defaultValue;
		} catch (InstanceCreationException ex) {
			ex.printStackTrace();
			throw new IllegalStateException(ex);
		}
	}
	
	public boolean removeAttribute(String key) {
		return context.getAttributes().remove(key) != null ;
	}

	

	public Object putAttribute(String key, Object value) {
		return getAttributes().put(key, value);
	}

	public ConcurrentMap getAttributes() {
		return context.getAttributes() ;
	}

	public boolean contains(Object key) {
		return getAttributes().get(key) != null;
	}

	public Client getClientDispatcher() {
		return context.getClientDispatcher();
	}

	public Enroler getDefaultEnroler() {
		return context.getDefaultEnroler();
	}

	public Verifier getDefaultVerifier() {
		return context.getDefaultVerifier();
	}

	public Logger getLogger() {
		return context.getLogger();
	}

	public Series getParameters() {
		return context.getParameters();
	}

	public Client getServerDispatcher() {
		return context.getServerDispatcher();
	}

	public void setAttributes(Map attributes) {
		context.setAttributes(attributes);
	}

	public void setClientDispatcher(Client clientDispatcher) {
		context.setClientDispatcher(clientDispatcher);
	}

	public void setDefaultEnroler(Enroler enroler) {
		context.setDefaultEnroler(enroler);
	}

	public void setDefaultVerifier(Verifier verifier) {
		context.setDefaultVerifier(verifier);
	}

	public void setLogger(Logger logger) {
		context.setLogger(logger);
	}

	public void setLogger(String loggerName) {
		context.setLogger(loggerName);
	}

	public void setParameters(Series parameters) {
		context.setParameters(parameters);
	}

	public void setServerDispatcher(Client serverDispatcher) {
		context.setServerDispatcher(serverDispatcher);
	}

	private void addFilter(FilterLocation location, IService service, IRadonFilter filter) {
		if (!getFilters(location).contains(filter)) {
			getFilters(location).add(filter);
			filter.init(service);

		}
	}

	
	
	
	void addPreFilter(IService service, IRadonFilter filter) {

		addFilter(FilterLocation.PRE, service, filter);
	}

	void addAfterFilter(IService service, IRadonFilter filter) {
		addFilter(FilterLocation.AFTER, service, filter);
	}

	List<IRadonFilter> getPreFilters() {
		return Collections.unmodifiableList(getFilters(FilterLocation.PRE));
	}

	List<IRadonFilter> getAfterFilters() {
		return Collections.unmodifiableList(getFilters(FilterLocation.AFTER));
	}

	private List<IRadonFilter> getFilters(FilterLocation location) {
		List<IRadonFilter> filters = (List<IRadonFilter>) getSelfAttributeObject(location.getString(), List.class);
		if (filters == null) {
			filters = ObjectUtil.coalesce(getSelfAttributeObject(location.getString(), List.class), ListUtil.newList());
			putAttribute(location.getString(), filters);
		}
		return filters;
	}

	void addPreFilter(int index, IRadonFilter filter) {
		if (!getPreFilters().contains(filter)) {
			getFilters(FilterLocation.PRE).add(index, filter);
		}
	}

	void removeFilter(FilterLocation location, IRadonFilter self) {
		getFilters(location).remove(self);
	}

	public String toString() {
		return getZone() + "Context:" + hashCode();
	}

	void restart() {
		removeFilter(FilterLocation.PRE, RevokeServiceFilter.SELF);
	}

	void suspend() {
		addPreFilter(0, RevokeServiceFilter.SELF);
	}

	public void loadAttribute(IService service, XMLConfig config) throws ConfigurationException, InstanceCreationException {
		AttributeUtil.load(service, config) ;
	}


}

package net.ion.bleujin.section;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.engine.Engine;
import org.restlet.engine.RestletHelper;
import org.restlet.engine.resource.AnnotationUtils;
import org.restlet.resource.Finder;
import org.restlet.security.Role;
import org.restlet.service.ConnectorService;
import org.restlet.service.ConverterService;
import org.restlet.service.DecoderService;
import org.restlet.service.MetadataService;
import org.restlet.service.RangeService;
import org.restlet.service.StatusService;
import org.restlet.service.TaskService;
import org.restlet.service.TunnelService;
import org.restlet.util.ServiceList;

public class BaseSection extends Restlet {

	private static final ThreadLocal CURRENT = new ThreadLocal();
	private volatile Class finderClass;
	private volatile RestletHelper helper;
	private final List roles;
	private volatile Restlet inboundRoot;
	private volatile Restlet outboundRoot;
	private final ServiceList services;

	public static BaseSection getCurrent() {
		return (BaseSection) CURRENT.get();
	}

	public static void setCurrent(BaseSection application) {
		CURRENT.set(application);
	}

	public BaseSection() {
		this(null);
	}

	public BaseSection(Context context) {
		super(context);
		if (Engine.getInstance() != null)
			helper = new SectionHelper(this);
		outboundRoot = null;
		inboundRoot = null;
		roles = new CopyOnWriteArrayList();
		services = new ServiceList(context);
		services.add(new TunnelService(true, true));
		services.add(new StatusService());
		services.add(new DecoderService());
		services.add(new RangeService());
		services.add(new ConnectorService());
		services.add(new ConverterService());
		services.add(new MetadataService());
		services.add(new TaskService());
	}

	public Restlet createInboundRoot() {
		return null;
	}

	public Restlet createOutboundRoot() {
		return getContext() == null ? null : getContext().getClientDispatcher();
	}

	public ConnectorService getConnectorService() {
		return (ConnectorService) getServices().get(ConnectorService.class);
	}

	public ConverterService getConverterService() {
		return (ConverterService) getServices().get(ConverterService.class);
	}

	public DecoderService getDecoderService() {
		return (DecoderService) getServices().get(DecoderService.class);
	}

	public Class getFinderClass() {
		return finderClass;
	}

	private RestletHelper getHelper() {
		return helper;
	}

	public Restlet getInboundRoot() {
		if (inboundRoot == null)
			synchronized (this) {
				if (inboundRoot == null)
					inboundRoot = createInboundRoot();
			}
		return inboundRoot;
	}

	public MetadataService getMetadataService() {
		return (MetadataService) getServices().get(MetadataService.class);
	}

	public Restlet getOutboundRoot() {
		if (outboundRoot == null)
			synchronized (this) {
				if (outboundRoot == null)
					outboundRoot = createOutboundRoot();
			}
		return outboundRoot;
	}

	public RangeService getRangeService() {
		return (RangeService) getServices().get(RangeService.class);
	}

	public Role getRole(String name) {
		for (Iterator i$ = getRoles().iterator(); i$.hasNext();) {
			Role role = (Role) i$.next();
			if (role.getName().equals(name))
				return role;
		}

		return null;
	}

	public List getRoles() {
		return roles;
	}

	public ServiceList getServices() {
		return services;
	}

	public StatusService getStatusService() {
		return (StatusService) getServices().get(StatusService.class);
	}

	public TaskService getTaskService() {
		return (TaskService) getServices().get(TaskService.class);
	}

	public TunnelService getTunnelService() {
		return (TunnelService) getServices().get(TunnelService.class);
	}

	public void handle(Request request, Response response) {
		super.handle(request, response);
		if (getHelper() != null)
			getHelper().handle(request, response);
	}

	public synchronized void setOutboundRoot(Class outboundRootClass) {
		setClientRoot(outboundRootClass);
	}

	/**
	 * @deprecated Method setClientRoot is deprecated
	 */
	public synchronized void setClientRoot(Class clientRootClass) {
		setOutboundRoot(Finder.createFinder(clientRootClass, getFinderClass(), getContext(), getLogger()));
	}

	public void setConnectorService(ConnectorService connectorService) {
		getServices().set(connectorService);
	}

	public void setContext(Context context) {
		super.setContext(context);
		getServices().setContext(context);
	}

	public void setConverterService(ConverterService converterService) {
		getServices().set(converterService);
	}

	public void setDecoderService(DecoderService decoderService) {
		getServices().set(decoderService);
	}

	public void setFinderClass(Class finderClass) {
		this.finderClass = finderClass;
	}

	public synchronized void setInboundRoot(Class inboundRootClass) {
		setInboundRoot(((Restlet) (Finder.createFinder(inboundRootClass, getFinderClass(), getContext(), getLogger()))));
	}

	public synchronized void setInboundRoot(Restlet inboundRoot) {
		this.inboundRoot = inboundRoot;
		if (inboundRoot != null && inboundRoot.getContext() == null)
			inboundRoot.setContext(getContext());
	}

	public void setMetadataService(MetadataService metadataService) {
		getServices().set(metadataService);
	}

	public synchronized void setOutboundRoot(Restlet outboundRoot) {
		this.outboundRoot = outboundRoot;
		if (outboundRoot != null && outboundRoot.getContext() == null)
			outboundRoot.setContext(getContext());
	}

	public void setRangeService(RangeService rangeService) {
		getServices().set(rangeService);
	}

	public void setRoles(List roles) {
		synchronized (getRoles()) {
			if (roles != getRoles()) {
				getRoles().clear();
				if (roles != null)
					getRoles().addAll(roles);
			}
		}
	}

	public void setStatusService(StatusService statusService) {
		getServices().set(statusService);
	}

	public void setTaskService(TaskService taskService) {
		getServices().set(taskService);
	}

	public void setTunnelService(TunnelService tunnelService) {
		getServices().set(tunnelService);
	}

	public synchronized void start() throws Exception {
		if (isStopped()) {
			super.start();
			if (getHelper() != null)
				getHelper().start();
			getServices().start();
			if (getInboundRoot() != null)
				getInboundRoot().start();
			if (getOutboundRoot() != null)
				getOutboundRoot().start();
		}
	}

	public synchronized void stop() throws Exception {
		if (isStarted()) {
			if (getOutboundRoot() != null)
				getOutboundRoot().stop();
			if (getInboundRoot() != null)
				getInboundRoot().stop();
			getServices().stop();
			if (getHelper() != null)
				getHelper().stop();
			AnnotationUtils.clearCache();
			super.stop();
		}
	}

}

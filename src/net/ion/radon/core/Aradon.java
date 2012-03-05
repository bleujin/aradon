package net.ion.radon.core;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.radon.core.EnumClass.FilterLocation;
import net.ion.radon.core.classloading.PathFinder;
import net.ion.radon.core.config.AradonConstant;
import net.ion.radon.core.config.ConnectorConfig;
import net.ion.radon.core.config.Releasable;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.context.OnEventObject;
import net.ion.radon.core.context.OnEventObject.AradonEvent;
import net.ion.radon.core.except.AradonRuntimeException;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.core.representation.JsonObjectRepresentation;
import net.ion.radon.core.server.AradonServerHelper;
import net.ion.radon.core.server.ServerFactory;

import org.apache.commons.configuration.ConfigurationException;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Protocol;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.routing.Router;
import org.restlet.service.ConverterService;
import org.restlet.service.MetadataService;
import org.restlet.service.Service;

public class Aradon extends Component implements IService, AradonConstant {

	private Map<String, SectionService> sections;
	private List<WrapperReleaseObject> releasables;
	private TreeContext rootContext;

	private AradonServerHelper serverHelper;
	private AradonConfig aconfig;

	public Aradon() {
		super();
		this.releasables = ListUtil.newList();
		this.sections = MapUtil.newCaseInsensitiveMap();
		this.rootContext = TreeContext.createRootContext(getDefaultHost());
	}

	public void init(String rootConfigFilePath) throws ConfigurationException, InstanceCreationException {
		if (!new File(rootConfigFilePath).exists()) {
			throw new ConfigurationException(rootConfigFilePath + " not exists");
		}
		init(XMLConfig.create(rootConfigFilePath));
	}

	public void init(XMLConfig config) throws ConfigurationException, InstanceCreationException {
		this.aconfig = AradonConfig.create(config).init(this, rootContext);

		setLogService(new RadonLogService());

		getServers().add(Protocol.RIAP);
		getClients().add(Protocol.RIAP);
	}

	public Response handle(Request request) {
		final Response response = new Response(request);
		handle(request, response);
		return response;
	}

	public void handle(Request request, Response response) {
		if (!isStarted())
			start();

		InnerRequest innerRequest = InnerRequest.create(request);
		InnerResponse innerResponse = InnerResponse.create(response, innerRequest);
		// innerResponse.setRequest(innerRequest) ;

		// response.setRequest(innerRequest) ;
		// Response innerResponse = response ;
		try {

			if (Protocol.RIAP.equals(innerRequest.getProtocol())) {
				getDefaultHost().getContext().getClientDispatcher().handle(innerRequest, innerResponse);

			} else {
				super.handle(innerRequest, innerResponse);
			}

		} catch (ResourceException ex) {
			ex.printStackTrace();
			getLogger().warning(ex.getMessage());
			response.setStatus(ex.getStatus());
		} catch (Exception ex) {
			ex.printStackTrace();
			getLogger().warning(ex.getMessage());
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, ex);
		} catch (Throwable ex) {
			ex.printStackTrace();
			getLogger().warning(ex.getMessage());
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, ex);
		}
	}

	public void addReleasable(IService service, Releasable releasable) {
		releasables.add(WrapperReleaseObject.create(service, releasable));
	}

	private void slayReleasable() {
		if (started)
			return;
		Debug.debug("Bye....... Aradon");
		for (WrapperReleaseObject releasable : releasables) {
			if (releasable != null && releasable.getValue() != null)
				releasable.getValue().doRelease();
		}

		for (SectionService section : sections.values()) {
			try {
				section.stop();
			} catch (Throwable ignore) {
				ignore.printStackTrace();
			}
		}
		this.started = false;
		Debug.debug("End World.........");
	}

	private void setSection(String sectionName, Application section) {
		if (sections.containsKey(sectionName)) {
			Debug.warn("SECTION[" + sectionName + "] already exists. Ignored....======================");
		} else {
			Debug.info("SECTION : " + sectionName + " loaded");
			sections.put(sectionName, (SectionService) section);
		}
	}

	private transient boolean started = false;

	@Override
	public void stop() {
		if (!started)
			return;
		try {
			super.stop();
			getConfig().stopShell();
			onEventFire(AradonEvent.STOP, this);
			if (serverHelper != null)
				serverHelper.stop();
		} catch (Exception ignore) {
			ignore.printStackTrace();
			getLogger().warning(ignore.getMessage());
		} finally {
			this.slayReleasable();
			getLogger().warning("aradon server stoped");
			this.started = false;
		}
	}

	public AradonConfig getConfig() {
		return this.aconfig;
	}

	@Override
	public void start() {
		if (this.started)
			return;
		try {
			super.start();
			getConfig().loadPlugIn(this);
		} catch (Exception e) {
			throw new AradonRuntimeException(e);
		}

		onEventFire(AradonEvent.START, this);
		this.started = true;
	}

	public void startServer(int port) throws Exception {
		if (alreadyUsePortNum(port)) {
			Debug.warn(port + " port is occupied");
			throw new IllegalArgumentException(port + " port is occupied");
		}

		startServer(aconfig.getConnectorConfig(port));
		getConfig().launchShell(this);
	}

	public void startServer(ConnectorConfig cfig) throws Exception {
		if (!isStarted())
			start();
		serverHelper = ServerFactory.create(getContext(), this, cfig);

		serverHelper.start();
		serverHelper.addTo(getServers());

		final Aradon aradon = this;
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				aradon.stop();
			}
		});

		getLogger().warning("aradon started : " + cfig.getPort());

		rootContext.putAttribute(CONFIG_PORT, cfig.getPort());
	}

	public synchronized void reload() throws Exception {
		// if (true) throw new UnsupportedOperationException("not yet") ;

		// reload section
		for (Application section : this.sections.values()) {
			this.getDefaultHost().detach(section);
		}
		sections.clear();

		// release
		slayReleasable();

		aconfig.init(this, rootContext);
		onEventFire(AradonEvent.RELOAD, this);
	}

	private boolean alreadyUsePortNum(int port) {
		Socket s = null;
		try {
			s = new Socket(InetAddress.getLocalHost(), port);
			s.setSoTimeout(1000);
			s.close();
			return true;
		} catch (UnknownHostException e) {
			e.getStackTrace();
			return false;
		} catch (IOException e) {
			e.getStackTrace();
			return false;
		} finally {
			if (s != null)
				try {
					s.close();
				} catch (IOException ignore) {
					ignore.printStackTrace();
				}
		}
	}

	private void onEventFire(AradonEvent event, IService service) {
		for (Object key : service.getServiceContext().getAttributes().keySet()) {
			Object value = service.getServiceContext().getAttributeObject(ObjectUtil.toString(key));
			if (OnEventObject.class.isInstance(value)) {
				((OnEventObject) value).onEvent(event, service);
			}
		}

		for (IService child : service.getChildren()) {
			onEventFire(event, child);
		}
	}

	public void addAfterFilter(IRadonFilter filter) {
		rootContext.addAfterFilter(this, filter);
	}

	public void addPreFilter(IRadonFilter filter) {
		rootContext.addPreFilter(this, filter);
	}

	public List<IRadonFilter> getAfterFilters() {
		return rootContext.getAfterFilters();
	}

	public List<IRadonFilter> getPreFilters() {
		return rootContext.getPreFilters();
	}

	public TreeContext getServiceContext() {
		return rootContext;
	}

	public void removeAfterFilter(IRadonFilter filter) {
		rootContext.removeFilter(FilterLocation.AFTER, filter);
	}

	public void removePreFilter(IRadonFilter filter) {
		rootContext.removeFilter(FilterLocation.PRE, filter);
	}

	public void restart() {
		rootContext.restart();
	}

	public void suspend() {
		rootContext.suspend();
	}

	public IService getParent() {
		return IService.ROOT;
	}

	public SectionService attach(String sectionName, XMLConfig xmlConfig) throws ConfigurationException, InstanceCreationException {

		final SectionService section = SectionFactory.parse(this, sectionName, xmlConfig);
		attach(section);
		return section;
	}

	public void attach(SectionService section) {
		getDefaultHost().attach("/" + section.getName(), section, Router.MODE_BEST_MATCH);
		getInternalRouter().attach("/" + section.getName(), section, Router.MODE_BEST_MATCH);
		setSection(section.getName(), section);

	}

	public void detach(String sectionName) {
		SectionService section = getChildService(sectionName);
		getDefaultHost().detach(section);
		getInternalRouter().detach(section);
		sections.remove(sectionName);

		getLogger().warning(sectionName + " section detattached..");
	}

	public SectionService getChildService(String name) {
		final SectionService result = sections.get(name);
		if (result == null)
			throw new IllegalArgumentException("not found section : " + name);
		return result;
	}

	public Collection<SectionService> getChildren() {
		return Collections.unmodifiableCollection(this.sections.values());
	}

	public Aradon getAradon() {
		return this;
	}

	public String getName() {
		return "aradon";
	}

	public String getNamePath() {
		return "/";
	}

	public RadonLogService getRadonLogService() {
		return (RadonLogService) getLogService();
	}

	public List<String> getRecentLog(int count) {
		return getRadonLogService().recentLog(this, count);
	}

	public <T> T handle(Request request, Class<? extends T> resultClass) {
		if (!isStarted())
			start();

		Response response = handle(request);
		if (!response.getStatus().isSuccess())
			throw new ResourceException(response.getStatus(), response.toString());

		try {
			Representation entity = response.getEntity();
			if (entity == null) {
				return null; // or Unable to find a converter for this object
			} else if (entity instanceof JsonObjectRepresentation) {
				return ((JsonObjectRepresentation) entity).getJsonObject().getAsObject(resultClass);
			} else {
				Object resultObj = ((ObjectRepresentation) entity).getObject();
				return resultClass.cast(resultObj);
			}
		} catch (IOException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e.getMessage());
		}
	}

	public ConverterService getConverterService() {
		ConverterService result = rootContext.getAttributeObject(ConverterService.class.getCanonicalName(), ConverterService.class);
		if (result == null) {
			rootContext.putAttribute(ConverterService.class.getCanonicalName(), new ConverterService());
			return getConverterService();
		} else {
			return result;
		}
	}

	public MetadataService getMetadataService() {
		MetadataService result = rootContext.getAttributeObject(MetadataService.class.getCanonicalName(), MetadataService.class);
		if (result == null) {
			rootContext.putAttribute(MetadataService.class.getCanonicalName(), new MetadataService());
			return getMetadataService();
		} else {
			return result;
		}
	}

	public boolean containsSection(String sectionName) {
		return sections.containsKey(sectionName);
	}

	public Engine getEngine() {
		return Engine.getInstance();
	}

}

class WrapperReleaseObject {

	private IService service;
	private Releasable releasable;

	private WrapperReleaseObject(IService service, Releasable releasable) {
		this.service = service;
		this.releasable = releasable;
	}

	Releasable getValue() {
		return releasable;
	}

	static WrapperReleaseObject create(IService service, Releasable releasable) {
		return new WrapperReleaseObject(service, releasable);
	}

}
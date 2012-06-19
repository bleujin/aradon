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
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.radon.client.AsyncHttpHandler;
import net.ion.radon.core.EnumClass.FilterLocation;
import net.ion.radon.core.EnumClass.PlugInApply;
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
import net.ion.radon.core.server.AradonServerHelper;
import net.ion.radon.core.server.ServerFactory;

import org.apache.commons.configuration.ConfigurationException;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Server;
import org.restlet.Uniform;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.routing.Router;
import org.restlet.service.ConverterService;
import org.restlet.service.MetadataService;

public class Aradon extends Component implements IService, AradonConstant {

	private Map<String, SectionService> sections;
	private List<WrapperReleaseObject> releasables;
	private TreeContext rootContext;

	private AradonConfig aconfig;
	private AradonServerHelper serverHelper;
	private boolean initialized = false ;
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
		this.initialized = true ;
	}

	public Response handle(Request request) {
		final Response response = new Response(request);
		handle(request, response);
		return response;
	}
	
	private String getSectionName(Request request) {
		Reference resourceRef = request.getResourceRef() ;
		List<String> segs = resourceRef.getSegments() ;
		if(segs.size() <= 1) {
			return "" ;
		}
		String firstSeg = segs.get(0);
		return sections.containsKey(firstSeg) ? firstSeg : "" ;
	}


	public void handle(Request request, Response response) {
		if (!isStarted())
			start();

		InnerRequest innerRequest = InnerRequest.create(getSectionName(request), request);
		
		// new URI(request.getResourceRef().toString()).toString()
		

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
		if (isStarted())
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
		Debug.debug("End World.........");
	}

	@Override
	public void stop() {
		if (isStopped()) return ;
		try {
			super.stop() ;
			getConfig().stopShell();
			onEventFire(AradonEvent.STOP, this);
 			getServices().stop() ;
//			getServices().clear() ;
			if (serverHelper != null) serverHelper.stop() ;
		} catch (Exception ignore) {
			ignore.printStackTrace();
			getLogger().warning(ignore.getMessage());
		} finally {
			this.slayReleasable();
			getLogger().warning("aradon server stoped");
		}
	}

	public AradonConfig getConfig() {
		return this.aconfig;
	}

	@Override
	public void start() {
		if (isStarted()) {
			return ;
		}
		try {
			if (! initialized) init(XMLConfig.BLANK) ;
			getServers().add(new Server(Protocol.RIAP)) ;
			super.start();
			getConfig().loadPlugIn(this);
		} catch (Exception e) {
			throw new AradonRuntimeException(e);
		}

		onEventFire(AradonEvent.START, this);
	}

	public void startServer(int port) throws Exception {
		if (! initialized) init(XMLConfig.BLANK) ;
		if (port > 0 && alreadyUsePortNum(port)) {
			Debug.warn(port + " port is occupied");
			throw new IllegalArgumentException(port + " port is occupied");
		}
		startServer(aconfig.getConnectorConfig(port));
		getConfig().launchShell(this);
	}

	public synchronized void startServer(ConnectorConfig cfig) throws Exception {
		rootContext.putAttribute(CONFIG_PORT, cfig.getPort());
		
		this.serverHelper = ServerFactory.create(getContext(), this, cfig) ;
		serverHelper.start() ;
//		loadServerHelper(cfig) ;

		start() ;

		final Aradon aradon = this;
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				aradon.stop();
			}
		});

		getLogger().warning("aradon started : " + cfig.getPort());
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
			IOUtil.closeQuietly(s) ;
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

	public void attach(SectionService newSection, PlugInApply apply) {
		
		String newName = newSection.getName();
		if (! sections.containsKey(newName)) {
			Debug.info("SECTION : " + newName + " loaded");
			getDefaultHost().attach("/" + newName, newSection, Router.MODE_BEST_MATCH);
			getInternalRouter().attach("/" + newName, newSection, Router.MODE_BEST_MATCH);
			sections.put(newName, (SectionService) newSection);
		} else {
			if (apply == PlugInApply.IGNORE){
				Debug.warn("SECTION[" + newName + "] already exists. Ignored....======================");
			} else if (apply == PlugInApply.OVERWRITE){
				SectionService existSection = sections.get(newName) ;
				onEventFire(AradonEvent.STOP, existSection) ;
				
				getDefaultHost().detach(existSection) ;
				getInternalRouter().detach(existSection) ;
				
				getDefaultHost().attach("/" + newName, newSection, Router.MODE_BEST_MATCH);
				getInternalRouter().attach("/" + newName, newSection, Router.MODE_BEST_MATCH);
				sections.put(newName, (SectionService) newSection);
				
				Debug.line('%', newSection) ;
				
			} else if (apply == PlugInApply.MERGE) {
				SectionService existSection = sections.get(newName) ;
				for (IRadonFilter prefilter : newSection.getPreFilters()) {
					existSection.addPreFilter(prefilter) ;
				}  
				for (IRadonFilter afterFilter : newSection.getAfterFilters()) {
					existSection.addAfterFilter(afterFilter) ;
				}  
				for (Entry entry : (Set<Entry>)(newSection.getServiceContext().getAttributes().entrySet())) {
					existSection.getServiceContext().getAttributes().put(entry.getKey(), entry.getValue()) ;
				}
				for(PathService pservice : newSection.getChildren()) {
					existSection.attach(pservice) ;
				}
			}
		}
		
		
	}

	public void attach(SectionService section) {
		attach(section, PlugInApply.IGNORE) ;
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
package net.ion.radon.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.config.RadonConfigurationBuilder;
import net.ion.nradon.handler.aradon.AradonHandler;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.EnumClass.PlugInApply;
import net.ion.radon.core.config.AradonConfiguration;
import net.ion.radon.core.config.AradonConstant;
import net.ion.radon.core.config.AttributeValue;
import net.ion.radon.core.config.Configuration;
import net.ion.radon.core.config.ConfigurationBuilder;
import net.ion.radon.core.config.ConnectorConfiguration;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.config.SectionConfiguration;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.context.OnEventObject;
import net.ion.radon.core.context.OnOrderEventObject;
import net.ion.radon.core.context.OnEventObject.AradonEvent;
import net.ion.radon.core.except.AradonRuntimeException;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.let.FilterUtil;
import net.ion.radon.core.let.IRadonPathService;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.core.let.PathService;
import net.ion.radon.core.server.AradonServerHelper;
import net.ion.radon.core.server.ServerFactory;
import net.ion.radon.impl.filter.RevokeServiceFilter;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.resource.ResourceException;
import org.restlet.routing.Filter;
import org.restlet.routing.Router;
import org.restlet.service.ConverterService;
import org.restlet.service.MetadataService;

public class Aradon extends Component implements IService<SectionService>, AradonConstant {

	private TreeContext rootContext;

	// private AradonConfig aconfig;
	private AradonServerHelper serverHelper;
	private Aradon() {
		super();
		this.rootContext = TreeContext.createRootContext(getDefaultHost());
	}

	public static Aradon create(XMLConfig config) throws InstanceCreationException {
		return create(ConfigurationBuilder.load(config).build());
	}

	public static Aradon create(String configFilePath) throws InstanceCreationException {
		if (!new File(configFilePath).exists()) {
			throw new IllegalArgumentException(configFilePath + " not exists");
		}
		return create(XMLConfig.create(configFilePath));
	}

	public static Aradon create() {
		return create(ConfigurationBuilder.newBuilder().build());
	}

	public static Aradon create(Configuration config) {
		Aradon aradon = new Aradon();
		aradon.init(config);
		return aradon;
	}

	private Configuration config;
	private Map<String, SectionService> sections = MapUtil.newMap();

	private void init(Configuration config) {
		for (Entry<String, AttributeValue> entry : config.aradon().attributes().entrySet()) {
			rootContext.putAttribute(entry.getKey(), entry.getValue());
		}
		this.config = config;
		config.aradon().initFilter(this) ;
		this.setContext(rootContext);
		setLogService(new RadonLogService());

		
		for (SectionConfiguration sconfig : config.aradon().sections().restSections()) {
			attach(sconfig);
		}

		for (Entry<String, AttributeValue> entry : config.plugin().attributes().entrySet()){
			rootContext.putAttribute(entry.getKey(), entry.getValue());
		}
		
		for (SectionConfiguration pconfig : config.plugin().sections()){
			attach(pconfig) ;
		}
		rootContext.putAttribute(CONFIG_PORT, config.server().connector().port());
		
		//start();
	}

	private String getSectionName(Request request) {
		Reference resourceRef = request.getResourceRef();
		List<String> segs = resourceRef.getSegments();
		if (segs.size() <= 1) {
			return "";
		}
		String firstSeg = segs.get(0);
		return sections.containsKey(firstSeg) ? firstSeg : "";
	}

	public void handle(Request request, Response response) {
		if (!isStarted())
			start();

		InnerRequest innerRequest = InnerRequest.create(getSectionName(request), request);
		InnerResponse innerResponse = InnerResponse.create(response, innerRequest);
		// innerResponse.setRequest(innerRequest) ;

		IFilterResult preResult = FilterUtil.preHandle(this, config.aradon().prefilters(), innerRequest, innerResponse);
		if (preResult.getResult() == Filter.STOP) {
			response.setStatus(preResult.getCause().getStatus());
			response.setEntity(preResult.getReplaceRepresentation());
			return;
		}
		try {
			if (preResult.getResult() != Filter.SKIP) {
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
		} finally {
			FilterUtil.afterHandle(this, config.aradon().afterfilters(), innerRequest, innerResponse);
		}
	}

	// public void addReleasable(IService service, Releasable releasable) {
	// releasables.add(WrapperReleaseObject.create(service, releasable));
	// }

	@Override
	public void stop() {
		this.destorySelf();
	}

	public void destorySelf() {
		if (isStopped())
			return;

		onEventFire(AradonEvent.STOP, this);
		List<String> sectionNames = ListUtil.newList() ;
		for (SectionService section : sections.values()) {
			sectionNames.add(section.getName());
		}
		for (String sectionName : sectionNames) {
			detach(sectionName);
		}

		
		getServiceContext().closeAttribute();

		getGlobalConfig().server().stopShell();
		try {
			// getServices().stop();
			// getServices().clear();
			if (serverHelper != null)
				serverHelper.stop();

			try {
				super.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ignore) {
			ignore.printStackTrace();
			getLogger().warning(ignore.getMessage());
		} finally {
			Debug.debug("Bye....... Aradon");
			getLogger().warning("aradon server stoped");
		}
	}

	public AradonConfiguration getConfig() {
		return config.aradon();
	}

	public Configuration getGlobalConfig() {
		return config;
	}

	@Override
	public void start() {
		if (isStarted()) {
			return;
		}
		try {
			getServers().add(new Server(Protocol.RIAP));
			super.start();
//			getGlobalConfig().plugin().loadPlugIn(this);
		} catch (Exception e) {
			throw new AradonRuntimeException(e);
		}

		onEventFire(AradonEvent.START, this);
	}

	public Aradon startServer(int port) throws Exception {

		long start = System.currentTimeMillis();
		// if (port > 0 && useAlreadyPortNum(port)) {
		// Debug.warn(port + " port is occupied");
		// throw new IllegalArgumentException(port + " port is occupied");
		// }

		// Debug.line(System.currentTimeMillis() - start) ;

		startServer(config.server().connector().port(port));
		return this;
	}

	public synchronized void startServer(ConnectorConfiguration cfig) throws Exception {
		rootContext.putAttribute(CONFIG_PORT, cfig.port());

		this.serverHelper = ServerFactory.create(getContext(), this, cfig);
		serverHelper.start();

		// start();

		final Aradon aradon = this;
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				aradon.destorySelf();
			}
		});

		getGlobalConfig().server().launchShell(this);
		getLogger().warning("aradon started : " + cfig.port());
	}

	public synchronized void reload() throws Exception {
		// if (true) throw new UnsupportedOperationException("not yet") ;

		// reload section
		for (Application section : this.sections.values()) {
			this.getDefaultHost().detach(section);
		}
		sections.clear();

		config.init(this, rootContext);
		onEventFire(AradonEvent.RELOAD, this);
	}

	private boolean useAlreadyPortNum(int port) {
		Socket s = null;
		try {
			s = new Socket(InetAddress.getLocalHost(), port);
			s.setSoTimeout(400);
			s.close();
			return true;
		} catch (UnknownHostException e) {
			e.getStackTrace();
			return false;
		} catch (IOException e) {
			e.getStackTrace();
			return false;
		} finally {
			IOUtil.closeQuietly(s);
		}
	}

	private void onEventFire(final AradonEvent event, IService iservice) {
		TreeContext serviceContext = iservice.getServiceContext();
		List<OnEventObject> temp = sortEventObject(event, serviceContext);
		
		for (OnEventObject eventObject : temp) {
			 eventObject.onEvent(event, iservice);
		}

		for (Object child : iservice.getChildren()) {
			onEventFire(event, (IService)child);
		}
	}
	

	private List<OnEventObject> sortEventObject(final AradonEvent event, TreeContext serviceContext) {
		List<OnEventObject> result =  ListUtil.newList() ;
		for (Object key : serviceContext.getAttributes().keySet()) {
			Object value = serviceContext.getAttributeObject(ObjectUtil.toString(key));
			if (OnEventObject.class.isInstance(value)) {
				result.add((OnEventObject) value) ;
			}
		}
		
		Collections.sort(result, new Comparator<OnEventObject>(){
			private int order(OnEventObject o){
				if (o instanceof OnOrderEventObject){
					return ((OnOrderEventObject)o).order() ;
				} else {
					return 100 ;
				}
			}
			public int compare(OnEventObject o1, OnEventObject o2) {
				return (order(o1) - order(o2)) * (event == AradonEvent.START ? 1 : -1);
			}}) ;
		return result;
	}
	
	public SectionService attach(SectionConfiguration sconfig) {
		if (sections.containsKey(sconfig.name())) {
			throw new IllegalArgumentException("already exist section : " + sconfig);
		}
		HttpRestSection restSection = HttpRestSection.create(this, rootContext.createChildContext(), sconfig);
		getDefaultHost().attach("/" + sconfig.name(), restSection, Router.MODE_BEST_MATCH);
		getInternalRouter().attach("/" + sconfig.name(), restSection, Router.MODE_BEST_MATCH);
		sections.put(sconfig.name(), restSection);
		return restSection;
	}

	public SectionService attach(SectionConfiguration sconfig, PlugInApply apply) {

		String newName = sconfig.name();

		if (!sections.containsKey(newName)) {
			HttpRestSection newSection = HttpRestSection.create(this, rootContext.createChildContext(), sconfig);
			Debug.info("SECTION : " + newName + " loaded");
			getDefaultHost().attach("/" + newName, newSection, Router.MODE_BEST_MATCH);
			getInternalRouter().attach("/" + newName, newSection, Router.MODE_BEST_MATCH);
			sections.put(newName, newSection);
			return newSection;
		} else {
			SectionService existSection = sections.get(newName);
			if (apply == PlugInApply.IGNORE) {
				Debug.warn("SECTION[" + newName + "] already exists. Ignored....======================");
			} else if (apply == PlugInApply.OVERWRITE) {
				if (existSection != null) {
					onEventFire(AradonEvent.STOP, existSection);

					getDefaultHost().detach(existSection);
					getInternalRouter().detach(existSection);
				}

				HttpRestSection newSection = HttpRestSection.create(this, rootContext.createChildContext(), sconfig);
				getDefaultHost().attach("/" + newName, newSection, Router.MODE_BEST_MATCH);
				getInternalRouter().attach("/" + newName, newSection, Router.MODE_BEST_MATCH);
				sections.put(newName, newSection);

				Debug.debug('%', sconfig.toString());

			} else if (apply == PlugInApply.MERGE) {
				for (IRadonFilter prefilter : sconfig.prefilters()) {
					existSection.getConfig().addPreFilter(prefilter);
				}
				for (IRadonFilter afterFilter : sconfig.afterfilters()) {
					existSection.getConfig().addAfterFilter(afterFilter);
				}
				for (Entry<String, AttributeValue> entry : sconfig.attributes().entrySet()) {
					existSection.getConfig().attributes().put(entry.getKey(), entry.getValue());
				}
				for (PathConfiguration pconfig : sconfig.pathConfiguration()) {
					existSection.attach(pconfig);
				}
			}
			return existSection;
		}

	}

	public Aradon detach(String sname) {
		SectionService child = sections.get(sname);
		if (child == null)
			return this;
		config.aradon().sections().removeSection(sname);
		sections.remove(sname);
		getDefaultHost().detach(child);
		getInternalRouter().detach(child);
		child.stop();
		return this;
	}

	public TreeContext getServiceContext() {
		return rootContext;
	}

	public void restart() {
		config.aradon().removePreFilter(RevokeServiceFilter.SELF);
	}

	public void suspend() {
		config.aradon().addPreFilter(0, RevokeServiceFilter.SELF);
	}

	public IService getParent() {
		return IService.ROOT;
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

	public Radon toRadon() throws InterruptedException, ExecutionException, FileNotFoundException{
		return toRadon(config.server().connector().port()) ;
	}
	
	public Radon toRadon(int port) throws InterruptedException, ExecutionException, FileNotFoundException {
		RadonConfigurationBuilder rbuilder = RadonConfiguration.newBuilder(port);
		rbuilder.rootContext(this.getServiceContext()) ;
		AradonHandler aradonHandler = AradonHandler.create(this) ;
		
		for (SectionService ss : this.getChildren()) {
			if (StringUtil.isBlank(ss.getName())){ // default section
				for(PathService ps : ss.getPathChildren()){
					IMatchMode matchMode = ps.getConfig().imatchMode();
					
					for (String pattern : ps.getConfig().urlPatterns()) {
						rbuilder.add(coimpatibleStartWith(matchMode, pattern), aradonHandler) ;
					}
				}
			} else {
				
				for(IRadonPathService pservice : ss.getRadonChildren()){ // except path
					IMatchMode matchMode = pservice.getConfig().imatchMode() ;
					
					for (String pattern : pservice.getConfig().urlPatterns()) {
						rbuilder.add(coimpatibleStartWith(matchMode, "/" + ss.getName() + pattern), pservice.toHttpHandler()) ;
					}
				}
				
				rbuilder.add("/" + ss.getName() + "/{aradon_remainpath__}*", aradonHandler) ;
			}
			
		}
		
		Protocol protocol = config.server().connector().protocol();
		if (protocol.HTTPS.equals(protocol)){
			rbuilder.protocol(protocol).setupSsl(config.server().connector().getSslParam()) ;
		}
		
		return rbuilder.createRadon() ;
	}

	private String coimpatibleStartWith(IMatchMode matchMode, String pattern) {
		String newPattern = pattern ;
		if (matchMode == IMatchMode.STARTWITH && (!(pattern.endsWith("*")))){
			newPattern += "*" ;
		}
		return newPattern;
	}
	
	private List<String> astericURLPattern(List<String> urlPattrns, IMatchMode matchMode){
		List<String> result = ListUtil.newList() ;
		for (String urlPattern : urlPattrns) {
			String astericPattern = urlPattern.replaceAll("\\{[^\\/]*\\}", ".*");
			if (matchMode == IMatchMode.STARTWITH) astericPattern += urlPattern.equals("/") ? ".*" : "/.*" ;
			result.add(astericPattern) ;
		}
		return result ;
	}


}

package net.ion.radon.core;

import static net.ion.radon.core.RadonAttributeKey.IZONE_ATTRIBUTE_KEY;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.PathMaker;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.EnumClass.FilterLocation;
import net.ion.radon.core.EnumClass.IZone;
import net.ion.radon.core.cli.DirConfig;
import net.ion.radon.core.config.ConnectorConfig;
import net.ion.radon.core.config.Releasable;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.context.OnEventObject;
import net.ion.radon.core.context.OnEventObject.AradonEvent;
import net.ion.radon.core.except.AradonRuntimeException;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.let.FilterUtil;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.core.server.AradonServerHelper;
import net.ion.radon.core.server.ServerFactory;
import net.ion.radon.impl.section.PluginConfig;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.engine.Engine;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.routing.Router;
import org.restlet.service.ConverterService;
import org.restlet.service.MetadataService;

public class Aradon extends Component implements IService{

	private Map<String, SectionService> sections;
	private List<WrapperReleaseObject> releasables;
	private TreeContext rootContext;
	private List<String> childConfigPath;

	public static ThreadLocal<DirConfig> DIR_LOCAL = new ThreadLocal<DirConfig>();

	private XMLConfig rootConfig;
	private static Aradon CURRENT;
	private AradonServerHelper serverHelper ;
	
	public final static String CONFIG_PORT = "aradon.config.port";

	void setSection(String sectionName, Application section) {
		if (sections.containsKey(sectionName)) {
			Debug.warn("SECTION[" + sectionName + "] already exists. Ignored....======================");
		} else {
			Debug.info("SECTION : " + sectionName + " loaded");
			sections.put(sectionName, (SectionService) section);
		}
	}

	public void init(String rootConfigFilePath) throws ConfigurationException, SQLException, InstanceCreationException, SecurityException, FileNotFoundException, IOException {
		if (! new File(rootConfigFilePath).exists()) {
			throw new ConfigurationException(rootConfigFilePath + " not exists") ;
		}
		init(XMLConfig.create(rootConfigFilePath));
	}
	
	public void init(XMLConfig config) throws ConfigurationException, SQLException, InstanceCreationException, SecurityException, FileNotFoundException, IOException{
		this.rootConfig = config;
		initConfig(this.rootConfig);
		initLogConfig();


		CURRENT = this;
		setLogService(new RadonLogService());
		getServers().add(Protocol.RIAP);
		getClients().add(Protocol.RIAP) ;
		
		
	}


	private void initConfig(final XMLConfig config) throws UnknownHostException, SQLException, InstanceCreationException, ConfigurationException {
		this.releasables = ListUtil.newList();
		this.childConfigPath = ListUtil.newList();
		this.rootContext = TreeContext.createRootContext(getDefaultHost());
		this.sections = MapUtil.newCaseInsensitiveMap();

		getStatusService().setContactEmail(config.findChild("context.attribute", "id", RadonAttributeKey.LET_CONTACT_EMAIL).getElementValue());
		getStatusService().setHomeRef(new Reference(config.findChild("context.attribute", "id", RadonAttributeKey.LET_CONTACT_HELP_DOC).getElementValue()));

		// at this point, the default class loader has all the jars you indicated

		ClassAppender appender = new ClassAppender();
		loadClassPath(config, appender);
		appender.invokeURL();

		rootContext.putAttribute(IZONE_ATTRIBUTE_KEY, IZone.Application);
		File currentDir = new File("./");
		init(currentDir, config, rootContext);
		Debug.info("ROOT loaded");
		for (String configPath : childConfigPath) {
			File file = new File(configPath);
			if (!file.exists()) {
				Debug.warn(file.getAbsolutePath() + " not exists. ignored");
				continue;
			}
			XMLConfig childConfig = XMLConfig.create(file);
			init(currentDir, childConfig, rootContext);
			Debug.info(configPath + " parsed");
		}

		super.setContext(this.rootContext);

	}

	public synchronized void reload() throws Exception {

		// reload section
		for (Application section : this.sections.values()) {
			this.getDefaultHost().detach(this);
		}
		sections.clear();

		// release
		slayReleasable();

		initConfig(this.rootConfig);
		onEventFire(AradonEvent.RELOAD, this) ;
	}

	private void loadClassPath(XMLConfig rconfig, ClassAppender loader) throws ConfigurationException {
		List<XMLConfig> configs = rconfig.children("import");

		try {
			for (XMLConfig config : configs) {
				String configPath = config.getAttributeValue("path");
				final File file = new File(configPath);
				if (!file.exists()) {
					Debug.warn(file.getAbsolutePath() + " not exists. ignored");
					continue;
				} else if (childConfigPath.contains(configPath)) {
					Debug.warn(file.getAbsolutePath() + " recursived : infinite loop");
					continue;
				}

				childConfigPath.add(configPath);
				XMLConfig childConfig = XMLConfig.create(file);
				loadClassPath(childConfig, loader);
			}

			if (rconfig.hasChild("plugin")) {
				XMLConfig plugin = rconfig.firstChild("plugin");
				String[] paths = ObjectUtil.coalesce(StringUtil.split(plugin.getAttributeValue("includepath"), ";"), new String[0]);

				if (plugin.hasChild("jar")) {
					String[] jars = plugin.childValueList("jar[@src]").toArray(new String[0]);
					loader.appendJar(jars);
				}

				loader.appendPath(paths);
			}
		} catch (MalformedURLException ex) {
			throw new ConfigurationException(ex);
		}
	}

	private TreeContext init(File baseDir, XMLConfig config, TreeContext context) throws ConfigurationException, UnknownHostException, SQLException, InstanceCreationException {

		DirConfig dc = DirConfig.create(baseDir);
		DIR_LOCAL.set(dc);

		AttributeUtil.load(this, config);
		FilterUtil.setFilter(this, config);

		List<XMLConfig> sections = config.children("section");
		sections.addAll(config.children("notification"));
		for (XMLConfig sconfig : sections) {
			this.attach(sconfig.getAttributeValue("name"), sconfig);
		}

		return context;
	}

	public void appendPlugin(File pluginFile, XMLConfig config) throws Exception {
		this.initPlugin(pluginFile, config);
	}

	private TreeContext initPlugin(File pluginFile, XMLConfig config) throws ConfigurationException, UnknownHostException, SQLException, InstanceCreationException {
		String pluginName = StringUtils.substringBeforeLast(pluginFile.getName(), ".");
		final PluginConfig pconfig = PluginConfig.create(pluginName, config);
		// plugins.put(pluginName, pconfig);

		DirConfig dc = DirConfig.create(pluginFile.getParentFile());
		DIR_LOCAL.set(dc);

		AttributeUtil.load(this, config);
		FilterUtil.setFilter(this, config);

		List<XMLConfig> sections = config.children("section");
		for (XMLConfig sconfig : sections) {
			attach(SectionFactory.createSection(this, sconfig.getAttributeValue("name"), sconfig, pconfig));
		}
		return this.rootContext;
	}

	public Response handle(Request request) {
		final Response response = new Response(request);
		handle(request, response);
		return response;
	}

	public void handle(Request request, Response response) {
		if (! isStarted()) start() ;
		
		InnerRequest innerRequest = InnerRequest.create(request);
		InnerResponse innerResponse = InnerResponse.create(response, innerRequest);

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
		Debug.debug("Bye....... Aradon");
		for (WrapperReleaseObject releasable : releasables) {
			if (releasable != null && releasable.getValue() != null) releasable.getValue().doRelease();
		}

		for (SectionService section : sections.values()) {
			try {
				section.stop();
			} catch (Exception ignore) {
				ignore.printStackTrace();
			}
		}

		Debug.debug("End World.........");
	}

	private boolean started = false ;
	@Override
	public void stop() {
		if (! started) return ;
		try {
			super.stop();
			
			onEventFire(AradonEvent.STOP, this) ;
			if (serverHelper != null) serverHelper.stop() ;
		} catch (Exception ignore) {
			ignore.printStackTrace();
			getLogger().warning(ignore.getMessage());
		} finally {
			this.slayReleasable();
			getLogger().warning("aradon server stoped");
			this.started = false ;
		}
	}

	public XMLConfig getRootConfig() {
		return rootConfig;
	}

	
	public void startServer(int port) throws Exception {
		if (alreadyUsePortNum(port)) {
			Debug.warn(port + " port is occupied") ;
			throw new IllegalArgumentException(port + " port is occupied") ;
		}
		
		XMLConfig connConfig = rootConfig.firstChild("server-config.connector-config");
		startServer(ConnectorConfig.create(connConfig, port)) ;
	}
	
	
	@Override
	public void start(){
		try {
			super.start() ;
		} catch (Exception e) {
			throw new AradonRuntimeException(e) ;
		}
		
		onEventFire(AradonEvent.START, this) ;
		this.started = true ;
	}
	
	public void startServer(ConnectorConfig cfig) throws Exception{
		if (! isStarted()) start() ;
		serverHelper = ServerFactory.create(getContext(), this, cfig);

		serverHelper.start();
		serverHelper.addTo(getServers()) ;

		final Aradon aradon = this;
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				aradon.stop();
			}
		});

		getLogger().warning("aradon started : " + cfig.getPort());
		
		rootContext.putAttribute(CONFIG_PORT, cfig.getPort()) ;
	}

	private boolean alreadyUsePortNum(int port) {
		Socket s = null ;
		try {
			s = new Socket(InetAddress.getLocalHost(), port);
			s.setSoTimeout(1000);
			s.close() ;
			return true ;
		} catch (UnknownHostException e) {
			e.getStackTrace() ;
			return false ;
		} catch (IOException e) {
			e.getStackTrace() ;
			return false ;
		} finally {
			if (s != null) try {s.close() ;} catch (IOException ignore) {ignore.printStackTrace();}
		}
	}

	private void onEventFire(AradonEvent event, IService service) {
		for (Object key : service.getServiceContext().getAttributes().keySet()) {
			Object value = service.getServiceContext().getAttributeObject(ObjectUtil.toString(key)) ;
			if (OnEventObject.class.isInstance(value)){
				((OnEventObject)value).onEvent(event, service) ;
			}
		}
		
		for (IService child : service.getChildren()) {
			onEventFire(event, child) ;
		}
	}

	private void initLogConfig() throws SecurityException, FileNotFoundException, IOException {

		// 
		String logConfigPath = null;
		String homeDir = SystemUtils.getUserDir().getAbsolutePath();
		final String propertyFile = System.getProperty("java.util.logging.config.file");
		final String configuredFile = rootConfig.getString("server-config.log-config-file");

		if (StringUtil.isNotBlank(configuredFile) && new File(configuredFile).exists()) {
			logConfigPath = configuredFile;
		} else if (new File(PathMaker.getFilePath(homeDir, "log4j.properties")).exists()) {
			logConfigPath = PathMaker.getFilePath(homeDir, "log4j.properties");
		} else if (StringUtil.isNotBlank(propertyFile) && new File(propertyFile).exists()) {
			logConfigPath = propertyFile;
		}

		if (!StringUtil.isBlank(logConfigPath)) {
			final File file = new File(logConfigPath);
			if (!file.getParentFile().exists())
				file.mkdirs();

			System.setProperty("java.util.logging.config.file", file.getAbsolutePath());
			LogManager.getLogManager().readConfiguration(new FileInputStream(file));
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
		if (rootConfig == null){
			try {
				init(XMLConfig.BLANK) ;
			} catch (SecurityException e) {
				throw new InstanceCreationException(e) ;
			} catch (FileNotFoundException e) {
				throw new InstanceCreationException(e) ;
			} catch (SQLException e) {
				throw new InstanceCreationException(e) ;
			} catch (IOException e) {
				throw new InstanceCreationException(e) ;
			}
		}
		
		final SectionService section = SectionFactory.parse(this, sectionName, xmlConfig) ;
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

	static Aradon getCurrent() {
		return CURRENT;
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
		if (! isStarted()) start() ;
		
		Response response = handle(request);
		if (!response.getStatus().isSuccess())
			throw new ResourceException(response.getStatus(), response.toString());

		try {
			Object resultObj = ((ObjectRepresentation)response.getEntity()).getObject() ;
			return resultClass.cast(resultObj) ;
		} catch (IOException e) {
			throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST, e.getMessage());
		}
	}

	public ConverterService getConverterService(){
		ConverterService result = rootContext.getAttributeObject(ConverterService.class.getCanonicalName(), ConverterService.class) ;
		if (result == null){
			rootContext.putAttribute(ConverterService.class.getCanonicalName(), new ConverterService()) ;
			return getConverterService() ;
		} else {
			return result ;
		}
	}
	
	public MetadataService getMetadataService(){
		MetadataService result = rootContext.getAttributeObject(MetadataService.class.getCanonicalName(), MetadataService.class) ;
		if (result == null){
			rootContext.putAttribute(MetadataService.class.getCanonicalName(), new MetadataService()) ;
			return getMetadataService() ;
		} else {
			return result ;
		}
	}

	public boolean containsSection(String sectionName) {
		return sections.containsKey(sectionName);
	}

	public Engine getEngine() {
		return Engine.getInstance() ;
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
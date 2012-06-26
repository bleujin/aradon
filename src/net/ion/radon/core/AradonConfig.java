package net.ion.radon.core;

import static net.ion.radon.core.RadonAttributeKey.IZONE_ATTRIBUTE_KEY;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;

import net.ion.framework.util.Debug;
import net.ion.framework.util.FileUtil;
import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.PathMaker;
import net.ion.framework.util.StringUtil;
import net.ion.framework.util.ZipUtil;
import net.ion.radon.core.EnumClass.IZone;
import net.ion.radon.core.EnumClass.PlugInApply;
import net.ion.radon.core.config.AradonConstant;
import net.ion.radon.core.config.ConnectorConfig;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.let.FilterUtil;
import net.ion.radon.core.service.GroovyShellService;
import net.ion.radon.impl.section.PluginConfig;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.SystemUtils;
import org.restlet.data.Reference;

public class AradonConfig {

	private GroovyShellService gshell;
	private XMLConfig config;
	private List<PluginConfig> pconfigs = ListUtil.newList() ;
	
	private AradonConfig(XMLConfig config) {
		this.config = config;
	}

	public static AradonConfig create(XMLConfig config) throws ConfigurationException {
		try {
			AradonConfig result = new AradonConfig(config);
			if (StringUtil.isBlank(System.getProperty(AradonConstant.HOME_DIR))) {
				System.setProperty("aradon." + result.getId() + ".home.dir", new File(".").getCanonicalPath());
			}
			return result;
		} catch (IOException ex) {
			throw new ConfigurationException(ex);
		}

	}

	String getContactEmail() {
		return config.findChild("context.attribute", "id", RadonAttributeKey.LET_CONTACT_EMAIL).getElementValue();
	}

	String getHomeRef() {
		return config.findChild("context.attribute", "id", RadonAttributeKey.LET_CONTACT_HELP_DOC).getElementValue();
	}

	ConnectorConfig getConnectorConfig(int port) throws ConfigurationException {
		XMLConfig connConfig = config.firstChild("server-config.connector-config");
		return ConnectorConfig.create(connConfig, port);
	}


	AradonConfig init(Aradon aradon, TreeContext rootContext) throws ConfigurationException, InstanceCreationException{
		initConfig(aradon, rootContext);
		initLog();
		return this ;
	}
	
	
	private void initConfig(final Aradon aradon, TreeContext rootContext) throws InstanceCreationException, ConfigurationException {

		aradon.getStatusService().setContactEmail(getContactEmail());
		aradon.getStatusService().setHomeRef(new Reference(getHomeRef()));

		// at this point, the default class loader has all the jars you indicated

		loadClassPath();

		rootContext.putAttribute(IZONE_ATTRIBUTE_KEY, IZone.Application);
		File currentDir = new File("./");
		initConfig(aradon, currentDir, getXMLConfig(), rootContext);
		Debug.info("ROOT loaded");
		for (String configPath : childConfigPath()) {
			File file = new File(configPath);
			if (!file.exists()) {
				Debug.warn(file.getAbsolutePath() + " not exists. ignored");
				continue;
			}
			XMLConfig childConfig = XMLConfig.create(file);
			initConfig(aradon, currentDir, childConfig, rootContext);
			Debug.info(configPath + " parsed");
		}

		aradon.setContext(rootContext);
	}
	
	private TreeContext initConfig(Aradon aradon, File baseDir, XMLConfig config, TreeContext context) throws ConfigurationException, InstanceCreationException {

		AttributeUtil.load(aradon, config);
		FilterUtil.setFilter(aradon, config);

		List<XMLConfig> sections = config.children("section");
		sections.addAll(config.children("notification"));
		for (XMLConfig sconfig : sections) {
			aradon.attach(sconfig.getAttributeValue("name"), sconfig);
		}

		return context;
	}
	

	private void initLog() throws ConfigurationException {

		String logConfigPath = null;
		String homeDir = SystemUtils.getUserDir().getAbsolutePath();
		final String propertyFile = System.getProperty("java.util.logging.config.file");
		final String configuredFile = config.getString("server-config.log-config-file");

		if (StringUtil.isNotBlank(configuredFile) && new File(configuredFile).exists()) {
			logConfigPath = configuredFile;
		} else if (new File(PathMaker.getFilePath(homeDir, "log4j.properties")).exists()) {
			logConfigPath = PathMaker.getFilePath(homeDir, "log4j.properties");
		} else if (StringUtil.isNotBlank(propertyFile) && new File(propertyFile).exists()) {
			logConfigPath = propertyFile;
		}

		try {
			if (!StringUtil.isBlank(logConfigPath)) {
				final File file = new File(logConfigPath);
				if (!file.getParentFile().exists())
					file.mkdirs();

				System.setProperty("java.util.logging.config.file", file.getAbsolutePath());
				LogManager.getLogManager().readConfiguration(new FileInputStream(file));
			}
		} catch (IOException ex) {
			throw new ConfigurationException(ex);
		}
	}
	
	
	void launchShell(final Aradon aradon) {
		if (gshell != null)
			return;

		boolean autostart = Boolean.valueOf(config.getString("server-config.manage-port[@auto-start]", "false"));
		if (autostart == false) {
			aradon.getLogger().warning("aradon manage shell not loaded");
			return;
		}
		final int managePort = config.getInt("server-config.manage-port", 4567);
		aradon.getLogger().warning("aradon manage shell port :" + managePort);

		Thread shellThread = new Thread() {
			public void run() {
				gshell = new GroovyShellService(managePort);
				Map bindings = MapUtil.create("aradon", aradon);
				gshell.setBindings(bindings);
				gshell.launch();
			}
		};
		shellThread.start();
	}

	XMLConfig getXMLConfig() {
		return config;
	}

	void stopShell() {
		if (gshell != null)
			gshell.destroy();
	}

	private List<String> childConfigPath = ListUtil.newList() ;
	List<String> childConfigPath(){
		return Collections.unmodifiableList(childConfigPath) ;
	}
	
	void loadClassPath() throws ConfigurationException{
		ClassAppender appender = ClassAppender.create() ;
		loadClassPath(getXMLConfig(), appender);
		appender.invokeURL();
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
				Debug.line("ClassLoader:", paths) ;
			}
		} catch (MalformedURLException ex) {
			throw new ConfigurationException(ex);
		}
	}
	
	
	public String getId() {
		return StringUtil.defaultIfEmpty(config.getString("server-config[@id]"), "mercury");
	}

	void addPlugInInfo(File xmlConfigFile, XMLConfig pxconfig) throws ConfigurationException, IOException {
		File pluginHomeDir = xmlConfigFile.getParentFile().getParentFile() ;
		PluginConfig pconfig = PluginConfig.create(pxconfig) ;
		pconfigs.add(pconfig) ;
		System.setProperty("aradon." + getId() + "[" + pconfig.getId() + "].home.dir", pluginHomeDir.getCanonicalPath()) ;
	}
	
	public List<PluginConfig> getPlugInConfigs(){
		return Collections.unmodifiableList(pconfigs) ;
	}

	
	public File findAradonFile(String remainPath){
		String homeDir = System.getProperty("aradon." + getId() + ".home.dir") ;
		return new File(PathMaker.getFilePath(homeDir, remainPath)) ;
	}
	
	public File findPlugInFile(String plugInId, String remainPath){
		String homeDir = System.getProperty("aradon." + getId() + "[" + plugInId + "].home.dir") ;
		if (StringUtil.isBlank(homeDir)) throw new IllegalArgumentException(plugInId + " : not found plugIn") ;
		return new File(PathMaker.getFilePath(homeDir, remainPath)) ;
	}
	
	
	void loadPlugIn(Aradon aradon) throws ConfigurationException, InstanceCreationException, IOException {
		new PlugInDeployer().loadPlugIn(aradon) ;
	}

	static FileFilter DIR_FILTER = new DirFilter();
	private static FileFilter PLUGIN_CONFIG_FILE_FILTER = new configFilter();
	private static FileFilter JAR_FILTER = new jarFilter();
	
	private final static class configFilter implements FileFilter {
		public boolean accept(File file) {
			return file.getName().equals("aradon-config.xml");
		}
	}

	private final static class DirFilter implements FileFilter {
		public boolean accept(File file) {
			return file.isDirectory();
		}
	}

	private final static class jarFilter implements FileFilter {
		public boolean accept(File file) {
			return file.isFile() && file.getName().endsWith(".jar");
		}
	}

	
	private static class PlugInDeployer {
		private PlugInDeployer(){
		}
		
		void loadPlugIn(Aradon aradon) throws ConfigurationException, InstanceCreationException, IOException {
			AradonConfig aconfig = aradon.getConfig() ;
			
			String parentDir = StringUtil.defaultIfEmpty(System.getProperty(Aradon.HOME_DIR), "./") ;
			String plugInDir = StringUtil.defaultIfEmpty(aconfig.getXMLConfig().getString("plugin[@home]"), "plugin");
			String plugInFullPath = PathMaker.getFilePath((plugInDir.startsWith("/") ? "" : parentDir) , plugInDir) ;
			
			
			File plugInFileDir = new File(plugInFullPath);
			if (!(plugInFileDir.exists()) || !(plugInFileDir.isDirectory()))
				return;

			extractPluginZip(plugInFileDir);

			List<File> configFiles = ListUtil.newList();
			ClassAppender appender = ClassAppender.create();

			File[] plugDirs = plugInFileDir.listFiles(DIR_FILTER);
			for (File plugDir : plugDirs) {

				File[] foundConfigFiles = FileUtil.findFiles(plugDir, PLUGIN_CONFIG_FILE_FILTER, true);
				CollectionUtils.addAll(configFiles, foundConfigFiles);

				File[] jars = FileUtil.findFiles(plugDir, JAR_FILTER, true);
				appender.appendJar(jars);
			}
			appender.invokeURL();

			for (File xmlFile : configFiles) {
				appendPlugin(aradon, xmlFile, XMLConfig.create(xmlFile));
			}
		}

		private void appendPlugin(Aradon aradon, File xmlConfigFile, XMLConfig pconfig) throws ConfigurationException, InstanceCreationException, IOException {
			// plugins.put(pluginName, pconfig);

			AttributeUtil.load(aradon, pconfig);
			FilterUtil.setFilter(aradon, pconfig);

			List<XMLConfig> sections = pconfig.children("section");
			for (XMLConfig sconfig : sections) {
				aradon.attach(SectionFactory.createSection(aradon, sconfig.getAttributeValue("name"), sconfig), PlugInApply.create(pconfig.getString("plugin[@apply]")));
			}
			aradon.getConfig().addPlugInInfo(xmlConfigFile, pconfig);
			// return this.rootContext;
		}

		private void extractPluginZip(File plugDir) throws IOException {
			ZipUtil zipper = new ZipUtil();
			File[] zipfiles = plugDir.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return file.getName().endsWith(".zip");
				}
			});

			for (File zip : zipfiles) {
				final String name = StringUtil.substringBeforeLast(zip.getName(), ".");
				File dir = new File(PathMaker.getFilePath(plugDir.getPath(), name));
				if ((!dir.exists()) || isNewer(zip, dir)) {
					zipper.unzip(zip, PathMaker.getFilePath(plugDir.getPath(), name));
				}
			}
		}

		private boolean isNewer(File zip, File dir) {
			return zip.lastModified() > dir.lastModified();
		}
		
	}
}

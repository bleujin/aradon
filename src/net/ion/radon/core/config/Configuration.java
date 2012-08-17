package net.ion.radon.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;
import java.util.logging.LogManager;

import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.PathMaker;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.TreeContext;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.SystemUtils;

public class Configuration {
	private final AradonConfiguration aradonConfig ;
	private final ServerConfiguration serverConfig ;
	private final PlugInsConfiguration pluginConfig ;
	
	Configuration(AradonConfiguration aradonConfig, ServerConfiguration serverConfig, PlugInsConfiguration pluginConfig) {
		this.aradonConfig = aradonConfig ;
		this.serverConfig = serverConfig ;
		this.pluginConfig = pluginConfig ;
	}
	
	public AradonConfiguration aradon() {
		return aradonConfig ;
	}
	
	public ServerConfiguration server(){
		return serverConfig ;
	}

	public PlugInsConfiguration plugin() {
		return pluginConfig;
	}

	public void init(Aradon aradon, TreeContext rootContext) throws ConfigurationException, InstanceCreationException{
		initConfig(aradon, rootContext);
		initLog();
	}
	
	
	private void initConfig(final Aradon aradon, TreeContext rootContext) throws InstanceCreationException, ConfigurationException {

//		aradon.getStatusService().setContactEmail(aradonConfig.contactEmail());
//		aradon.getStatusService().setHomeRef(aradonConfig.homeRef());
//
//		// at this point, the default class loader has all the jars you indicated
//		loadClassPath();
//
//		rootContext.putAttribute(IZONE_ATTRIBUTE_KEY, IZone.Application);
//		File currentDir = new File("./");
//		initConfig(aradon, currentDir, getXMLConfig(), rootContext);
//		Debug.info("ROOT loaded");
//		for (String configPath : childConfigPath()) {
//			File file = new File(configPath);
//			if (!file.exists()) {
//				Debug.warn(file.getAbsolutePath() + " not exists. ignored");
//				continue;
//			}
//			XMLConfig childConfig = XMLConfig.create(file);
//			initConfig(aradon, currentDir, childConfig, rootContext);
//			Debug.info(configPath + " parsed");
//		}
//
//		aradon.setContext(rootContext);
	}
	

	private void initLog() throws ConfigurationException {

		String logConfigPath = null;
		String homeDir = SystemUtils.getUserDir().getAbsolutePath();
		final String propertyFile = System.getProperty("java.util.logging.config.file");
		final String configuredFile = serverConfig.logConfigPath();

		if (StringUtil.isNotBlank(configuredFile) && new File(configuredFile).exists()) {
			logConfigPath = configuredFile;
		} else if (new File(PathMaker.getFilePath(homeDir, "log4j.properties")).exists()) {
			logConfigPath = PathMaker.getFilePath(homeDir, "log4j.properties");
		} else if (StringUtil.isNotBlank(propertyFile) && new File(propertyFile).exists()) {
			logConfigPath = propertyFile;
		}

		FileInputStream fis = null ;
		try {
			if (!StringUtil.isBlank(logConfigPath)) {
				final File file = new File(logConfigPath);
				if (!file.getParentFile().exists())
					file.mkdirs();

				System.setProperty("java.util.logging.config.file", file.getAbsolutePath());
				fis = new FileInputStream(file);
				
				LogManager.getLogManager().readConfiguration(fis);
			}
		} catch (IOException ex) {
			throw new ConfigurationException(ex);
		} finally {
			IOUtil.closeQuietly(fis) ;
//			LogManager.getLogManager().reset() ;
		}
	}
	
//	void loadClassPath() throws ConfigurationException{
//		ClassAppender appender = ClassAppender.create() ;
//		loadClassPath(getXMLConfig(), appender);
//		appender.invokeURL();
//	}
	
	private List<String> childConfigPath = ListUtil.newList() ;
	List<String> childConfigPath(){
		return Collections.unmodifiableList(childConfigPath) ;
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

	public static ConfigurationBuilder newBuilder() {
		return new ConfigurationBuilder() ;
	}
	
}

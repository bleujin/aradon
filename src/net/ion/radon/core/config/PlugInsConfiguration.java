package net.ion.radon.core.config;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ion.framework.util.Debug;
import net.ion.framework.util.FileUtil;
import net.ion.framework.util.InstanceCreationException;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.PathMaker;
import net.ion.framework.util.StringUtil;
import net.ion.framework.util.ZipUtil;
import net.ion.radon.core.Aradon;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.ConfigurationException;

public class PlugInsConfiguration {

	private String aradonId;
	private final String homePath ;
	private final List<String> paths ;
	private List<PlugInConfiguration> pconfigs = ListUtil.newList() ;
	
	PlugInsConfiguration(String aradonId, String homePath, List<String> paths) {
		this.aradonId = aradonId ;
		this.homePath = StringUtil.defaultIfEmpty(homePath, "") ;
		this.paths = paths ;
		
		init() ;
	}

	public PlugInsConfiguration init() {
		try {
			new PlugInDeployer(homePath).loadPlugIn() ;
		} catch(IOException ex){
			throw new IllegalStateException(ex) ;
		} catch (ConfigurationException ex) {
			throw new IllegalStateException(ex) ;
		} catch (InstanceCreationException ex) {
			throw new IllegalStateException(ex) ;
		}
		
		return this ;
	}
	
	public File findAradonFile(String remainPath){
		String homeDir = System.getProperty("aradon." + aradonId + ".home.dir") ;
		return new File(PathMaker.getFilePath(homeDir, remainPath)) ;
	}

	public File findPlugInFile(String plugInId, String remainPath){
		String homeDir = System.getProperty("aradon." + aradonId + "[" + plugInId + "].home.dir") ;
		if (StringUtil.isBlank(homeDir)) throw new IllegalArgumentException(plugInId + " : not found plugIn") ;
		return new File(PathMaker.getFilePath(homeDir, remainPath)) ;
	}

	public List<SectionConfiguration> sections() {
		List<SectionConfiguration> result = ListUtil.newList() ;
		
		for (PlugInConfiguration pc : pconfigs) {
			result.addAll(pc.getAradonConfig().sections().sections()) ; 
		}
		
		return result;
	}

	
	void addPlugInInfo(File xmlConfigFile, XMLConfig pxconfig) throws ConfigurationException, InstanceCreationException {
//		File pluginHomeDir = xmlConfigFile.getParentFile().getParentFile() ;
//		PlugInConfiguration pconfig = PlugInConfiguration.fromLoad(pxconfig) ;
//		pconfigs.add(pconfig) ;
//		
//		System.setProperty("aradon." + aradonId + "[" + pconfig.getId() + "].home.dir", pluginHomeDir.getCanonicalPath()) ;
	}
	

	public List<PlugInConfiguration> plugins() {
		return pconfigs;
	}
	
	
	private class PlugInDeployer {
		private String homePath ;
		private PlugInDeployer(String homePath){
			this.homePath = homePath ;
		}
		
		void loadPlugIn() throws IOException, ConfigurationException, InstanceCreationException  {
//			AradonConfig aconfig = aradon.getConfig() ;
			
			String parentDir = StringUtil.defaultIfEmpty(System.getProperty(Aradon.HOME_DIR), "./") ;
			
			System.setProperty("aradon." + aradonId +".home.dir", new File(parentDir).getCanonicalPath()) ;
			
			String plugInDir =  StringUtil.defaultIfEmpty(homePath, "plugin"); ;
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
				appendPlugin(xmlFile);
			}
			
		}

		private void appendPlugin(File xmlConfigFile) throws ConfigurationException, InstanceCreationException, IOException  {
			// plugins.put(pluginName, pconfig);
			XMLConfig xconfig = XMLConfig.create(xmlConfigFile) ;
			File pluginHomeDir = xmlConfigFile.getParentFile().getParentFile() ;
			PlugInConfiguration piconfig = PlugInConfiguration.fromLoad(xconfig) ;

			PlugInsConfiguration.this.pconfigs.add(piconfig) ;
			System.setProperty("aradon." + aradonId + "[" + piconfig.getId() + "].home.dir", pluginHomeDir.getCanonicalPath()) ;
			
			// addPlugInInfo(xmlConfigFile, xconfig);
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

	public Map<String, AttributeValue> attributes() {
		Map<String, AttributeValue> result = MapUtil.newMap() ;
		
		for (PlugInConfiguration pc : pconfigs) {
			result.putAll(pc.getAradonConfig().attributes()) ; 
		}
		
		return result;
	}



}

class ClassAppender {

	private Set<URL> targets = new HashSet<URL>();

	private ClassAppender() {
	}
	
	final static ClassAppender create(){
		return new ClassAppender();
	}
	 
	void invokeURL() throws ConfigurationException {
		try {
			Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
			addURL.setAccessible(true);// you're telling the JVM to override the default visibility
			ClassLoader cl = ClassLoader.getSystemClassLoader();
			for (URL url : targets.toArray(new URL[0])) {
				addURL.invoke(cl, new Object[] { url });
			}
		} catch (SecurityException ex) {
			throw new ConfigurationException(ex.getMessage(), ex) ;
		} catch (IllegalArgumentException ex) {
			throw new ConfigurationException(ex.getMessage(), ex) ;
		} catch (IllegalAccessException ex) {
			throw new ConfigurationException(ex.getMessage(), ex) ;
		} catch (InvocationTargetException ex) {
			throw new ConfigurationException(ex.getMessage(), ex) ;
		} catch (NoSuchMethodException ex) {
			throw new ConfigurationException(ex.getMessage(), ex) ;
		}
	}

	void appendPath(String[] paths) throws MalformedURLException {
		for (String path : paths) {
			final File file = new File(path);
			if (!file.exists()) {
				Debug.warn(file + " not exists");
				continue;
			}
			
			List<File> jarFileList = ListUtil.newList() ; 
			findJarFiles(file, jarFileList) ;
			File[] jars = jarFileList.toArray(new File[0]) ;
			
			List<String> jarsFileName = new ArrayList<String>();
			for (File jarfile : jars) {
				jarsFileName.add(jarfile.getAbsolutePath());
			}

			appendJar(jarsFileName.toArray(new String[0]));
			targets.add(file.toURL());
		}
	}

	private void findJarFiles(File file, List<File> jarFileList) {
		File[] jarFiles = file.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return ! file.isDirectory() &&  file.getName().endsWith(".jar");
			}
		});
		jarFileList.addAll(Arrays.asList(jarFiles)) ;
		File[] dirs = file.listFiles(PlugInsConfiguration.DIR_FILTER);
		for (File dir : dirs) {
			findJarFiles(dir, jarFileList) ;
		}
	}

	void appendJar(String[] jars) throws MalformedURLException {
		for (String jarpath : jars) {
			File file = new File(jarpath);
			if (!file.exists()) {
				Debug.warn(file + " not exists");
				continue;
			}
			targets.add(new URL("jar:file:" + file.getAbsolutePath() + "!/"));
			Debug.line("load:" + new URL("jar:file:" + file.getAbsolutePath() + "!/")) ;
		}
	}
	
	void appendJar(File[] jars) throws MalformedURLException {
		for (File file : jars) {
			targets.add(new URL("jar:file:" + file.getAbsolutePath() + "!/"));
		}
	}
}

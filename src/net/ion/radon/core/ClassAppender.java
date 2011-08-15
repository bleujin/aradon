package net.ion.radon.core;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.radon.ARadonServer;

import org.apache.commons.configuration.ConfigurationException;

public class ClassAppender {

	private Set<URL> targets = new HashSet<URL>();

	ClassAppender() {
	}
	
	public final static ClassAppender create(){
		return new ClassAppender();
	}
	 
	public void invokeURL() throws ConfigurationException {
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

	public void appendPath(String[] paths) throws MalformedURLException {
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
		File[] dirs = file.listFiles(ARadonServer.DIR_FILTER);
		for (File dir : dirs) {
			findJarFiles(dir, jarFileList) ;
		}
	}

	public void appendJar(String[] jars) throws MalformedURLException {
		for (String jarpath : jars) {
			File file = new File(jarpath);
			if (!file.exists()) {
				Debug.warn(file + " not exists");
				continue;
			}
			targets.add(new URL("jar:file:" + file.getAbsolutePath() + "!/"));
		}
	}
	
	public void appendJar(File[] jars) throws MalformedURLException {
		for (File file : jars) {
			targets.add(new URL("jar:file:" + file.getAbsolutePath() + "!/"));
		}
	}
}

package net.ion.radon.core.script;

import groovy.lang.GroovyClassLoader;
import groovy.util.GroovyScriptEngine;

import java.io.File;
import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import net.ion.radon.core.filter.IRadonFilter;

public class ScriptFactory {

	private static GroovyClassLoader loader = null;

	private ScriptFactory(){
	}
	
	public static GroovyClassLoader groovyLoader() {
		if (loader == null) {
			ClassLoader parent = ScriptFactory.class.getClassLoader();
			loader = new GroovyClassLoader(parent);
		}
		return loader;
	}
	
	
	public static IRadonFilter createFilter(String lang, File file) throws IOException{
		
		if ("groovy".equalsIgnoreCase(lang)){
			GroovyScriptEngine gse = new GroovyScriptEngine(new String[]{file.getParent()}, IRadonFilter.class.getClassLoader()) ;
			return new GroovyLetFilter(gse, file.getName()) ;
		} else {
			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName(lang);
			
			return new ScriptLetFilter(engine, file) ;
		}
		
	}

	public static IRadonFilter createGroovyFilter(File file) throws IOException {
		return createFilter("groovy", file);
	}

	public static IRadonFilter createRhinoFilter(File file) throws IOException {
		return createFilter("javascript", file);
	} 
	
	
}

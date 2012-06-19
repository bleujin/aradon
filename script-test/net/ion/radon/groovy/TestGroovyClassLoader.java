package net.ion.radon.groovy;

import java.io.File;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import junit.framework.TestCase;
import net.ion.bulletin.HelloWorld;
import net.ion.framework.util.Debug;
import net.ion.radon.core.script.ScriptFactory;

public class TestGroovyClassLoader extends TestCase{

	public void testGroovy() throws Exception {
		HelloWorld h = new HelloWorld() ;
		h.setName("bleujin") ;
		
		Debug.debug(h.hi()) ;
	}
	
	public void testCreateScriptEngine() throws Exception {
		for (int i = 0; i < 1; i++) {
			// GroovyScriptEngine engine = new GroovyScriptEngine(".") ;
			ClassLoader parent = ScriptFactory.class.getClassLoader();
			GroovyClassLoader loader = new GroovyClassLoader(parent);
			// Class gclass = loader.parseClass(new File("./groovy-test/groovy/ChartFilter.groovy"));
		}
	}
	
	public void testRun() throws Exception {
		long start = System.nanoTime() ;
		GroovyScriptEngine engine = new GroovyScriptEngine(".") ;
		Debug.line("create engine", System.nanoTime() - start); 

		final Binding binding = new Binding();
		//binding.setProperty("limit", 10) ;

		Debug.line("set binding", System.nanoTime() - start);
		
		for (int i = 0; i < 10 ; i++) {
			runScript(engine, binding);
		}
		
		Debug.line("run", System.nanoTime() - start);
	}

	private void runScript(GroovyScriptEngine engine, final Binding binding) throws ResourceException, ScriptException {
		engine.run("groovy/net/ion/bulletin/Fibonacci.groovy", binding) ;
	}
	
	public void testSpeed() throws Exception {
		Thread[] ths = new Thread[10] ;
		
		final GroovyScriptEngine engine = new GroovyScriptEngine(".") ;
		
		for (int i = 0; i < ths.length; i++) {
			ths[i] = new Thread(){
				public void run(){
					try {
						for (int j = 0; j < 10 ; j++) {
							final Binding binding = new Binding();
							binding.setProperty("limit", 10) ;
							engine.run("groovy/net/ion/bulletin/Fibonacci.groovy", binding) ;
						}
					} catch (ResourceException e) {
						e.printStackTrace();
					} catch (ScriptException e) {
						e.printStackTrace();
					}
				}
			} ;
		}
		
		for (int i = 0; i < ths.length; i++) {
			ths[i].start() ;
		}
		
		for (int i = 0; i < ths.length; i++) {
			ths[i].join() ;
		}
	}
	
	
	
	
	
	
	
	
	
	
}

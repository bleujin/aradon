package net.ion.bulletin;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import net.ion.framework.util.Debug;

public class HelloShell {

	public static void main(String[] args) throws Exception {
		GroovyShell shell = new GroovyShell() ;

		for (int i = 0; i < 1; i++) {
			Object result = shell.evaluate("" +
					"def mass = 22.3 \n" +
					"def velocity = 10.6 \n" +
					"mass * velocity**2 / 2" + 
					"") ;
			Debug.debug(result) ;
		}
		
		
		GroovyScriptEngine engine = new GroovyScriptEngine(".") ;
		Binding binding = new Binding();
		binding.setProperty("limit", 10) ;
		Debug.line(engine.run("groovy/net/ion/bulletin/Fibonacci.groovy", binding)) ;
		
	}
}

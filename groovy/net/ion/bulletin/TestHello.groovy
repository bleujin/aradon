package net.ion.bulletin;

import groovy.util.GroovyTestCase;
import org.restlet.Request; 

class TestHello extends GroovyTestCase {

	public void testScriptEngine() throws Exception {
		String root = "." ;
		GroovyScriptEngine engine = new GroovyScriptEngine(root) ;
		Binding binding = new Binding();
		binding.setVariable("name", "greeting") ;
		binding.setVariable("request", new Request(Method.GET, "http://localhost:9002/"));

		engine.run ("./resource/groovy/HelloWorld.groovy", binding)
		
		// Debug.line result, result.getResult()
	}
}

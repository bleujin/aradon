package net.ion.bulletin;

import groovy.lang.Binding;
import net.ion.framework.util.Debug;
import net.ion.radon.core.filter.IFilterResult;

import org.restlet.Request 
import org.restlet.data.Method;

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

package net.ion.radon.script;

import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import junit.framework.TestCase;
import net.ion.framework.db.DBController;
import net.ion.framework.db.IDBController;
import net.ion.framework.db.Rows;
import net.ion.framework.db.manager.DBManager;
import net.ion.framework.db.manager.OracleDBManager;
import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;

import org.mozilla.javascript.Context;

public class TestScript extends TestCase {

	public void testHello() throws Exception {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");

		// Case 1
		engine.eval("print('Hello, World')");

		// Case 2
		Object result = engine.eval("5+4");
		Debug.debug(result, result.getClass());

		// Case 3
		mgr.put("op1", 4);
		Bindings bind = engine.createBindings();
		bind.put("op2", 5);

		Debug.debug(engine.eval("op1 + op2", bind));

	}

	public void testBindingScope() throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");

		engine.put("x", "hello");
		// print global variable "x"
		engine.eval("println(x);");
		// the above line prints "hello"

		// Now, pass a different script context
		ScriptContext newContext = new SimpleScriptContext();
		Bindings engineScope = newContext.getBindings(ScriptContext.ENGINE_SCOPE);

		// add new variable "x" to the new engineScope
		engineScope.put("x", "world");

		// execute the same script - but this time pass a different script context
		engine.eval("println(x);", newContext);
		// the above line prints "world"

	}

	public void testRunnableImpl() throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");

		// JavaScript code in a String
		String script = "function run() { print('run called'); }";

		// evaluate script
		engine.eval(script);

		Invocable inv = (Invocable) engine;

		// get Runnable interface object from engine. This interface methods
		// are implemented by script functions with the matching name.
		Runnable r = (Runnable) inv.getInterface(Runnable.class);

		// start a new thread that runs the script implemented
		// runnable interface
		Thread th = new Thread(r);
		th.start();
	}

	public void testCreateScriptEngine() throws Exception {

		ScriptEngineManager mgr = new ScriptEngineManager();

		long start = System.currentTimeMillis();
		Reader reader = new FileReader("./resource/rhino/binding.js");
		final String content = IOUtil.toString(reader);

		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		Bindings bind = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		bind.put("my_var2", 4);

		for (int i = 0; i < 1000; i++) {

			bind.put("my_var1", i % 10);
			StringReader sreader = new StringReader(content);
			Object result = engine.eval(sreader);
			Debug.debug(result, engine instanceof Compilable);
		}

		Debug.line(System.currentTimeMillis() - start);

	}

	public void testBinding() throws Exception {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("javascript");
		Bindings bind = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		bind.put("my_var2", 4);

		// Reader reader = new FileReader("./resource/rhino/binding.js");
		// String str = IOUtil.toString(reader) ;
		// engine.eval(str, bind);

		Reader reader = new FileReader("./resource/rhino/binding.js");
		String content = IOUtil.toString(reader);

		for (int i = 0; i < 10; i++) {
			bind.put("my_var1", i);
			StringReader sreader = new StringReader(content);
			Object result = engine.eval(sreader);
			Debug.debug(result, engine instanceof Compilable);
		}

		// engine.eval("println(my_var1 + my_var2)", bind) ;

	}

	public void testResultSet() throws Exception {
		DBManager dbm = new OracleDBManager("jdbc:oracle:thin:@dev-test.i-on.net:1521:devTest", "bleu", "redf");
		IDBController dc = new DBController(dbm);
		dc.initSelf();

		try {
			Rows rows = dc.getRows("select * from emp_sample");

			ScriptEngineManager mgr = new ScriptEngineManager();
			ScriptEngine engine = mgr.getEngineByName("javascript");

			Bindings bind = engine.createBindings();
			bind.put("rows", rows);

			engine.setBindings(bind, ScriptContext.ENGINE_SCOPE);

			Reader reader = new FileReader("./resource/rhino/resultset.js");
			engine.eval(reader);

		} finally {
			dc.destroySelf();
		}
	}

	public void testGroovy() throws Exception {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("Groovy");

		engine.eval("println 'Hello, World'");
	}

	public void testEngineFactory() throws Exception {
		ScriptEngineManager manager = new ScriptEngineManager();
		List<ScriptEngineFactory> engines = manager.getEngineFactories();

		Debug.debug("The following " + engines.size() + " scripting engines were found");

		for (ScriptEngineFactory engine : engines) {
			Debug.debug("Engine name: " + engine.getEngineName());
			Debug.debug("\tVersion: " + engine.getEngineVersion());
			Debug.debug("\tLanguage: " + engine.getLanguageName());
			Debug.debug("\tExtensions: " + engine.getExtensions());
			Debug.debug("\tshortNames: " + engine.getNames());
			Debug.debug("\tthread: " + engine.getParameter("THREADING"));
		}

	}

	// can run with jdk1.6
	public void testCompile() throws Exception {
		ScriptContext sc = new SimpleScriptContext();

		Map<String, CompiledScript> m = new HashMap<String, CompiledScript>();
		// ...
		Context.enter();
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByExtension("js");
		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		bindings.put("num", "20");
		if (engine instanceof Compilable) {
			CompiledScript script = m.get("fib");
			if (script == null) {
				Compilable compilingEngine = (Compilable) engine;
				script = compilingEngine.compile(
						"fib(num);" + "function fib(n) {" + 
						"  if(n <= 1) return n; " + 
						"  return fib(n-1) + fib(n-2); " + 
						"};");
				m.put("fib", script);
			}
			script.eval(bindings);
		}
	}

	public void testThreadSafe() throws Exception {

		ScriptEngineManager mgr = new ScriptEngineManager();

		long start = System.currentTimeMillis();
		Reader reader = new FileReader("./resource/rhino/thread_binding.js");
		final String content = IOUtil.toString(reader);

		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		StdThread[] ct = new StdThread[5] ;
		for (int i = 0; i < ct.length; i++) {
			ct[i] = new StdThread(mgr, engine, content) ;
		}
		
		for (int i = 0; i < ct.length; i++) {
			ct[i].start() ;
		}
		for (int i = 0; i < ct.length; i++) {
			ct[i].join() ;
		}
		Debug.line(System.currentTimeMillis() - start);
	}
}

class StdThread extends Thread {
	private ScriptEngineManager mgr ;
	private ScriptEngine engine;
	private String content;

	StdThread(ScriptEngineManager mgr, ScriptEngine engine, String content) {
		this.mgr = mgr ;
		this.engine = engine;
		this.content = content;
	}

	public void run() {
		// this.engine = mgr.getEngineByName("js") ;
		Bindings bind = engine.createBindings();
		try {
			for (int i = 0; i < 1000; i++) {
				final int myval = i % 10;
				bind.put("my_var1", myval);
				engine.setBindings(bind, ScriptContext.ENGINE_SCOPE) ;
				
				StringReader sreader = new StringReader(content);
				Object result = engine.eval(sreader);
				Debug.line((Integer)result -  myval) ;
			}
		} catch (ScriptException e) {
			e.printStackTrace();
		}

	}

}

package net.ion.radon.core.script;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;
import java.io.IOException;

import net.ion.framework.util.Debug;
import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

import org.codehaus.groovy.control.CompilationFailedException;
import org.restlet.Request;
import org.restlet.Response;

public class GroovyRunFilter extends IRadonFilter {

	private GroovyObject gobject;

	private GroovyRunFilter(GroovyObject gobject) {
		this.gobject = gobject;
	}

	public final static GroovyRunFilter create(File sourceFile) throws CompilationFailedException, IOException, InstantiationException, IllegalAccessException {
		ClassLoader parent = GroovyRunFilter.class.getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);
		Class gclass = loader.parseClass(sourceFile);
		GroovyObject gobject = (GroovyObject) gclass.newInstance();

		return new GroovyRunFilter(gobject);
	}

	@Override
	public IFilterResult afterHandle(IService iservice, Request request, Response response) {
		return execute(iservice, request, response);
	}

	@Override
	public IFilterResult preHandle(IService iservice, Request request, Response response) {
		return execute(iservice, request, response);
	}

	private IFilterResult execute(IService iservice, Request request, Response response) {
		long start = System.currentTimeMillis();
		IFilterResult result = (IFilterResult) gobject.invokeMethod("run", new Object[] {iservice, request, response});
		Debug.line("Execute time", (System.currentTimeMillis() - start));
		return result;
	}

}

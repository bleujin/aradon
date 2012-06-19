package net.ion.radon.core.script;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import net.ion.framework.util.UTF8FileUtils;
import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

public class ScriptLetFilter extends IRadonFilter {

	private ScriptEngine engine;
	private Reader content;

	ScriptLetFilter(ScriptEngine engine, File file) throws IOException {
		this.engine = engine;
		this.content = readFile(file);
	}

	private Reader readFile(File file) throws IOException {
		return UTF8FileUtils.readFileToReader(file);
	}

	@Override
	public IFilterResult afterHandle(IService iservice, Request request, Response response) {
		try {
			Bindings binding = engine.getBindings(ScriptContext.ENGINE_SCOPE);
			binding.put("service", iservice);
			binding.put("request", request);
			binding.put("response", response);
			Object result;
			result = engine.eval(content);
			return (IFilterResult) result;
		} catch (ScriptException e) {
			throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e);
		}
	}

	@Override
	public IFilterResult preHandle(IService iservice, Request request, Response response) {
		return afterHandle(iservice, request, response);
	}

}

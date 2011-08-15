package net.ion.radon.core.script;

import groovy.lang.Binding;
import groovy.lang.Script;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import net.ion.framework.util.Debug;
import net.ion.radon.core.IService;
import net.ion.radon.core.EnumClass.FilterLocation;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;

import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;

public class GroovyLetFilter extends IRadonFilter{

	private GroovyScriptEngine gse;
	private String fileName ;

	GroovyLetFilter(GroovyScriptEngine gse, String fileName) {
		this.gse = gse;
		this.fileName = fileName ;
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
		try {
			Binding binding = new Binding() ;
			binding.setProperty("service", iservice) ;
			binding.setProperty("request", request) ;
			binding.setProperty("response", response) ;
			Script script = gse.createScript(fileName, binding);
			IFilterResult result = (IFilterResult) script.run() ; 

			return result;
		} catch (ResourceException e) {
			throw new org.restlet.resource.ResourceException(Status.SERVER_ERROR_INTERNAL, e) ;
		} catch (ScriptException e) {
			throw new org.restlet.resource.ResourceException(Status.SERVER_ERROR_INTERNAL, e) ;
		}

	}

}

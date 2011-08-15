package net.ion.radon.impl.let;

import org.restlet.data.Method;
import org.restlet.engine.resource.AnnotationUtils;
import org.restlet.engine.resource.VariantInfo;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.Search;
import org.restlet.resource.UniformResource;

import org.restlet.resource.ServerResource;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.radon.client.SubRequest;
import net.ion.radon.core.let.AbstractLet;
import net.ion.radon.param.TestBean;

public class ObjectLet extends ServerResource{
	
	@Get("xml")
	public TestBean toXML(TestBean tb){
		Debug.line(((VariantInfo) (getVariants().get(0)) ).getAnnotationInfo()) ;
		return tb ;
	}

	
	@Search
	public TestBean listMyBean(TestBean tb){
		tb.setQuery("SEARCH") ;
		return tb ;
	}

	
	@Put
	public TestBean putMyBean(TestBean tb){
		return tb ;
	}
	

}

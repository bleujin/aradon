package net.ion.radon.impl.let;

import net.ion.framework.util.Debug;
import net.ion.radon.param.TestBean;

import org.restlet.engine.resource.VariantInfo;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.Search;
import org.restlet.resource.ServerResource;

public class ObjectLet extends ServerResource{
	
	@Post 
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

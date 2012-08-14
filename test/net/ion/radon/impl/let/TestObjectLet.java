package net.ion.radon.impl.let;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.ion.framework.rest.StdObject;
import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.ISerialRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.param.TestBean;
import net.ion.radon.util.AradonTester;

import org.junit.Ignore;
import org.junit.Test;
import org.restlet.data.Method;
import org.restlet.engine.resource.VariantInfo;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.Search;
import org.restlet.resource.ServerResource;

public class TestObjectLet extends TestBaseAradon{

	@Test
	public void testgRIAPGet() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", ObjectLet.class).getAradon() ;

		AradonClient ac = AradonClientFactory.create(aradon) ;
		ISerialRequest request = ac.createSerialRequest("/test") ;
		TestBean param = new TestBean();
		param.setColor(ListUtil.toList("Red", "Blue")) ;
		TestBean tb = request.post(param, TestBean.class) ;
		
		assertEquals("Red", tb.getColor().get(0)) ;
	}
	

	@Test
	public void testObjectGet() throws Exception {
		Aradon aradon = testAradon() ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		ISerialRequest ar = ac.createSerialRequest("/?aradon.result.format=object");
		StdObject sto = ar.get(StdObject.class) ;
		
		assertTrue(sto != null) ;
	}
	
	@Test
	public void testObjectPut() throws Exception {
		Aradon aradon = testAradon() ;
		
		aradon.getChildService("another").attach(PathConfiguration.create("object", "/object", ObjectLet.class)) ;

		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		ISerialRequest ar = ac.createSerialRequest("/another/object") ;
		
		final TestBean obj = new TestBean();
		obj.setColor(ListUtil.create("Red" )) ;
		
		TestBean tb = ar.put(obj, TestBean.class) ;
		Debug.debug(tb) ;
	}
	
	
	@Ignore
	public void testAnnotation() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", ObjectLet.class).getAradon() ;

		AradonClient ac = AradonClientFactory.create(aradon) ;
		ISerialRequest request = ac.createSerialRequest("/test") ;
		TestBean param = new TestBean();
		param.setColor(ListUtil.toList("Red", "Blue")) ;
		TestBean tb = request.handle(Method.SEARCH, param, TestBean.class) ;
		
		assertEquals("SEARCH", tb.getQuery()) ;
		
	}

}

class ObjectLet extends ServerResource{
	
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


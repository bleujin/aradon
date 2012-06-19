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
import net.ion.radon.core.TestAradon;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.param.TestBean;
import net.ion.radon.util.AradonTester;

import org.junit.Ignore;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.data.Method;
import org.restlet.representation.ObjectRepresentation;

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
		
		aradon.getChildService("another").attach(PathInfo.create("object", "/object", ObjectLet.class)) ;

		
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

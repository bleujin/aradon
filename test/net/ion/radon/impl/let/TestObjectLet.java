package net.ion.radon.impl.let;

import net.ion.framework.rest.StdObject;
import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.radon.TestAradon;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.client.SubRequest;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.param.TestBean;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.engine.resource.AnnotationUtils;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ServerResource;
import org.restlet.resource.UniformResource;
import org.restlet.service.MetadataService;
import org.restlet.service.Service;
import org.restlet.util.Series;

public class TestObjectLet extends TestAradon{

	public void testgRIAPGet() throws Exception {
		initAradon() ;
		aradon.getChildService("another").attach(PathInfo.create("object", "/object", ObjectLet.class)) ;
		Request request = new Request(Method.GET, "riap://component/another/object") ;
		
		
		TestBean tb = aradon.handle(request, TestBean.class) ;
		assertEquals("Red", tb.getColor().get(0)) ;
	}
	

	public void testObjectGet() throws Exception {
		initAradon() ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		IAradonRequest ar = ac.createRequest("/?aradon.result.format=object");
		StdObject sto = ar.get(StdObject.class) ;
		
		assertTrue(sto != null) ;
	}
	
	public void testObjectPut() throws Exception {
		initAradon() ;
		
		aradon.getChildService("another").attach(PathInfo.create("object", "/object", ObjectLet.class)) ;

		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		IAradonRequest ar = ac.createRequest("/another/object") ;
		
		final TestBean obj = new TestBean();
		obj.setColor(ListUtil.create("Red" )) ;
		
		TestBean tb = ar.put(obj, TestBean.class) ;
		Debug.debug(tb) ;
	}
	
	public void testAnnotation() throws Exception {
		initAradon() ;

		aradon.getChildService("another").attach(PathInfo.create("object", "/object", ObjectLet.class)) ;

		Request request = new Request(Method.SEARCH, "riap://component/another/object") ;
		
		final TestBean testBean = new TestBean();
		testBean.setColor(ListUtil.create("Red")) ;
		request.setEntity(new ObjectRepresentation(testBean)) ;
		
		TestBean result = aradon.handle(request, TestBean.class) ;
		assertEquals("SEARCH", result.getQuery()) ;
	}

}

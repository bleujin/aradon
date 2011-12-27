package net.ion.radon.core.let;

import static org.junit.Assert.*;
import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.PathInfo;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestSection extends TestAradon{

	@Test
	public void testSettionName() throws Exception {
		initAradon() ;
		
		SectionService section = aradon.getChildService("another") ;
		Debug.debug(section.toString() );
	}
	
	@Test
	public void testRestart() throws Exception {
		initAradon() ;
		SectionService section = aradon.attach("test", XMLConfig.BLANK) ;
		section.attach(PathInfo.HELLO) ;
		
		Request req = new Request(Method.GET, "riap://component/test/test");
		Response res = aradon.handle(req) ;
		
		assertEquals(200, res.getStatus().getCode()) ;
		section.suspend() ;
		
		req = new Request(Method.GET, "riap://component/test/test");
		res = aradon.handle(req) ;
		assertEquals(503, res.getStatus().getCode()) ;
	}
	
	@Test
	public void testGetPathName() throws Exception {
		initAradon() ;
		SectionService section = aradon.attach("test", XMLConfig.BLANK) ;
		section.attach(PathInfo.create("hello", "/test, /test/{next}", "", "", HelloWorldLet.class)) ;

		Request req = new Request(Method.GET, "riap://component/test/test");
		Response res = aradon.handle(req) ;
		
		
	}
	
	@Test
	public void testCrossCallSection0() throws Exception {
		initAradon() ;
		SectionService section1 = aradon.attach("test1", XMLConfig.BLANK) ;
		SectionService section2 = aradon.attach("test2", XMLConfig.BLANK) ;
		section2.attach(PathInfo.create("hello", "/hello", "", "", HelloWorldLet.class)) ;
		section1.attach(PathInfo.create("cross", "/cross", "", "", TestCrossLet.class)) ;
		
		Request request = new Request(Method.GET, "riap://component/test2/hello") ;
		Response response = aradon.handle(request) ;
		assertEquals(200, response.getStatus().getCode()) ;
		Debug.debug(response.getEntityAsText()) ;
	}
	
	@Test
	public void testCrossCallSection() throws Exception {
		initAradon() ;
		SectionService section1 = aradon.attach("test1", XMLConfig.BLANK) ;
		SectionService section2 = aradon.attach("test2", XMLConfig.BLANK) ;
		section2.attach(PathInfo.create("hello", "/hello", "", "", HelloWorldLet.class)) ;
		section1.attach(PathInfo.create("cross", "/cross", "", "", TestCrossLet.class)) ;
		
//		Request request = new Request(Method.GET, "riap://component/test2/hello") ;
//		Response response = aradon.handle(request) ;
//		assertEquals(200, response.getStatus().getCode()) ;
//		Debug.debug(response.getEntityAsText()) ;
		
		Request request = new Request(Method.GET, "riap://component/test1/cross") ;
		Response response = aradon.handle(request) ;
		assertEquals(200, response.getStatus().getCode()) ;
		Debug.debug(response.getEntityAsText()) ;
	}
	
}

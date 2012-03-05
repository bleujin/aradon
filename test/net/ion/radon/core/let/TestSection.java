package net.ion.radon.core.let;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.PathInfo;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestSection extends TestBaseAradon{

	@Test
	public void testSettionName() throws Exception {
		Aradon aradon = testAradon() ;
		
		SectionService section = aradon.getChildService("another") ;
		Debug.debug(section.toString() );
	}
	
	@Test
	public void testRestart() throws Exception {
		Aradon aradon = testAradon() ;
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
		Aradon aradon = testAradon() ;

		SectionService section = aradon.attach("test", XMLConfig.BLANK) ;
		section.attach(PathInfo.create("hello", "/test, /test/{next}", HelloWorldLet.class)) ;

		Request req = new Request(Method.GET, "riap://component/test/test");
		Response res = aradon.handle(req) ;
	}
	
	
}

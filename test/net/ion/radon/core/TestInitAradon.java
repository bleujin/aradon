package net.ion.radon.core;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.let.GetLet;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.util.AradonTester;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestInitAradon extends TestCase{
	
	public void testInit() throws Exception {
		Aradon aradon = new Aradon() ;
		aradon.init(XMLConfig.BLANK) ;
		
		SectionService section = aradon.attach("test", XMLConfig.BLANK) ;
		section.attach(PathInfo.create("hello", "/hello", GetLet.class)) ;
		
		Request request = new Request(Method.GET, "riap://component/test/hello") ;
		Response response = aradon.handle(request) ;
		
		assertEquals(GetLet.class.getCanonicalName(), response.getEntityAsText()) ;
	}
	
	public void testInitTester() throws Exception {
		AradonTester at = AradonTester.create().register("test", "/hello", GetLet.class) ;

		Request request = new Request(Method.GET, "riap://component/test/hello") ;
		Response response = at.getAradon().handle(request) ;
		
		assertEquals(GetLet.class.getCanonicalName(), response.getEntityAsText()) ;
	}
	
}

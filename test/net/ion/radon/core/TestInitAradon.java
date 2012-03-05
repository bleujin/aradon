package net.ion.radon.core;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.ListUtil;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.let.GetLet;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.engine.Engine;

public class TestInitAradon {
	
	@Test
	public void initAradon() throws Exception {
		Engine.getInstance() ;

		
		Aradon aradon = new Aradon() ;
		aradon.init(XMLConfig.BLANK) ;
		
		SectionService section = aradon.attach("test", XMLConfig.BLANK) ;
		section.attach(PathInfo.create("hello", "/hello", GetLet.class)) ;
		
		Request request = new Request(Method.GET, "riap://component/test/hello") ;
		Response response = aradon.handle(request) ;
		
		
		assertEquals(GetLet.class.getCanonicalName(), response.getEntityAsText()) ;
	}
	
	@Test
	public void initTester() throws Exception {
		AradonTester at = AradonTester.create().register("test", "/hello", GetLet.class) ;

		Request request = new Request(Method.GET, "riap://component/test/hello") ;
		Response response = at.getAradon().handle(request) ;
		
		assertEquals(GetLet.class.getCanonicalName(), response.getEntityAsText()) ;
	}
	
	@Test
	public void startNStop() throws Exception {
		for (int i : ListUtil.rangeNum(1, 3)) {
			AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class) ;
			at.startServer(9111) ;
			
			at.getAradon().stop() ;
		}
	}
	
}

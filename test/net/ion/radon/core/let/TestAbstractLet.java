package net.ion.radon.core.let;

import static org.junit.Assert.assertEquals;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.IService;
import net.ion.radon.core.PathService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TestAradon;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestAbstractLet extends TestBaseAradon {

	@Test
	public void testAbstractLet() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/hello", HelloWorldLet.class).getAradon() ;
		
		SectionService section = aradon.attach("test", XMLConfig.BLANK);
		final PathInfo pathInfo = PathInfo.create("test", "/test", HelloWorldLet.class);
		section.attach(pathInfo);
		
		Request request = new Request(Method.GET, "riap://component/test/test");
		Response response = aradon.handle(request);

		InnerResponse res = (InnerResponse) Response.getCurrent() ;
		assertEquals(true, ((PathService)res.getPathService(aradon)).getPathInfo() == pathInfo);
	}
	
	@Test
	public void testParentService() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/hello", HelloWorldLet.class).getAradon() ;
		
		SectionService section = aradon.attach("test", XMLConfig.BLANK);
		section.attach(PathInfo.create("test", "/test", HelloWorldLet.class));
		
		Request request = new Request(Method.GET, "riap://component/test/test");
		Response response = aradon.handle(request);
		
		InnerResponse res = (InnerResponse) Response.getCurrent() ;
		final IService pservice = res.getPathService(aradon);
		assertEquals(true, pservice == section.getChildService("test")) ;
		assertEquals(true, section == pservice.getParent());
		assertEquals(true, aradon == pservice.getParent().getParent());
		assertEquals(true, IService.ROOT == pservice.getParent().getParent().getParent());
	}
	
	
	
	
	
}

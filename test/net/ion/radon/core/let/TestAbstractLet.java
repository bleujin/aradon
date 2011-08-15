package net.ion.radon.core.let;

import net.ion.radon.TestAradon;
import net.ion.radon.core.IService;
import net.ion.radon.core.PathService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.impl.section.PathInfo;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestAbstractLet extends TestAradon {

	public void testAbstractLet() throws Exception {
		initAradon();
		
		SectionService section = aradon.attach("test", XMLConfig.BLANK);
		final PathInfo pathInfo = PathInfo.create("test", "/test", "", "", HelloWorldLet.class);
		section.attach(pathInfo);
		
		Request request = new Request(Method.GET, "riap://component/test/test");
		Response response = aradon.handle(request);
		
		assertEquals(true, ((PathService)getInnerResponse().getPathService(aradon)).getPathInfo() == pathInfo);
	}
	
	public void testParentService() throws Exception {
		initAradon();
		
		SectionService section = aradon.attach("test", XMLConfig.BLANK);
		section.attach(PathInfo.create("test", "/test", "", "", HelloWorldLet.class));
		
		Request request = new Request(Method.GET, "riap://component/test/test");
		Response response = aradon.handle(request);
		
		final IService pservice = getInnerResponse().getPathService(aradon);
		assertEquals(true, pservice == section.getChildService("test")) ;
		assertEquals(true, section == pservice.getParent());
		assertEquals(true, aradon == pservice.getParent().getParent());
		assertEquals(true, IService.ROOT == pservice.getParent().getParent().getParent());
	}
	
	
	
	
	
}

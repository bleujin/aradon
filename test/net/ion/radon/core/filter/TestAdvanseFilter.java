package net.ion.radon.core.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.radon.TestAradon;
import net.ion.radon.core.PathService;
import net.ion.radon.core.SectionService;
import net.ion.radon.impl.filter.RevokeServiceFilter;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Status;

public class TestAdvanseFilter extends TestAradon{
	
	@Test
	public void testPathRevokeFilter() throws Exception {
		initAradon();
		SectionService section = aradon.getChildService("another");
		section.getChildService("hello").suspend() ;

		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);
		
		assertEquals(Status.SERVER_ERROR_SERVICE_UNAVAILABLE, response.getStatus()) ;
	}
	
	@Test
	public void testSectionRevokeFilter() throws Exception {
		initAradon();
		SectionService secSrv = aradon.getChildService("another");
		secSrv.addPreFilter(RevokeServiceFilter.SELF) ;
		
		secSrv.getChildService("hello").addPreFilter(new HiFilter());

		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);
		
		assertEquals(Status.SERVER_ERROR_SERVICE_UNAVAILABLE, response.getStatus()) ;
	}
	
	
	@Test
	public void testOrderFilter() throws Exception {
		initAradon();
		SectionService section = aradon.getChildService("another");
		section.addPreFilter(new HiFilter(1)) ;
		section.addPreFilter(new HiFilter(2)) ;
		section.addPreFilter(new HiFilter(3)) ;
		
		section.getChildService("hello").addPreFilter(new HiFilter(4)) ;
		
		HiFilter.result = ListUtil.newList() ;
		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);
		
		assertTrue(Arrays.equals(HiFilter.result.toArray(), new Object[]{1,2,3,4})) ;
	}
	
	@Test
	public void testOrderFilter2() throws Exception {
		initAradon();
		SectionService section = aradon.getChildService("another");
		section.addPreFilter(new HiFilter(1)) ;
		section.addAfterFilter(new HiFilter(3)) ;
		section.addAfterFilter(new HiFilter(4)) ;
		
		section.getChildService("hello").addPreFilter(new HiFilter(2)) ;

		HiFilter.result = ListUtil.newList() ;
		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);
		
		assertTrue(Arrays.equals(HiFilter.result.toArray(), new Object[]{1,2,3,4})) ;
	}
	
	
	@Test
	public void testSkipFilter() throws Exception {
		initAradon();
		SectionService secSrv = aradon.getChildService("another");
		secSrv.addPreFilter(new HiFilter(1)) ;
		
		PathService pservice = secSrv.getChildService("hello");
		
		pservice.addPreFilter(new HiFilter(2, IFilterResult.SKIP_RESULT)) ;
		pservice.addPreFilter(new HiFilter(3)) ;
		pservice.addAfterFilter(new HiFilter(4)) ;

		HiFilter.result = ListUtil.newList() ;
		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		aradon.handle(request);
		
		Debug.debug(HiFilter.result.toArray()) ;
		assertTrue(Arrays.equals(HiFilter.result.toArray(), new Object[]{1,2,3,4})) ;
	}
	
	@Test
	public void testSkipFilter2() throws Exception {
		initAradon();
		SectionService secSrv = aradon.getChildService("another");
		secSrv.addPreFilter(new HiFilter(1, IFilterResult.SKIP_RESULT)) ;
		
		PathService pservice = secSrv.getChildService("hello");
		pservice.addPreFilter(new HiFilter(2)) ;
		pservice.addPreFilter(new HiFilter(3)) ;
		pservice.addAfterFilter(new HiFilter(4)) ;

		HiFilter.result = ListUtil.newList() ;
		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		aradon.handle(request);
		
		Debug.debug(HiFilter.result.toArray()) ;
		assertTrue(Arrays.equals(HiFilter.result.toArray(), new Object[]{1,2,3,4})) ;
	}
	
	
	
}

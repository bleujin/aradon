package net.ion.radon.core.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.PathService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TestAradon;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.impl.filter.RevokeServiceFilter;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Status;

public class TestAdvanseFilter extends TestBaseAradon{
	
	@Test
	public void testPathRevokeFilter() throws Exception {
		Aradon aradon = testAradon();
		SectionService section = aradon.getChildService("another");
		section.getChildService("hello").suspend() ;

		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);
		
		assertEquals(Status.SERVER_ERROR_SERVICE_UNAVAILABLE, response.getStatus()) ;
	}
	
	@Test
	public void testSectionRevokeFilter() throws Exception {
		Aradon aradon = testAradon();
		SectionService secSrv = aradon.getChildService("another");
		secSrv.addPreFilter(RevokeServiceFilter.SELF) ;
		
		secSrv.getChildService("hello").addPreFilter(new HiFilter());

		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);
		
		assertEquals(Status.SERVER_ERROR_SERVICE_UNAVAILABLE, response.getStatus()) ;
	}
	
	
	@Test
	public void testOrderFilter() throws Exception {
		Aradon aradon = testAradon();

		SectionService section = aradon.getChildService("another");
		section.addPreFilter(new HiFilter(1)) ;
		section.addPreFilter(new HiFilter(2)) ;
		
		section.getChildService("hello").addPreFilter(new HiFilter(3)) ;
		
		HiFilter.result = ListUtil.newList() ;
		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);
		 
		assertTrue(Arrays.equals(HiFilter.result.toArray(), new Object[]{1,2,3})) ;
	}
	
	@Test
	public void testOrderFilter2() throws Exception {
		Aradon aradon = testAradon();

		SectionService section = aradon.getChildService("another");
		section.addPreFilter(new HiFilter(1)) ;
		section.addPreFilter(new HiFilter(2)) ;
		section.getChildService("hello").addPreFilter(new HiFilter(3)) ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		HiFilter.result = ListUtil.newList() ;
		Response response = ac.createRequest("/another/hello?greeting=Hello&name=bleujin").handle(Method.GET) ;
		assertTrue(Arrays.equals(HiFilter.result.toArray(), new Object[]{1,2,3})) ;
		
		aradon.stop() ;
	}
	
	
	@Test
	public void testSkipFilter() throws Exception {
		Aradon aradon = testAradon();
		
		SectionService secSrv = aradon.getChildService("another");
		secSrv.addPreFilter(new HiFilter(1)) ;
		
		PathService pservice = secSrv.getChildService("hello");
		
		pservice.addPreFilter(new HiFilter(2, IFilterResult.SKIP_RESULT)) ;
		pservice.addPreFilter(new HiFilter(3)) ;
		pservice.addAfterFilter(new HiFilter(4)) ;

		HiFilter.result = ListUtil.newList() ;
		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		aradon.handle(request);
		 
		assertTrue(Arrays.equals(HiFilter.result.toArray(), new Object[]{1,2,3})) ;
	}

	
	
}

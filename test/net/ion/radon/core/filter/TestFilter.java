package net.ion.radon.core.filter;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.IService;
import net.ion.radon.core.PathService;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TestAradon;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.EnumClass.IConvertType;
import net.ion.radon.core.EnumClass.IZone;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.impl.filter.ExecuteTimeFilter;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.param.ParamToBeanFilter;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestFilter extends TestBaseAradon {

	@Test
	public void testPathFilter() throws Exception {
		Aradon aradon = testAradon();
		SectionService section = aradon.getChildService("another");
		final PathService helloService = section.getChildService("hello");
		helloService.addPreFilter(new ParamToBeanFilter("mybean", "net.ion.radon.core.filter.HelloBean")) ;
		helloService.addPreFilter(new HiFilter()) ;

		assertEquals(2, helloService.getPreFilters().size()) ;
		
		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);
		
		InnerResponse res = (InnerResponse) Response.getCurrent() ;
		IService pservice = res.getPathService(aradon) ; 
		InnerRequest ireq = ((InnerResponse)Response.getCurrent()).getInnerRequest() ;
		assertEquals(2, pservice.getPreFilters().size()) ;
		
		assertEquals(true, helloService.getServiceContext() == ireq.getContext().getParentContext()) ;
		
		TreeContext requestContext = (TreeContext) response.getRequest().getAttributes().get(RadonAttributeKey.REQUEST_CONTEXT);
		assertEquals(true, requestContext == ireq.getContext()) ;
		
		HelloBean bean = requestContext.getSelfAttributeObject("mybean", HelloBean.class);
		Debug.debug(bean) ;
		
		
		assertEquals("bleujin", bean.getName()) ;
	}

	@Test
	public void testSectionFilter() throws Exception {
		Aradon aradon = testAradon();
		SectionService secSrv = aradon.getChildService("another");
		TreeContext sectionContext = secSrv.getServiceContext();
		assertEquals(IZone.Section, sectionContext.getZone());
		final ParamToBeanFilter paramfilter = new ParamToBeanFilter("mybean", "net.ion.radon.core.filter.HelloBean");
		secSrv.addPreFilter(paramfilter) ;
		secSrv.addPreFilter(new HiFilter()) ;

		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);

		InnerResponse res = (InnerResponse) Response.getCurrent() ;
		IService pservice = res.getPathService(aradon) ;
		
		TreeContext requestContext = (TreeContext) response.getRequest().getAttributes().get(RadonAttributeKey.REQUEST_CONTEXT);
		assertEquals(true, pservice.getParent().getPreFilters().contains(paramfilter)) ;
		Debug.debug(requestContext.getSelfAttributeObject("mybean", HelloBean.class));
		HelloBean bean = requestContext.getSelfAttributeObject("mybean", HelloBean.class);
		assertEquals("bleujin", bean.getName()) ;
	}
	
	@Test
	public void testApplicationFilter() throws Exception {
		Aradon aradon = testAradon();

		assertEquals(IZone.Application, aradon.getServiceContext().getZone());
		final ParamToBeanFilter paramFilter = new ParamToBeanFilter("mybean", "net.ion.radon.core.filter.HelloBean");
		aradon.addPreFilter(paramFilter) ;
		aradon.addPreFilter(new HiFilter()) ;

		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);

		InnerResponse res = (InnerResponse) Response.getCurrent() ;
		IService pservice = res.getPathService(aradon) ;
		TreeContext requestContext = (TreeContext) response.getRequest().getAttributes().get(RadonAttributeKey.REQUEST_CONTEXT);
		assertEquals(true, pservice.getAradon().getPreFilters().contains(paramFilter)) ;
		
		Debug.debug(requestContext.getSelfAttributeObject("mybean", HelloBean.class));
	}
	
	
	@Test
	public void testInit() throws Exception {
		Aradon aradon = testAradon();
		
		SectionService section = aradon.getChildService("another");
		section.attach(PathInfo.HELLO) ;
		
		section.addPreFilter(new ExecuteTimeFilter()) ;

		
		section.getPreFilters() ;
		
	}
	
	
	
	
	
}

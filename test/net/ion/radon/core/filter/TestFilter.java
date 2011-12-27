package net.ion.radon.core.filter;

import static org.junit.Assert.*;
import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.core.IService;
import net.ion.radon.core.PathService;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.EnumClass.IConvertType;
import net.ion.radon.core.EnumClass.IZone;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.impl.filter.ExecuteTimeFilter;
import net.ion.radon.impl.section.PathInfo;
import net.ion.radon.param.ParamFilter;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestFilter extends TestAradon {

	@Test
	public void testPathFilter() throws Exception {
		initAradon();
		SectionService section = aradon.getChildService("another");
		final PathService helloService = section.getChildService("hello");
		helloService.addPreFilter(new ParamFilter("mybean", IConvertType.BEAN.toString(), "net.ion.radon.core.filter.HelloBean")) ;
		helloService.addPreFilter(new HiFilter()) ;

		assertEquals(2, helloService.getPreFilters().size()) ;
		
		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);
		
		IService pservice = getInnerResponse().getPathService(aradon) ; 
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
		initAradon();
		SectionService secSrv = aradon.getChildService("another");
		TreeContext sectionContext = secSrv.getServiceContext();
		assertEquals(IZone.Section, sectionContext.getZone());
		final ParamFilter paramfilter = new ParamFilter("mybean", IConvertType.BEAN.toString(), "net.ion.radon.core.filter.HelloBean");
		secSrv.addPreFilter(paramfilter) ;
		secSrv.addPreFilter(new HiFilter()) ;

		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);

		IService pservice = getInnerResponse().getPathService(aradon) ;
		
		TreeContext requestContext = (TreeContext) response.getRequest().getAttributes().get(RadonAttributeKey.REQUEST_CONTEXT);
		assertEquals(true, pservice.getParent().getPreFilters().contains(paramfilter)) ;
		Debug.debug(requestContext.getSelfAttributeObject("mybean", HelloBean.class));
		HelloBean bean = requestContext.getSelfAttributeObject("mybean", HelloBean.class);
		assertEquals("bleujin", bean.getName()) ;
	}
	
	@Test
	public void testApplicationFilter() throws Exception {
		initAradon();

		assertEquals(IZone.Application, aradon.getServiceContext().getZone());
		final ParamFilter paramFilter = new ParamFilter("mybean", IConvertType.BEAN.toString(), "net.ion.radon.core.filter.HelloBean");
		aradon.addPreFilter(paramFilter) ;
		aradon.addPreFilter(new HiFilter()) ;

		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);

		IService pservice = getInnerResponse().getPathService(aradon) ;
		TreeContext requestContext = (TreeContext) response.getRequest().getAttributes().get(RadonAttributeKey.REQUEST_CONTEXT);
		assertEquals(true, pservice.getAradon().getPreFilters().contains(paramFilter)) ;
		
		Debug.debug(requestContext.getSelfAttributeObject("mybean", HelloBean.class));
	}
	
	
	@Test
	public void testInit() throws Exception {
		initAradon();
		
		SectionService section = aradon.getChildService("another");
		section.attach(PathInfo.HELLO) ;
		
		section.addPreFilter(new ExecuteTimeFilter()) ;

		
		section.getPreFilters() ;
		
	}
	
	
	
	
	
}

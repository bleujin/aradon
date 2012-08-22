package net.ion.radon.core.filter;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.IService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TestBaseAradon;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.EnumClass.IZone;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.core.let.PathService;
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
		helloService.getConfig().addPreFilter(new ParamToBeanFilter("mybean", "net.ion.radon.core.filter.HelloBean")) ;
		helloService.getConfig().addPreFilter(new HiFilter()) ;

		assertEquals(2, helloService.getConfig().prefilters().size()) ;
		
		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);
		
		InnerResponse res = (InnerResponse) Response.getCurrent() ;
		PathConfiguration pconfig = res.getPathConfiguration() ; 
		InnerRequest ireq = ((InnerResponse)Response.getCurrent()).getInnerRequest() ;
		assertEquals(2, pconfig.prefilters().size()) ;
		
		assertEquals(true, helloService.getServiceContext() == ireq.getPathService().getServiceContext()) ;
		
		HelloBean bean = (HelloBean) response.getRequest().getAttributes().get("mybean");
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
		secSrv.getConfig().addPreFilter(paramfilter) ;
		secSrv.getConfig().addPreFilter(new HiFilter()) ;

		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);

		InnerResponse res = (InnerResponse) Response.getCurrent() ;
		PathConfiguration pconfig = res.getPathConfiguration() ;
		
		assertEquals(true, res.getInnerRequest().getPathService().getParent().getConfig().prefilters().contains(paramfilter)) ;
		HelloBean bean = (HelloBean) request.getAttributes().get("mybean");
		assertEquals("bleujin", bean.getName()) ;
	}
	
	@Test
	public void testApplicationFilter() throws Exception {
		Aradon aradon = testAradon();

		assertEquals(IZone.Application, aradon.getServiceContext().getZone());
		final ParamToBeanFilter paramFilter = new ParamToBeanFilter("mybean", "net.ion.radon.core.filter.HelloBean");
		aradon.getConfig().addPreFilter(paramFilter) ;
		aradon.getConfig().addPreFilter(new HiFilter()) ;

		Request request = new Request(Method.GET, "riap://component/another/hello?greeting=Hello&name=bleujin");
		Response response = aradon.handle(request);

		InnerResponse res = (InnerResponse) Response.getCurrent() ;
		PathConfiguration pservice = res.getPathConfiguration();
		assertEquals(true, aradon.getConfig().prefilters().contains(paramFilter)) ;

		HelloBean bean = (HelloBean) request.getAttributes().get("mybean");
		Debug.debug(bean);
	}
	
	
	@Test
	public void testInit() throws Exception {
		Aradon aradon = testAradon();
		
		SectionService section = aradon.getChildService("another");
		section.attach(PathConfiguration.testHello()) ;
		
		SampleFilter filter = new SampleFilter();
		section.getConfig().addPreFilter(filter) ;
		
		AradonClient client = AradonClientFactory.create(aradon);
		client.createRequest("/another/hello").get() ;
		
		assertEquals(true, filter.initialized()) ;
	}
	
}

class SampleFilter extends IRadonFilter{

	private boolean initialized = false ;
	@Override
	public IFilterResult afterHandle(IService iservice, Request request, Response response) {
		return IFilterResult.CONTINUE_RESULT;
	}

	@Override
	public void init(IService service){
		this.initialized = true ;
	}
	
	@Override
	public IFilterResult preHandle(IService iservice, Request request, Response response) {
		return IFilterResult.CONTINUE_RESULT;
	}

	public boolean initialized(){
		return initialized ;
	}
}

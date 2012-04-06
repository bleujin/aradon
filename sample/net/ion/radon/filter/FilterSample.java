package net.ion.radon.filter;

import java.util.Map;

import junit.framework.TestCase;
import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.parse.gson.JsonUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.IService;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.core.security.ChallengeAuthenticator;
import net.ion.radon.param.MyParameter;
import net.ion.radon.util.AradonTester;

import org.junit.Assert;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;

public class FilterSample extends TestCase{

	public void testParamToFilter() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", ConfirmLet.class).getAradon() ;
		aradon.addPreFilter(ParamToBeanFilter.create("emp", Employee.class)) ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		IAradonRequest request = ac.createRequest("/test") ;
		request.addParameter("empNo", "100").addParameter("ename", "bleujin").addParameter("names", "bleu").addParameter("names", "jin") ;
		
		Response res = request.handle(Method.POST) ;
		JsonObject rtn = JsonParser.fromString(res.getEntityAsText()).getAsJsonObject() ;
		
		assertEquals("bleujin", rtn.asString("ename")) ;
		assertEquals(100L, JsonUtil.toSimpleObject(rtn.get("empNo"))) ;
		assertEquals(100, rtn.asInt("empNo")) ;
		assertEquals("[\"bleu\",\"jin\"]", rtn.asJsonArray("names").toString()) ;
	}
	
	public void testAuthFilter() throws Exception {
		Aradon aradon = AradonTester.create()
			.register("sec", "/test", ConfirmLet.class)
			.register("nosec", "/test", ConfirmLet.class).getAradon() ;
		aradon.getChildService("sec").addPreFilter(new ChallengeAuthenticator("sec area", "admin", "redf")) ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		
		assertEquals(401, ac.createRequest("/sec/test").handle(Method.GET).getStatus().getCode()) ;
		assertEquals(200, ac.createRequest("/nosec/test").handle(Method.GET).getStatus().getCode()) ;
		
		assertEquals(200, ac.createRequest("/sec/test", "admin", "redf").handle(Method.GET).getStatus().getCode()) ;
		
//		aradon.startServer(9000) ;
//		new InfinityThread().startNJoin() ;
//		aradon.stop() ;
	}
	
}


class ConfirmLet extends AbstractServerResource {
	
	@Get
	public String confirm(){
		return "hi" ;
	}
	
	@Post
	public String post(){
		Employee emp = getContext().getAttributeObject("emp", Employee.class) ;
		Assert.assertEquals("bleujin", emp.getEname()) ;
		Assert.assertEquals(100, emp.getEmpNo()) ;
		Assert.assertEquals(new String[]{"bleu","jin"}, emp.getNames()) ;

		return JsonParser.fromObject(emp).toString() ;
	}
}

class ParamToBeanFilter extends IRadonFilter {

	private String beanName ;
	private String className ; 

	public ParamToBeanFilter(String beanName, String className) {
		this.beanName = beanName  ;
		this.className = className ;
	}

	public final static ParamToBeanFilter create(String name, Class clz){
		return new ParamToBeanFilter(name, clz.getCanonicalName()) ;
	}
	
	@Override
	public IFilterResult preHandle(IService service, Request request, Response response) {
		Map<String, Object> params = getInnerRequest(request).getGeneralParameter() ;
		
		try {
			Object beanObj = MyParameter.create(params).toBean(Class.forName(className)) ;
			getInnerRequest(request).getContext().putAttribute(this.beanName, beanObj) ;
		} catch (ClassNotFoundException e) {
			return IFilterResult.stopResult(new ResourceException(Status.SERVER_ERROR_NOT_IMPLEMENTED, e.getMessage())) ;
		}
		return IFilterResult.CONTINUE_RESULT;
	}

	@Override
	public IFilterResult afterHandle(IService service, Request request, Response response) {
		return IFilterResult.CONTINUE_RESULT ;
	}

}

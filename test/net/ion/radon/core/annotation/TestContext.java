package net.ion.radon.core.annotation;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.Debug;
import net.ion.nradon.let.IServiceLet;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.util.AradonTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.Get;

public class TestContext {

	private Aradon aradon ;
	private AradonClient ac ;
	
	@Before
	public void setUp() throws Exception {
		this.aradon = AradonTester.create()
			.putAttribute("cname", "bleujin")
			.register("", "/context", ContextLet.class).getAradon() ;
		this.ac = AradonClientFactory.create(aradon);
	}
	
	@After
	public void tearDown() throws Exception {
		ac.stop() ;
		aradon.stop() ;
	}
	
	@Test
	public void viewAnnotation() throws Exception {
		Response res = ac.createRequest("/context?m=view").handle(Method.GET);
		assertEquals(200, res.getStatus().getCode()) ;
		Debug.line(res.getEntityAsText()) ;
	}

	@Test
	public void viewInherit() throws Exception {
		Response res = ac.createRequest("/context?m=inherit").handle(Method.GET);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("bleujin", res.getEntityAsText()) ;
	}

}


class ContextLet extends ParentLet{
	
	@Get("?m=view")
	public String viewContextString(@AnContext TreeContext context, @AnRequest InnerRequest request, @AnResponse InnerResponse response){
		return "" + context + "," + request.getRemainPath() + ","+ response ;
	}

	@Get("?m=inherit")
	public String viewContextValue(@AnContext TreeContext context, @DefaultValue("cname") @FormParam("key") String key){
		return getContextValue(context, key) ;
	}

}

class ParentLet implements IServiceLet {
	
	protected String getContextValue(TreeContext context, String key){
		return context.getAttributeObject(key).toString() ;
	}
}



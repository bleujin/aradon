package net.ion.radon.core.annotation;

import static org.junit.Assert.assertEquals;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class TestAnnotation {

	private Aradon aradon ;
	private AradonClient ac ;
	
	@Before
	public void setUp() throws Exception {
		this.aradon = AradonTester.create()
			.putAttribute("akey", "avalue")
			.putAttribute("akeyobj", new StringBuffer("hello"))
			.register("", "/test, /test/{pkey}", TestLet.class).getAradon() ;
		this.ac = AradonClientFactory.create(aradon);
//		aradon.startServer(9000) ;
//		this.ac = AradonClientFactory.create("http://localhost:9000") ;
	}
	
	@After
	public void tearDown() throws Exception {
		ac.stop() ;
		aradon.stop() ;
	}
	
	@Test
	public void formParam() throws Exception {
		
		Response res = ac.createRequest("/test?m=header").addParameter("fkey", "fvalue").addHeader("hkey", "hvalue").handle(Method.GET);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("hvalue", res.getEntityAsText()) ;

		
		res = ac.createRequest("/test?m=form").addParameter("fkey", "fvalue").addHeader("hkey", "hvalue").handle(Method.GET);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("fvalue", res.getEntityAsText()) ;
		

		res = ac.createRequest("/test?m=cookie").addParameter("fkey", "fvalue").addHeader("hkey", "hvalue").addHeader("Cookie", "ckey=cvalue;ckey2=cvalue2").handle(Method.GET);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("cvalue", res.getEntityAsText()) ;
		

		res = ac.createRequest("/test/pvalue?m=path").addParameter("fkey", "fvalue").addHeader("hkey", "hvalue").addHeader("Cookie", "ckey=cvalue;ckey2=cvalue2").handle(Method.GET);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("pvalue", res.getEntityAsText()) ;
		

		res = ac.createRequest("/test/pvalue?m=context").addParameter("fkey", "fvalue").addHeader("hkey", "hvalue").addHeader("Cookie", "ckey=cvalue;ckey2=cvalue2").handle(Method.GET);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("avalue/pvalue", res.getEntityAsText()) ;

	}

	@Test 
	public void contextObj() throws Exception {
		Response res = ac.createRequest("/test/pvalue?m=context2").addParameter("fkey", "fvalue").addHeader("hkey", "hvalue").addHeader("Cookie", "ckey=cvalue;ckey2=cvalue2").handle(Method.GET);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("hello", res.getEntityAsText()) ;
	}
	
	
	@Test
	public void defaultValue() throws Exception {
		Response res = ac.createRequest("/test?m=default").handle(Method.GET);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("hello-1", res.getEntityAsText()) ;

		res = ac.createRequest("/test?m=default").addParameter("fkey", "").handle(Method.GET);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("-1", res.getEntityAsText()) ;

	}
	
	@Test
	public void formBean() throws Exception {
		Response res = ac.createRequest("/test?m=formbean").addParameter("name", "bleujin").addParameter("age", "20").handle(Method.POST);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("bleujin20bleujin", res.getEntityAsText()) ;
	}

	
	@Test
	public void typeParam(){
		Response res = ac.createRequest("/test/123?m=type").addParameter("fkey", "123").addHeader("hkey", "hvalue").addHeader("Cookie", "ckey=cvalue;ckey2=cvalue2").handle(Method.GET);
		assertEquals(200, res.getStatus().getCode()) ;
		assertEquals("246", res.getEntityAsText()) ;
	}
	
}


class TestLet extends AbstractServerResource {
	
	@Get("?m=header")
	public String header(@HeaderParam("hkey") String headerValue){
		return headerValue ;
	}
	
	@Get("?m=form")
	public String form(@FormParam("fkey") String formValue){
		return formValue ;
	}

	@Get("?m=cookie")
	public String cookie(@CookieParam("ckey") String cookieValue){
		return cookieValue ;
	}

	@Get("?m=path")
	public String path(@PathParam("pkey") String pathValue){
		return pathValue ;
	}
	
	@Get("?m=context")
	public String context(@ContextParam("akey") String contextValue, @PathParam("pkey") String pathValue){
		return contextValue + "/" + pathValue;
	}
	
	@Get("?m=context2")
	public String context2(@ContextParam("akeyobj") StringBuffer contextValue){
		return contextValue.toString() ;
	}
	
	@Get("?m=type")
	public String type(@PathParam("pkey") long pathValue, @FormParam("fkey") long formValue){
		return String.valueOf(pathValue + formValue);
	}
	
	@Get("?m=default")
	public String defaultValue(@DefaultValue("hello") @FormParam("fkey") String formValue, @DefaultValue("-1") @PathParam("pkey") int dft){
		return formValue + dft;
	}
	
	@Post("?m=formbean")
	public String formValue(@FormBean User user, @FormBean  Another ano ){
		return user.getName() + user.getAge() + ano.getName();
	}
}


class User {
	private int age ;
	private String name;
	
	public void setAge(int age) {
		this.age = age;
	}
	public int getAge() {
		return age;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}

class Another {
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}

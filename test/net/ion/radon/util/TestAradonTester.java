package net.ion.radon.util;

import static org.junit.Assert.assertEquals;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.core.let.PathService;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.resource.Get;

public class TestAradonTester {

	@Test
	public void register() throws Exception {
		AradonTester at = AradonTester.create().register("s1", "/shutdown/{name}", MyTestLet.class) ;
		
		assertEquals(1, at.getAradon().getChildren().size()) ; 
		assertEquals(1, at.getAradon().getChildService("s1").getChildren().size()) ;
		
		Response response = at.get("/s1/shutdown/bleujin") ;
		assertEquals("Hello bleujin", response.getEntityAsText()) ;
	}
	
	
	@Test
	public void matchMode() throws Exception {
		AradonTester at = AradonTester.create().register("s1", "/shutdown/{name}/", IMatchMode.STARTWITH, MyTestLet.class) ;
		Response response = at.get("/s1/shutdown/jinik/test") ;
		assertEquals("Hello jinik", response.getEntityAsText()) ;
	}
	
	@Test
	public void hello() throws Exception {
		AradonTester at = AradonTester.create().register("", "/{name}", MyTestLet.class) ;
		Response response = at.get("/bleujin") ;
		assertEquals("Hello bleujin", response.getEntityAsText()) ;
	}
	
	@Test
	public void pathName() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/{name}", "letname", IMatchMode.EQUALS, MyTestLet.class).getAradon() ;
		
		PathService ps = aradon.getChildService("").path("letname") ;
		assertEquals(true, ps != null) ;
	}
	
	@Test
	public void mergeSection() throws Exception {
		Aradon aradon = AradonTester.create().mergeSection("test")
			.addLet("/c1/{hame}", "mylet", MyTestLet.class)
			.addLet("/c2", "hello", MyTestLet.class).getAradon() ;
		
		
		
	}
	
	
}


class MyTestLet extends AbstractServerResource {
	
	@Get
	public String hello(){
		return "Hello " + getInnerRequest().getAttribute("name") ;
	}
	
}


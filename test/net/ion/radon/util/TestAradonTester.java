package net.ion.radon.util;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.radon.core.let.AbstractServerResource;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.resource.Get;

public class TestAradonTester {

	@Test
	public void testRegister() throws Exception {
		AradonTester at = AradonTester.create().register("s1", "/shutdown/{name}", MyTestLet.class) ;
		
		assertEquals(1, at.getAradon().getChildren().size()) ; 
		assertEquals(1, at.getAradon().getChildService("s1").getChildren().size()) ;
		
		Response response = at.get("/s1/shutdown/bleujin") ;
		assertEquals("Hello bleujin", response.getEntityAsText()) ;

		
		
	}
	
	/**
	 * 2011.10.05 jinik add
	 * */
	@Test
	public void testMatchMode() throws Exception {
		AradonTester at = AradonTester.create().register("s1", "/shutdown/{name}/", "STARTWITH", MyTestLet.class) ;
		Response response = at.get("/s1/shutdown/jinik/test") ;
		assertEquals("Hello jinik", response.getEntityAsText()) ;
	}
	
	@Test
	public void testHello() throws Exception {
		AradonTester at = AradonTester.create().register("", "/{name}", MyTestLet.class) ;
		Response response = at.get("/bleujin") ;
		assertEquals("Hello bleujin", response.getEntityAsText()) ;
	}
}


package net.ion.radon.client;

import static org.junit.Assert.*;
import net.ion.bleujin.HelloWorldLet2;
import net.ion.framework.util.Debug;
import net.ion.radon.core.Aradon;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.data.Form;

import junit.framework.TestCase;

public class TestRequestParameter {

	@Test
	public void get() throws Exception {
		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1").createRequest("/?p1=a&p2=3") ;
		Form form = req.getForm() ;
		
		assertEquals(2, form.getNames().size()) ;
		assertEquals("p1=a&p2=3", form.getQueryString()) ;
	}
	
	@Test
	public void getAdd() throws Exception {
		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1").createRequest("/?p1=a&p2=b") ;
		Form form = req.getForm() ;
		form.add("p3", "c") ;
		
		assertEquals(3, form.getNames().size()) ;
		assertEquals("p1=a&p2=b&p3=c", form.getQueryString()) ;
	}

	@Test
	public void getSameName() throws Exception {
		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1").createRequest("/?p1=a&p2=b") ;
		Form form = req.getForm() ;
		form.add("p2", "c") ;
		
		assertEquals(2, form.getNames().size()) ;
		Debug.debug("b", form.getFirstValue("p2")) ;
		Debug.debug("b,c", form.getValues("p2")) ;
	}
	
	@Test
	public void form() throws Exception {
		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1").createRequest("/?p1=a&p2=b") ;
		req.addParameter("p2", "c") ;

		Form form = req.getForm() ;
		assertEquals(2, form.getNames().size()) ;
		Debug.debug("b", form.getFirstValue("p2")) ;
		Debug.debug("b,c", form.getValues("p2")) ;

	}
	
	@Test
	public void setForm() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet2.class) ;
		Aradon aradon = at.getAradon() ;
		
		
		IAradonRequest req = AradonClientFactory.create(aradon).createRequest("/hello") ;
		req.addParameter("name", "bleujin") ;
		
		assertEquals("Hello World2 GET bleujin", req.get().getText()) ;
		at.getAradon().stop() ;
	}


	@Test
	public void setForm2() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet2.class) ;
		Aradon aradon = at.getAradon() ;
		
		IAradonRequest req = AradonClientFactory.create(aradon).createRequest("/hello?abc=3&def=1234") ;
		req.addParameter("name", "bleujin") ;
		
		assertEquals("Hello World2 GET bleujin", req.get().getText()) ;
		at.getAradon().stop() ;
	}
	

	


}

package net.ion.radon.client;

import net.ion.bleujin.HelloWorldLet2;
import net.ion.framework.util.Debug;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.restlet.data.Form;

import junit.framework.TestCase;

public class TestRequestParameter extends TestCase{

	
	public void testGet() throws Exception {
		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1").createRequest("/?p1=a&p2=3") ;
		Form form = req.getForm() ;
		
		assertEquals(2, form.getNames().size()) ;
		assertEquals("p1=a&p2=3", form.getQueryString()) ;
	}
	
	public void testGetAdd() throws Exception {
		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1").createRequest("/?p1=a&p2=b") ;
		Form form = req.getForm() ;
		form.add("p3", "c") ;
		
		assertEquals(3, form.getNames().size()) ;
		assertEquals("p1=a&p2=b&p3=c", form.getQueryString()) ;
	}

	public void testGetSameName() throws Exception {
		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1").createRequest("/?p1=a&p2=b") ;
		Form form = req.getForm() ;
		form.add("p2", "c") ;
		
		assertEquals(2, form.getNames().size()) ;
		Debug.debug("b", form.getFirstValue("p2")) ;
		Debug.debug("b,c", form.getValues("p2")) ;
	}
	
	public void testForm() throws Exception {
		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1").createRequest("/?p1=a&p2=b") ;
		req.addParameter("p2", "c") ;

		Form form = req.getForm() ;
		assertEquals(2, form.getNames().size()) ;
		Debug.debug("b", form.getFirstValue("p2")) ;
		Debug.debug("b,c", form.getValues("p2")) ;

	}
	
	public void testSetForm() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet2.class) ;
		at.startServer(9000) ;
		
		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1:9000").createRequest("/hello") ;
		req.addParameter("name", "bleujin") ;
		
		assertEquals("Hello World2 GET bleujin", req.get().getText()) ;
		at.getAradon().stop() ;
	}


	public void testSetForm2() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet2.class) ;
		at.startServer(9000) ;
		
		IAradonRequest req = AradonClientFactory.create("http://127.0.0.1:9000").createRequest("/hello?abc=3&def=1234") ;
		req.addParameter("name", "bleujin") ;
		
		assertEquals("Hello World2 GET bleujin", req.get().getText()) ;
		at.getAradon().stop() ;
	}


}

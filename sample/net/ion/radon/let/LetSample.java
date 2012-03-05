package net.ion.radon.let;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.core.let.DefaultLet;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

public class LetSample {

	@Test
	public void whenInnerClass() throws Exception{
		Aradon aradon = AradonTester.create()
				.register("test", "/ex1", HelloAbstractLet.class)
				.register("test", "/ex2/{name}", HelloResource.class)
				.register("test", "/ex3", HelloObjectLet.class)
				.getAradon() ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		assertEquals("hello", ac.createRequest("/test/ex1").get().getText()) ;
		assertEquals("hi bleujin", ac.createRequest("/test/ex2/bleujin").get().getText()) ;
		
		List<String> names = ac.createSerialRequest("/test/ex3?name=jin").get(List.class) ;
		assertEquals("[bleujin, hero, jin]", names.toString()) ;
		
//		aradon.startServer(9000) ;
//		new InfinityThread().startNJoin() ;
	}
}


class HelloAbstractLet extends DefaultLet {
	
	public Representation myGet(){
		return new StringRepresentation("hello") ;
	}
}

class HelloResource extends AbstractServerResource {

	@Get
	String print(){
		return "hi " + getInnerRequest().getAttribute("name") ;
	}
}

class HelloObjectLet extends AbstractServerResource {
	
	@Get
	List<String> names(){
		List<String> list = new ArrayList<String>() ;
		list.add("bleujin") ;
		list.add("hero") ;
		list.add(getInnerRequest().getParameter("name")) ;
		
		return list ;
	}
}

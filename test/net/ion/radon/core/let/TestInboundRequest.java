package net.ion.radon.core.let;

import java.io.Serializable;

import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.config.SectionConfiguration;
import net.ion.radon.impl.let.HelloWorldLet;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.data.Method;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

public class TestInboundRequest {

	@Test
	public void callOtherLet() throws Exception {
		Aradon aradon = Aradon.create() ;
		
		aradon.attach(SectionConfiguration.createBlank("s1"))
			.attach(PathConfiguration.create("hello", "/hello", HelloWorldLet.class))
			.attach(PathConfiguration.create("out", "/out", OutLet.class)) ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		Debug.debug(ac.createRequest("/s1/out").get()) ;
	}
	
	@Test
	public void callOtherObjectLet() throws Exception {
		Aradon aradon = Aradon.create() ;
		
		aradon.attach(SectionConfiguration.createBlank("s1"))
			.attach(PathConfiguration.create("object", "/object/{name}", ObjectLet.class))
			.attach(PathConfiguration.create("out", "/out", ObjectOutLet.class)) ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		Debug.debug(ac.createRequest("/s1/out").get()) ;
	}

}


class OutLet extends AbstractServerResource {
	
	@Get
	public String callInboundLet(){
		Request req = new Request(Method.GET, "riap://component/s1/hello") ;
		return getAradon().handle(req).getEntityAsText() ;
	}
}


class ObjectOutLet extends AbstractServerResource {
	
	@Get
	public Representation callInbound(){
		Request req = new Request(Method.GET, "riap://component/s1/object/bleujin") ;
		Representation entity = getAradon().handle(req).getEntity();
		
		Debug.line(entity) ;
		return entity ;
	}
}


class ObjectLet extends AbstractServerResource {
	@Get
	public Person createPerson(){
		return new Person(getInnerRequest().getAttribute("name")) ;
	}
}

class Person implements Serializable{

	private static final long serialVersionUID = -7192750782964294837L;
	private String name ;
	public Person(String name){
		this.name = name ;
	}
	
	public String name(){
		return name ;
	}
	
}
package net.ion.radon.bleujin;

import junit.framework.Assert;
import net.ion.nradon.let.IServiceLet;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.Get;

public class TestSimple  {

	@Test
	public void testStartWith() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/hello", "", IMatchMode.STARTWITH, Hello.class).getAradon();
		aradon.start() ;
		
		AradonClient ac = AradonClientFactory.create(aradon);
		Response response = ac.createRequest("/hello/hi").handle(Method.GET);

		Assert.assertEquals("Hello", response.getEntityAsText()) ; 
	}
}


class Hello implements IServiceLet {
	
	@Get
	public String hello(){
		return "Hello" ;
	}
}
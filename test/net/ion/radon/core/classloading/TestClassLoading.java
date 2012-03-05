package net.ion.radon.core.classloading;

import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestClassLoading {

	
	@Test
	public void confirm() throws Exception{
		Aradon aradon = AradonTester.create().register("", "/test", HelloWorldLet.class).getAradon() ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		IAradonRequest req = ac.createRequest("/test") ;
		
		req.handle(Method.GET) ;
		req.handle(Method.GET) ;
		Response res = req.handle(Method.GET) ;
	}
}

package net.ion.radon.client;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import net.ion.framework.util.Debug;
import net.ion.framework.util.MapUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestAradonClientFactory {

	@Test
	public void cache() throws Exception {
		AradonClient ac1 = AradonClientFactory.create("http://www.i-on.net") ;
		AradonClient ac2 = AradonClientFactory.create("http://www.i-on.net") ;
		AradonClient ac3 = AradonClientFactory.create("http://www.i-on.net/") ;
		AradonClient ac4 = AradonClientFactory.create("http://www.i-on.net:80/") ;
		
		assertEquals(true, ac1 == ac2) ;
		assertEquals(true, ac2 == ac3) ;
		assertEquals(true, ac3 == ac4) ;
		
		ac1.stop() ;
		ac2.stop() ;
		ac3.stop() ;
		ac4.stop() ;
	}

	@Test
	public void testRestart() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", HelloWorldLet.class).getAradon() ;
		aradon.startServer(9000) ;
		
		AradonClient ac = AradonClientFactory.create("http://localhost:9000") ;
		ac.createRequest("/test").get() ;
		ac.stop() ;
		
		Response res = ac.createRequest("/test").handle(Method.GET) ;
		assertEquals(200, res.getStatus().getCode()) ;
		
		for (int i = 0; i < 1000 ; i++) {
			ac.createRequest("/test").handle(Method.GET) ;
			ac.createRequest("/test").get() ;
		}
		
		ac.stop() ;
		aradon.stop() ;
	}
	
	@Test
	public void removeValue() throws Exception {
		Map x = MapUtil.newMap() ;
		x.put(1, "value1");
		x.put(2, "value2");
		x.put(3, "value3");
		x.put(4, "value4");
		x.put(5, "value5");
		x.put(6, "value6");

		x.values().remove("value4");

		Debug.line(x) ;
	}
	
}

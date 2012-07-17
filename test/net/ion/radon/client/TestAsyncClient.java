package net.ion.radon.client;

import java.util.List;
import java.util.concurrent.Future;

import net.ion.framework.util.ListUtil;
import net.ion.radon.core.Aradon;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Assert;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestAsyncClient {

	@Test
	public void onCompleted() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/hello", HelloWorldLet.class).getAradon() ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		Future<Integer> future = ac.createRequest("/hello").asyncHandle(Method.GET, new AsyncHttpHandler<Integer>() {
			public Integer onCompleted(Request request, Response response) {
				return response.getStatus().getCode();
			}

			public void onError(Request request, Response response) {
			}
		}) ;
		
		ac.stop() ;
		
		Assert.assertEquals(new Integer(200), future.get()) ;
		aradon.stop() ;
	}

	@Test
	public void onError() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/hello", HelloWorldLet.class).getAradon() ;
		aradon.startServer(9000) ;
		final List<Integer> codes = ListUtil.newList() ;
		
		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000") ;
		Future<Integer> future = ac.createRequest("/notfound").asyncHandle(Method.GET, new AsyncHttpHandler<Integer>() {
			public Integer onCompleted(Request request, Response response) {
				return response.getStatus().getCode();
			}
			public void onError(Request request, Response response) {
				codes.add(response.getStatus().getCode()) ;
			}
		}) ;
		
		ac.stop() ;

		Assert.assertTrue(future.get() == null) ;
		Assert.assertEquals(new Integer(404), codes.get(0)) ;
		aradon.stop() ;
	}

}

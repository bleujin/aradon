package net.ion.radon.core;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.plaf.ListUI;

import junit.framework.TestCase;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.RandomUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.config.ConnectorConfig;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.Get;

public class TestAradonThreadHandle extends TestCase{

	public void testServer() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", LongTimeLet.class).getAradon() ;
		aradon.startServer(ConnectorConfig.makeNettyHTTPConfig(9000)) ;
		
		new InfinityThread().startNJoin() ;
	}
	
//	public void testClient() throws Exception {
//		Callable<String> call = new Callable<String>() {
//			public String call() throws Exception {
//				// TODO Auto-generated method stub
//				long start = System.currentTimeMillis() ;
//				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000") ;
//				Response response = ac.createRequest("/test").handle(Method.GET) ;
//				return "" + (System.currentTimeMillis() - start) + "  " + response.getStatus() ;
//				
//			}
//		};
//		
//		
//		ExecutorService es = Executors.newFixedThreadPool(10) ;
//		
//		List<Future<String>> futures = ListUtil.newList() ;
//		for (int i : ListUtil.rangeNum(100)) {
//			futures.add(es.submit(call)) ;
//			Thread.sleep(20) ;
//		}
//
//		for (Future f : futures) {
//			Debug.line(f.get()); 
//		}
//	}
}

class LongTimeLet extends AbstractServerResource {
	
	@Get
	public String view(){
		try {
			long start = System.currentTimeMillis() ;
			Thread.sleep(RandomUtil.nextInt(600) + 400) ;
			long end = System.currentTimeMillis() ;
			return String.valueOf(end - start) ;
		} catch (InterruptedException e) {
			return e.getMessage() ;
		}
	}
	
}

package net.ion.radon.server;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.config.ConnectorConfig;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.resource.Get;

public class TestManyRequest {
	
	
	@Test
	public void testManyRequest() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello/{num}", DummyLet.class) ;
		at.getAradon().startServer(ConnectorConfig.makeSimpleHTTPConfig(9005)) ;
		
		AradonClient client = AradonClientFactory.create("http://127.0.0.1:9005");
		for (int i : ListUtil.rangeNum(50000)) {
			IAradonRequest request = client.createRequest("/hello/" + i);
			Response res = request.handle(Method.GET) ;
			// assertEquals(200, res.getStatus().getCode()) ;
			
			if (res.getStatus().getCode() != 200){
				Debug.line(res.getEntityAsText()) ;
			}
			
			if ((i % 100) == 0) {
				System.out.print('.');
			}
			res.release() ;
		}
		client.stop() ;
		at.getAradon().stop() ;
	}
}

class DummyLet extends AbstractServerResource {
	
	@Get
	public String hello(){
		return "hello " + getInnerRequest().getAttribute("num") ;
	}
}

package net.ion.radon.core.server;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;

import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.util.AradonTester;

public class TestServerFactory {

	@Test
	public void serverType() throws Exception{
		Aradon aradon = new Aradon() ;
		aradon.init("resource/config/aradon-config.xml") ;
		
		aradon.startServer(9000) ;
		
		Response res = AradonClientFactory.create("http://127.0.0.1:9000").createRequest("/").handle(Method.GET) ;
		Debug.line(res.getEntityAsText(), res.getStatus()) ; 
		Debug.line(AradonClientFactory.create("http://127.0.0.1:9000").createRequest("/").handle(Method.GET).getEntityAsText()) ;
		
		aradon.stop() ;
	}
	
}

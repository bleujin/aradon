package net.ion.radon.core;

import net.ion.framework.util.Debug;
import net.ion.nradon.let.IServiceLet;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.annotation.AnRequest;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.resource.Get;

public class TestAradon {

	
	private String TEST_CONFIG_FILE = "./resource/config/readonly-config.xml" ;
	
	@Test
	public void setUp() throws Exception {
		Debug.setPrintLevel(Debug.Level.Debug) ;
		Aradon aradon = Aradon.create(TEST_CONFIG_FILE) ;
		aradon.start() ;
	}
	
	@Test
	public void testFindPort() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/hello", FindLet.class).getAradon();
		aradon.startServer(9050) ;
		
		AradonClientFactory.create("http://localhost:9050").createRequest("/hello").get() ;
		aradon.stop() ;
	}


}

class FindLet implements IServiceLet {
	
	@Get
	public String findPort(@AnRequest InnerRequest request){
		
		Debug.line(request.getResourceRef().getHostIdentifier()) ;
		return "hhh" ;
	}
}


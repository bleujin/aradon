package net.ion.radon.impl.let;

import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;

public class TestDirLet {

	
	@Test
	public void getFile() throws Exception{
		Aradon aradon = AradonTester.create().register("", "/{file}", DirLet.class).getAradon() ;
		aradon.getServiceContext().putAttribute("base.dir", "./resource") ;
		
		aradon.start() ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		Response response = ac.createRequest("/ptest.prop").handle(Method.GET) ;
		
		Debug.debug(response.getEntityAsText()) ;
		
		aradon.stop() ;
		
	}
	
}

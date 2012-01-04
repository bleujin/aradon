package net.ion.radon.core.let;

import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.util.AradonTester;

import org.junit.Test;

public class TestParameter {

	
	@Test
	public void testNumber() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", ParameterLet.class).getAradon() ;
		
		IAradonRequest request = AradonClientFactory.create(aradon).createRequest("/test") ;
		request.addParameter("num", "3") ;
		request.post() ;
		
	}
	
}

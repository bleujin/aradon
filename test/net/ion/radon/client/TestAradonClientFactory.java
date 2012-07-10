package net.ion.radon.client;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
	}

	
}

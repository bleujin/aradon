package net.ion.radon.core.server;

import static org.junit.Assert.assertEquals;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;

import org.junit.Ignore;
import org.junit.Test;

public class TestClientFactory {

	@Ignore
	@Test
	public void cache() throws Exception {
		AradonClient ac1 = AradonClientFactory.create("http://www.i-on.net");
		AradonClient ac2 = AradonClientFactory.create("http://www.i-on.net");
		AradonClient ac3 = AradonClientFactory.create("http://www.i-on.net/");
		AradonClient ac4 = AradonClientFactory.create("http://www.i-on.net:80/");

		assertEquals(true, ac1 == ac2);
		assertEquals(true, ac2 == ac3);
		assertEquals(true, ac3 == ac4);
		ac1.stop() ;
		ac2.stop() ;
		ac3.stop() ;
		ac4.stop() ;
	}

	
}




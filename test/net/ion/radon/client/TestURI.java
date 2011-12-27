package net.ion.radon.client;

import java.net.URI;

import junit.framework.TestCase;

import net.ion.framework.util.Debug;

public class TestURI extends TestCase{

	public void testURIEqual() throws Exception {
		assertEquals(false, new URI("http://www.i-on.net/").equals(new URI("http://www.i-on.net:80/"))) ;
		assertNull(new URI("www.123.com/123.htm").getHost()) ;
		
		assertEquals("123.htm", new URI("123.htm").getPath()) ;
		assertEquals("123.htm", new URI("123.htm").getRawPath()) ;
		assertEquals("a=1&b=2", new URI("123.htm?a=1&b=2").getRawQuery()) ;
		
	}
	
	public void testURI() throws Exception {
		URI uri = new URI("http://www.i-on.net:9090/index.htm") ;
		assertEquals("http://www.i-on.net:9090", uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort()) ;
	}

	
}

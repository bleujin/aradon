package net.ion.radon.client;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;

import org.restlet.representation.Representation;
import org.restlet.security.User;

public class TestAradonClient extends TestCase {

	
	public void testInit() throws Exception {
		AradonClient ac = AradonClientFactory.create("http://61.250.201.157:9002") ;
		IAradonRequest ar = ac.createRequest("/") ;
		Representation r = ar.get() ;
		
		Debug.debug(r.getText(), r.getMediaType()) ;
	}
	
	public void xtestAuth() throws Exception {
		AradonClient ac = AradonClientFactory.create("http://61.250.201.157:9002") ;
		
		IAradonRequest ar = ac.createRequest("/other/hello?abcd=한글한글", "bleujin", "redf") ;

		for (int i = 0; i < 5 ; i++) {
			ar.get() ;
		}
		Representation r = ar.get() ;
		Debug.debug(r.getText(), r.getMediaType()) ;
	}

	public void xtestContext() throws Exception {
		AradonClient ac = AradonClientFactory.create("http://61.250.201.157:9002") ;
		IAradonRequest ar = ac.createRequest("/other/hello?abcd=한글한글", "bleujin", "redf") ;
		
		User user = ar.getUser() ;
		assertEquals("bleujin", user.getIdentifier().toString()) ;
	}

}

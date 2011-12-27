package net.ion.radon.client;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;

public class TestBasicRequest extends TestCase{

	
	public void testRepeatRequest() throws Exception {
		AradonClient client = AradonClientFactory.create("http://im.i-on.net") ;
		
		for (int i = 0; i < 10; i++) {
			Debug.debug(client.createRequest("/", "bleujin", "redfpark").get().getText()) ; 
		}
		//new InfinityThread().startNJoin() ;
	}
	
	
	public void testRepeatRequest2() throws Exception {
		AradonClient client = AradonClientFactory.create("http://im.i-on.net") ;
		
		for (int i = 0; i < 10; i++) {
			Debug.debug(client.createSerialRequest("/", "bleujin", "redfpark").get(String.class)) ; 
		}
		//new InfinityThread().startNJoin() ;
	}
	
	
}


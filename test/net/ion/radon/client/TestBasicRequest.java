package net.ion.radon.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.ecs.wml.A;
import org.restlet.engine.adapter.ClientCall;
import org.restlet.representation.Representation;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.framework.util.MapUtil;
import junit.framework.TestCase;

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


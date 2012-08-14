package net.ion.radon.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

import net.ion.radon.core.Aradon;

import org.apache.commons.collections.map.LRUMap;

public class AradonClientFactory {

	private static Map<Object, AradonClient> clientMap = Collections.synchronizedMap(new LRUMap(20)) ;
	
	public static synchronized AradonClient create(String hostAddress) {
			return create(hostAddress, false) ;
	}

	static synchronized void remove(AradonClient client){
		clientMap.values().remove(client) ;
//		clientMap.remove(makeKey(hostAddress, true)) ;
//		clientMap.remove(makeKey(hostAddress, false)) ;
	}

	public static synchronized AradonClient create(String hostAddress, boolean reliable) {
		
//		AradonHttpClient newClient = AradonHttpClient.create(hostAddress);
//		if (reliable) newClient.setReliable() ;
//		return newClient ;
		
		String key = makeKey(hostAddress, reliable) ;
		
		if ( ! clientMap.containsKey(key)){
			AradonHttpClient newClient = AradonHttpClient.create(hostAddress);
			if (reliable) newClient.setReliable() ;
			clientMap.put(key, newClient) ;
		} 
		return clientMap.get(key) ;
	}

	public static synchronized AradonClient create(Aradon aradon) {
		
//		return AradonInnerClient.create(aradon) ;
		
		Object key = makeKey(aradon) ;
		
		if ( ! clientMap.containsKey(key)){
			clientMap.put(key, AradonInnerClient.create(aradon)) ;
		} 
		return clientMap.get(key) ;
	}
	
	private static String makeKey(String hostAddress, boolean reliable) {
		try {
			URI uri = new URI(hostAddress) ;
			String key = uri.getScheme() + "://" + uri.getHost() + ":" + ((uri.getPort() <= 0) ? 80 : uri.getPort()) + "/" + reliable;
			return key;
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e) ;
		}
	}

	private static Object makeKey(Aradon aradon) {
		return aradon ;
	}


}

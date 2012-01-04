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
			String key = makeKey(hostAddress) ;
			
			if ( ! clientMap.containsKey(key)){
				clientMap.put(key, AradonHttpClient.create(hostAddress)) ;
			} 
			return clientMap.get(key) ;
	}

	private static String makeKey(String hostAddress) {
		try {
			URI uri = new URI(hostAddress) ;
			String key = uri.getScheme() + "://" + uri.getHost() + ":" + ((uri.getPort() <= 0) ? 80 : uri.getPort());
			return key;
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e) ;
		}
	}

	private static Object makeKey(Aradon aradon) {
		return aradon ;
	}
	
	public static AradonClient create(Aradon aradon) {
		Object key = makeKey(aradon) ;
		
		if ( ! clientMap.containsKey(key)){
			clientMap.put(key, AradonInnerClient.create(aradon)) ;
		} 
		return clientMap.get(key) ;
	}

}

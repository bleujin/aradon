package net.ion.radon.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.ion.radon.core.Aradon;

import org.apache.commons.collections.map.LRUMap;
import org.apache.tools.ant.taskdefs.ExecTask;

public class AradonClientFactory {

	private static Map<Object, AradonClient> clientMap = Collections.synchronizedMap(new LRUMap(20)) ;
	private static ExecutorService eService = Executors.newCachedThreadPool() ;
	
	public static synchronized AradonClient create(String hostAddress) {
			return create(hostAddress, false) ;
	}

	public static synchronized AradonClient create(String hostAddress, boolean reliable) {
		return create(hostAddress, reliable, eService) ;
	}

	public static synchronized AradonClient create(String hostAddress, boolean reliable, ExecutorService es) {
		String key = makeKey(hostAddress, reliable) ;
		
		if ( ! clientMap.containsKey(key)){
			AradonHttpClient newClient = AradonHttpClient.create(hostAddress, es);
			if (reliable) newClient.setReliable() ;
			clientMap.put(key, newClient) ;
		} 
		return clientMap.get(key) ;
	}

	public static synchronized AradonClient create(Aradon aradon) {
		return create(aradon, eService) ;
	}
	
	public static synchronized AradonClient create(Aradon aradon, ExecutorService es) {
		Object key = makeKey(aradon) ;
		
		if ( ! clientMap.containsKey(key)){
			clientMap.put(key, AradonInnerClient.create(aradon, es)) ;
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

	public static void shutdownNow(ExecutorService es) {
		if (es != eService){
			es.shutdown() ;
		}
	}
	

}

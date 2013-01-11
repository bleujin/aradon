package net.ion.nradon.filter;

import java.util.List;
import java.util.Set;

import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.WebSocketConnection;
import net.ion.radon.core.IService;
import net.ion.radon.core.config.ConfigCreator;
import net.ion.radon.core.config.XMLConfig;
import net.ion.radon.core.except.ConfigurationException;

import org.apache.commons.collections.set.ListOrderedSet;

public class XFilterUtil {

	public final static void httpStart(IService iservice, final HttpRequest request, final HttpResponse response, final HttpControl control) {
		IService current = iservice;
		while (current == null || current != IService.ROOT) {
			List<XRadonFilter> prefilters = current.getConfig().filters();
			for (XRadonFilter filter : prefilters) {
				filter.httpStart(current, request, response) ;
			}
			current = current.getParent();
		}
	}

	public final static void httpEnd(IService iservice, HttpRequest request, HttpResponse response, HttpControl control) {
		IService current = iservice;
		while (current == null || current != IService.ROOT) {
			List<XRadonFilter> prefilters = current.getConfig().filters();
			for (XRadonFilter filter : prefilters) {
				filter.httpEnd(current, request, response) ;
			}
			current = current.getParent();
		}
	}


	public final static void wsOpen(IService iservice, WebSocketConnection conn){
		IService current = iservice;
		while (current == null || current != IService.ROOT) {
			List<XRadonFilter> prefilters = current.getConfig().filters();
			for (XRadonFilter filter : prefilters) {
				filter.wsOpen(current, conn) ;
			}
			current = current.getParent();
		}
	}

	public final static void wsClose(IService iservice, WebSocketConnection conn){
		IService current = iservice;
		while (current == null || current != IService.ROOT) {
			List<XRadonFilter> prefilters = current.getConfig().filters();
			for (XRadonFilter filter : prefilters) {
				filter.wsClose(current, conn) ;
			}
			current = current.getParent();
		}
	}

	public final static void wsInbound(IService iservice, WebSocketConnection conn, String data){
		IService current = iservice;
		while (current == null || current != IService.ROOT) {
			List<XRadonFilter> prefilters = current.getConfig().filters();
			for (XRadonFilter filter : prefilters) {
				filter.wsInbound(current, conn, data) ;
			}
			current = current.getParent();
		}
	}

	public final static void wsInbound(IService iservice, WebSocketConnection conn, byte[] message){
		IService current = iservice;
		while (current == null || current != IService.ROOT) {
			List<XRadonFilter> prefilters = current.getConfig().filters();
			for (XRadonFilter filter : prefilters) {
				filter.wsInbound(current, conn, message) ;
			}
			current = current.getParent();
		}
	}

	public final static void wsInboundPong(IService iservice, WebSocketConnection conn, byte[] message){
		IService current = iservice;
		while (current == null || current != IService.ROOT) {
			List<XRadonFilter> prefilters = current.getConfig().filters();
			for (XRadonFilter filter : prefilters) {
				filter.wsInboundPong(current, conn, message) ;
			}
			current = current.getParent();
		}
	}

	public final static void wsInboundPing(IService iservice, WebSocketConnection conn, byte[] message){
		IService current = iservice;
		while (current == null || current != IService.ROOT) {
			List<XRadonFilter> prefilters = current.getConfig().filters();
			for (XRadonFilter filter : prefilters) {
				filter.wsInboundPing(current, conn, message) ;
			}
			current = current.getParent();
		}
	}

	public final static void esOpen(IService iservice, EventSourceConnection conn){
		IService current = iservice;
		while (current == null || current != IService.ROOT) {
			List<XRadonFilter> prefilters = current.getConfig().filters();
			for (XRadonFilter filter : prefilters) {
				filter.esOpen(current, conn) ;
			}
			current = current.getParent();
		}
	}

	public final static void esClose(IService iservice, EventSourceConnection conn){
		IService current = iservice;
		while (current == null || current != IService.ROOT) {
			List<XRadonFilter> prefilters = current.getConfig().filters();
			for (XRadonFilter filter : prefilters) {
				filter.esClose(current, conn) ;
			}
			current = current.getParent();
		}
	}

	
	
//	public abstract void error(HttpRequest request, Throwable error);
//	public abstract void custom(HttpRequest request, String action, String data);


	public static Set<XRadonFilter> getFilters(List<XMLConfig> configs)  {
		try {
			Set<XRadonFilter> result = new ListOrderedSet();
			for (XMLConfig config : configs) {
				XRadonFilter filter = ConfigCreator.createInstance(config);
				result.add(filter);
			}
			return result;
		} catch (Exception ex) {
			throw ConfigurationException.throwIt(ex);
		}
	}

	
	

}


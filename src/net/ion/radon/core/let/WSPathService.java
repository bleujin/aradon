package net.ion.radon.core.let;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.MapUtil;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.AbstractWebSocketResource;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.Radon;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.handler.event.ServerEventHandler;
import net.ion.nradon.handler.event.ServerEvent.EventType;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.HttpRestSection;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.WSPathConfiguration;

public class WSPathService extends AbstractWebSocketResource implements ServerEventHandler{
	public static final String VAR_SESSIONID = "$sessionId";

	private HttpRestSection restSection ;
	private AbstractWebSocketResource handler ;
	private WSPathConfiguration wsconfig ;

	WSPathService(Aradon aradon, HttpRestSection restSection, AbstractWebSocketResource handler, WSPathConfiguration wsconfig) {
		this.handler = handler ;
		this.wsconfig = wsconfig ;
	}

	public static WSPathService create(Aradon aradon, HttpRestSection restSection, TreeContext context, WSPathConfiguration wsconfig) {
		try {
			Constructor<? extends AbstractWebSocketResource> cons = wsconfig.handlerClz().getDeclaredConstructor();
			cons.setAccessible(true);
			AbstractWebSocketResource handler = cons.newInstance();
			handler.init(restSection, context, wsconfig);

			return new WSPathService(aradon, restSection, handler, wsconfig);
		} catch (Throwable th) {
			throw new IllegalStateException(th);
		}
	}

	public WSPathConfiguration getConfig() {
		return wsconfig;
	}

	public AbstractWebSocketResource handlerResource() {
		return this ;
	}

	public void onClose(WebSocketConnection conn) throws Exception {
		handler.onClose(conn) ;
	}

	public void onMessage(WebSocketConnection conn, String msg) throws Throwable {
		handler.onMessage(conn, msg) ;
	}

	public void onMessage(WebSocketConnection conn, byte[] msg) throws Throwable {
		handler.onMessage(conn, msg) ;
	}

	public void onOpen(WebSocketConnection conn) throws Exception {
		HttpRequest req = conn.httpRequest();
		Map<String, String> patternValues = URIParser.parse(req.uri(), getConfig().fullURLPattern());

		for (Entry<String, String> pvalue : patternValues.entrySet()) {
			conn.data(pvalue.getKey(), pvalue.getValue());
		}
		for (String key : req.queryParamKeys()) {
			conn.data(key, req.queryParam(key));
		}

		conn.data(VAR_SESSIONID, conn.httpRequest().remoteAddress().toString());
		
		handler.onOpen(conn) ;
	}

	public void onPong(WebSocketConnection conn, String msg) throws Throwable {
		handler.onPong(conn, msg) ;
	}

	public void onEvent(EventType event, Radon radon) {
		if (handler instanceof ServerEventHandler){
			((ServerEventHandler)handler).onEvent(event, radon) ;
		}
	}
	
	public AbstractWebSocketResource websocketResource(){
		return handler ;
	}


}



class URIParser {

	static String FIND_PATTERN =  "\\{[^/]+\\}";
	static String TRANS_PATTERN = "[^/\\?]+?";
	static String SPLIT_CHAR = "/?";
	
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> parse(String _url, String _pattern) {
		String[] urls = StringUtil.split(_url, SPLIT_CHAR);
		String[] urlPatterns = StringUtil.split(_pattern, SPLIT_CHAR);

		if (urlPatterns.length == 0) return Collections.EMPTY_MAP ; 
		
		Map<String, String> result = MapUtil.newMap();
		for (int i = 0; i < urls.length; i++) {
			if (i > urlPatterns.length-1) continue ;
			String pattern = urlPatterns[i];
			String url = urls[i];
			if (pattern.matches(FIND_PATTERN)) {
				result.put(pattern.substring(1, pattern.length() - 1), url);
			}
		}
		return result;
	}

}
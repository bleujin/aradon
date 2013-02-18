package net.ion.radon.core.let;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import net.ion.framework.util.MapUtil;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.AbstractWebSocketResource;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.Radon;
import net.ion.nradon.WebSocketConnection;
import net.ion.nradon.WebSocketHandler;
import net.ion.nradon.filter.XFilterUtil;
import net.ion.nradon.handler.HttpToWebSocketHandler;
import net.ion.nradon.handler.event.ServerEventHandler;
import net.ion.nradon.handler.event.ServerEvent.EventType;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.HttpRestSection;
import net.ion.radon.core.IService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.WSPathConfiguration;

public class WebSocketPathService implements ServerEventHandler, IService<WebSocketPathService>, WebSocketHandler, IRadonPathService{
	public static final String VAR_SESSIONID = "$sessionId";

	private AbstractWebSocketResource handler ;

	WebSocketPathService(Aradon aradon, HttpRestSection restSection, AbstractWebSocketResource handler, WSPathConfiguration wsconfig) {
		this.handler = handler ;
	}

	public static WebSocketPathService create(Aradon aradon, HttpRestSection restSection, TreeContext context, WSPathConfiguration wsconfig) {
		try {
			Constructor<? extends AbstractWebSocketResource> cons = wsconfig.handlerClz().getDeclaredConstructor();
			cons.setAccessible(true);
			AbstractWebSocketResource handler = cons.newInstance();

			WebSocketPathService result = new WebSocketPathService(aradon, restSection, handler, wsconfig);
			result.initService(restSection, context, wsconfig) ;
			return result;
		} catch (Throwable th) {
			throw new IllegalStateException(th);
		}
	}

	private void initService(SectionService parent, TreeContext context, WSPathConfiguration econfig){
		handler.init(parent, context, econfig) ;
		getConfig().initFilter(this) ;
	}

	
	public WSPathConfiguration getConfig() {
		return handler.getConfig();
	}

	public void onClose(WebSocketConnection conn) throws Throwable {
		XFilterUtil.wsClose(this, conn) ;
		handler.onClose(conn) ;
	}

	public void onMessage(WebSocketConnection conn, String msg) throws Throwable {
		XFilterUtil.wsInbound(this, conn, msg) ;
		handler.onMessage(conn, msg) ;
	}

	public void onMessage(WebSocketConnection conn, byte[] msg) throws Throwable {
		XFilterUtil.wsInbound(this, conn, msg) ;
		handler.onMessage(conn, msg) ;
	}

	public void onOpen(WebSocketConnection conn) throws Throwable {
		HttpRequest req = conn.httpRequest();
//		Map<String, String> patternValues = URIParser.parse(req.uri(), getConfig().fullURLPattern());
//		for (Entry<String, String> pvalue : patternValues.entrySet()) {
//			conn.data(pvalue.getKey(), pvalue.getValue());
//		}
		for (String key : req.queryParamKeys()) {
			conn.data(key, req.queryParam(key));
		}

		conn.data(VAR_SESSIONID, conn.httpRequest().remoteAddress().toString());
		
		XFilterUtil.wsOpen(this, conn) ;
		handler.onOpen(conn) ;
	}

	public void onPong(WebSocketConnection conn, byte[] msg) throws Throwable {
		XFilterUtil.wsInboundPong(this, conn, msg) ;
		handler.onPong(conn, msg) ;
	}

	public void onPing(WebSocketConnection conn, byte[] msg) throws Throwable {
		XFilterUtil.wsInboundPing(this, conn, msg) ;
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

	
	
	
	public Aradon getAradon() {
		return getParent().getAradon();
	}

	public WebSocketPathService getChildService(String childName) {
		throw new IllegalArgumentException("this is pathservice");
	}

	public Collection<WebSocketPathService> getChildren() {
		return Collections.EMPTY_LIST;
	}

	public String getName() {
		return getConfig().name();
	}

	public String getNamePath() {
		return "/" + getParent().getName() + "/" + getName();
	}

	public void reload() throws Exception {
		
	}

	public void restart() {
		
	}

	public void stop() throws Exception {
		
	}

	public void suspend() {
		
	}

	public IService getParent() {
		return handler.getParent();
	}

	public TreeContext getServiceContext() {
		return handler.getServiceContext();
	}
	
	public HttpHandler toHttpHandler() {
		return new HttpToWebSocketHandler(this);
	}


	public int order() {
		return 9;
	}

}


@Deprecated
class URIParser {

	static String FIND_PATTERN =  "\\{[^/]+\\}";
	static String TRANS_PATTERN = "[^/\\?]+?";
	static String SPLIT_CHAR = "/?";
	
	
	@Deprecated
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
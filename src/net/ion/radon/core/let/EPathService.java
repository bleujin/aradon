package net.ion.radon.core.let;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.nradon.AbstractEventSourceResource;
import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.Radon;
import net.ion.nradon.handler.event.ServerEventHandler;
import net.ion.nradon.handler.event.ServerEvent.EventType;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.HttpRestSection;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.EPathConfiguration;

public class EPathService extends AbstractEventSourceResource implements ServerEventHandler{
	private static final String VAR_SESSIONID = "$sessionId";

	private Aradon aradon;
	private HttpRestSection restSection ;
	private AbstractEventSourceResource handler ;
	private EPathConfiguration econfig ;

	EPathService(Aradon aradon, HttpRestSection restSection, AbstractEventSourceResource handler, EPathConfiguration wsconfig) {
		this.aradon = aradon ;
		this.restSection = restSection ;
		this.handler = handler ;
		this.econfig = wsconfig ;
	}

	public static EPathService create(Aradon aradon, HttpRestSection restSection, TreeContext context, EPathConfiguration wsconfig) {
		try {
			Constructor<? extends AbstractEventSourceResource> cons = wsconfig.handlerClz().getDeclaredConstructor();
			cons.setAccessible(true);
			AbstractEventSourceResource handler = cons.newInstance();
			handler.init(restSection, context, wsconfig);

			return new EPathService(aradon, restSection, handler, wsconfig);
		} catch (Throwable th) {
			throw new IllegalStateException(th);
		}
	}

	public EPathConfiguration getConfig() {
		return econfig;
	}

	public AbstractEventSourceResource handlerResource() {
		return this ;
	}

	public void onClose(EventSourceConnection conn) throws Exception {
		handler.onClose(conn) ;
	}

	public void onOpen(EventSourceConnection conn) throws Exception {
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

	public void onEvent(EventType event, Radon radon) {
		if (handler instanceof ServerEventHandler){
			((ServerEventHandler)handler).onEvent(event, radon) ;
		}
	}

}


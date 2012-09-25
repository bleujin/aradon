package net.ion.radon.core.let;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.nradon.AbstractEventSourceResource;
import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.Radon;
import net.ion.nradon.handler.HttpToEventSourceHandler;
import net.ion.nradon.handler.event.ServerEventHandler;
import net.ion.nradon.handler.event.ServerEvent.EventType;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.HttpRestSection;
import net.ion.radon.core.IService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.EPathConfiguration;

public class EPathService implements ServerEventHandler, IService<EPathService>, EventSourceHandler, IRadonPathService{
	private static final String VAR_SESSIONID = "$sessionId";

	private Aradon aradon;
	private AbstractEventSourceResource handler ;

	EPathService(Aradon aradon, HttpRestSection restSection, AbstractEventSourceResource handler, EPathConfiguration econfig) {
		this.aradon = aradon ;
		this.handler = handler ;
	}

	public static EPathService create(Aradon aradon, HttpRestSection restSection, TreeContext context, EPathConfiguration econfig) {
		try {
			Constructor<? extends AbstractEventSourceResource> cons = econfig.handlerClz().getDeclaredConstructor();
			cons.setAccessible(true);
			AbstractEventSourceResource handler = cons.newInstance();

			EPathService result = new EPathService(aradon, restSection, handler, econfig);
			result.initService(restSection, context, econfig) ;
			
			return result;
		} catch (Throwable th) {
			throw new IllegalStateException(th);
		}
	}

	private void initService(SectionService parent, TreeContext context, EPathConfiguration econfig){
		handler.init(parent, context, econfig) ;
	}
	
	public void onClose(EventSourceConnection conn) throws Exception {
		handler.onClose(conn) ;
	}

	public void onOpen(EventSourceConnection conn) throws Exception {
		HttpRequest req = conn.httpRequest();
		Map<String, String> patternValues = URIParser.parse(req.uri(), handler.getConfig().fullURLPattern());

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

	public Aradon getAradon() {
		return getParent().getAradon();
	}

	public EPathService getChildService(String childName) {
		throw new IllegalArgumentException("this is pathservice");
	}

	public Collection<EPathService> getChildren() {
		return Collections.EMPTY_LIST;
	}

	public String getName() {
		return handler.getConfig().name();
	}

	public String getNamePath() {
		return "/" + getParent().getName() + "/" + getName();
	}

	public void reload() throws Exception {
		// TODO Auto-generated method stub
	}

	public void restart() {
		// TODO Auto-generated method stub
	}

	public void stop() throws Exception {
		// TODO Auto-generated method stub
	}

	public void suspend() {
		// TODO Auto-generated method stub
	}

	public EPathConfiguration getConfig() {
		return handler.getConfig();
	}

	public IService getParent() {
		return handler.getParent();
	}

	public TreeContext getServiceContext() {
		return handler.getServiceContext();
	}

	public HttpHandler toHttpHandler() {
		return new HttpToEventSourceHandler(this);
	}
}


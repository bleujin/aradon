package net.ion.radon.core.let;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;

import net.ion.nradon.AbstractEventSourceResource;
import net.ion.nradon.EventSourceConnection;
import net.ion.nradon.EventSourceHandler;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.Radon;
import net.ion.nradon.filter.XFilterUtil;
import net.ion.nradon.handler.HttpToEventSourceHandler;
import net.ion.nradon.handler.event.ServerEventHandler;
import net.ion.nradon.handler.event.ServerEvent.EventType;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.HttpRestSection;
import net.ion.radon.core.IService;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.TreeContext;
import net.ion.radon.core.config.EPathConfiguration;

public class EventSourcePathService implements ServerEventHandler, IService<EventSourcePathService>, EventSourceHandler, IRadonPathService{
	private static final String VAR_SESSIONID = "$sessionId";

	private Aradon aradon;
	private AbstractEventSourceResource handler ;

	EventSourcePathService(Aradon aradon, HttpRestSection restSection, AbstractEventSourceResource handler, EPathConfiguration econfig) {
		this.aradon = aradon ;
		this.handler = handler ;
	}

	public static EventSourcePathService create(Aradon aradon, HttpRestSection restSection, TreeContext context, EPathConfiguration econfig) {
		try {
			Constructor<? extends AbstractEventSourceResource> cons = econfig.handlerClz().getDeclaredConstructor();
			cons.setAccessible(true);
			AbstractEventSourceResource handler = cons.newInstance();

			EventSourcePathService result = new EventSourcePathService(aradon, restSection, handler, econfig);
			result.initService(restSection, context, econfig) ;
			
			return result;
		} catch (Throwable th) {
			throw new IllegalStateException(th);
		}
	}

	private void initService(SectionService parent, TreeContext context, EPathConfiguration econfig){
		handler.init(parent, context, econfig) ;
		getConfig().initFilter(this) ;
	}
	
	public void onClose(EventSourceConnection conn) throws Exception {
		XFilterUtil.esClose(this, conn) ;
		handler.onClose(conn) ;
	}

	public void onOpen(EventSourceConnection conn) throws Exception {
		HttpRequest req = conn.httpRequest();
		for (String key : req.queryParamKeys()) {
			conn.data(key, req.queryParam(key));
		}
		
		conn.data(VAR_SESSIONID, req.remoteAddress().toString());
		
		XFilterUtil.esOpen(this, conn) ;
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

	public EventSourcePathService getChildService(String childName) {
		throw new IllegalArgumentException("this is pathservice");
	}

	public Collection<EventSourcePathService> getChildren() {
		return Collections.EMPTY_LIST;
	}

	public String getName() {
		return handler.getConfig().name();
	}

	public String getNamePath() {
		return "/" + getParent().getName() + "/" + getName();
	}

	public void reload(){
		// TODO Auto-generated method stub
	}

	public void restart() {
		// TODO Auto-generated method stub
	}

	public void stop(){
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
	
	public AbstractEventSourceResource eventsourceResource(){
		return handler ;
	}

	public TreeContext getServiceContext() {
		return handler.getServiceContext();
	}

	public HttpHandler toHttpHandler() {
		return new HttpToEventSourceHandler(this);
	}

	public int order() {
		return 8;
	}
}


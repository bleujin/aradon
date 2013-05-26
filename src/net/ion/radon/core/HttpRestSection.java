package net.ion.radon.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.config.RadonConfigurationBuilder;
import net.ion.nradon.handler.aradon.AradonHandler;
import net.ion.radon.core.EnumClass.IMatchMode;
import net.ion.radon.core.config.AttributeValue;
import net.ion.radon.core.config.EPathConfiguration;
import net.ion.radon.core.config.IPathConfiguration;
import net.ion.radon.core.config.LetPathConfiguration;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.config.SPathConfiguration;
import net.ion.radon.core.config.SectionConfiguration;
import net.ion.radon.core.config.WSPathConfiguration;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.let.EventSourcePathService;
import net.ion.radon.core.let.FilterUtil;
import net.ion.radon.core.let.IRadonPathService;
import net.ion.radon.core.let.PathService;
import net.ion.radon.core.let.SingleLetPathService;
import net.ion.radon.core.let.WebSocketPathService;
import net.ion.radon.core.routing.SectionRouter;
import net.ion.radon.core.routing.SectionTemplateRoute;
import net.ion.radon.impl.filter.RevokeServiceFilter;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.routing.Filter;

public class HttpRestSection extends SectionService  {

	private Aradon aradon;
	private TreeContext context;
	private SectionConfiguration sconfig;
	private SectionRouter router;
	private Map<String, PathService> paths = MapUtil.newMap();
	private Map<String, SingleLetPathService> spaths = MapUtil.newMap();
	private Map<String, WebSocketPathService> wspaths = MapUtil.newMap();
	private Map<String, EventSourcePathService> epaths = MapUtil.newMap();

	HttpRestSection(Aradon aradon, TreeContext context, SectionConfiguration sconfig) {
		super(context);
		this.aradon = aradon;
		this.context = context;
		this.sconfig = sconfig;
		this.router = new SectionRouter(context);
		init();
	}

	@Override
	public Restlet createInboundRoot() {
		return router;
	}

	private void init() {
		for (Entry<String, AttributeValue> entry : sconfig.attributes().entrySet()) {
			context.putAttribute(entry.getKey(), entry.getValue());
		}
		super.setName(sconfig.name());
		sconfig.initFilter(this);

		for (PathConfiguration pconfig : sconfig.pathConfiguration()) {
			attachPath(pconfig);
		}

		for (SPathConfiguration pconfig : sconfig.spathConfiguration()) {
			attachSPath(pconfig);
		}


		for (WSPathConfiguration wconfig : sconfig.wspathConfiguration()) {
			attachWSPath(wconfig);
		}
		
		for(EPathConfiguration econfig : sconfig.epathConfiguration()){
			attachEPath(econfig) ;
		}
		

	}

	private String makePathPattern(String urlPattern) {
		return StringUtil.isBlank(sconfig.name()) ? urlPattern.substring(1) : urlPattern;
	}

	public static HttpRestSection create(Aradon aradon, TreeContext context, SectionConfiguration sconfig) {
		HttpRestSection newSection = new HttpRestSection(aradon, context, sconfig);
		
		for( Entry<String, AttributeValue> entry : sconfig.attributes().entrySet()) {
			context.putAttribute(entry.getKey(), entry.getValue()) ;
		}
		
		return newSection;
	}

	@Override
	public void handle(Request request, Response response) {
		IFilterResult preResult = FilterUtil.preHandle(this, sconfig.prefilters(), request, response);
		if (preResult.getResult() == Filter.STOP) {
			response.setStatus(preResult.getCause().getStatus());
			response.setEntity(preResult.getReplaceRepresentation());
			return;
		}
		try {
			if (preResult.getResult() != Filter.SKIP) {
				router.handle(request, response);
			}
		} finally {
			FilterUtil.afterHandle(this, sconfig.afterfilters(), request, response);
		}
	}

	public Aradon getAradon() {
		return aradon;
	}

	public SectionConfiguration getConfig() {
		return sconfig;
	}


	@Override
	public SectionService attach(IPathConfiguration pconfig) {
		if (pconfig instanceof PathConfiguration){
			return attachPath((PathConfiguration)pconfig) ;
		} else if (pconfig instanceof WSPathConfiguration){
			return attachWSPath((WSPathConfiguration)pconfig) ;
		} else if (pconfig instanceof EPathConfiguration){
			return attachEPath((EPathConfiguration)pconfig) ;
		} else if (pconfig instanceof SPathConfiguration){
			return attachSPath((SPathConfiguration)pconfig) ;
		} else if (pconfig instanceof LetPathConfiguration){
			return attachPath( ((LetPathConfiguration)pconfig).toPathConfig()) ;
		} else {
			throw new IllegalArgumentException("exception.config.notsupport.config") ;
		}
		
	}
	
	private HttpRestSection attachWSPath(WSPathConfiguration wsconfig) {
		try {
			WebSocketPathService wlet = WebSocketPathService.create(aradon, this, context.createChildContext(), wsconfig) ;
			wspaths.put(wlet.getConfig().name(), wlet) ;
			return this;
		} catch (Throwable ex) {
			throw new IllegalStateException(ex) ;
		}
	}

	private HttpRestSection attachPath(PathConfiguration pconfig) {
		PathService plet = PathService.create(aradon, this, context.createChildContext(), pconfig);
		for (String url : pconfig.urlPatterns()) {
			router.attach(makePathPattern(url), plet, plet.getConfig().matchMode());
		}
		paths.put(plet.getConfig().name(), plet);

		return this;
	}

	
	private HttpRestSection attachSPath(SPathConfiguration pconfig) {
		SingleLetPathService splet = pconfig.createOuterLet(aradon, this, context) ;  
		spaths.put(splet.getConfig().name(), splet);

		return this;
	}

	private HttpRestSection attachEPath(EPathConfiguration econfig) {
		try {
			EventSourcePathService elet = EventSourcePathService.create(aradon, this, context.createChildContext(), econfig) ;
			epaths.put(elet.getConfig().name(), elet) ;
			return this;
		} catch (Throwable ex) {
			throw new IllegalStateException(ex) ;
		}
	}

	
	public HttpRestSection detach(String pathName) throws Exception {
		PathService child = paths.get(pathName);
		if (child == null)
			return this;
		sconfig.removePath(pathName);
		paths.remove(pathName);
		router.detach(child);
		child.stop();
		return this;
	}

	public void restart() {
		sconfig.removePreFilter(RevokeServiceFilter.SELF);
	}

	public void suspend() {
		sconfig.addPreFilter(0, RevokeServiceFilter.SELF);
	}

	public IService getChildService(String childName) {
		return ObjectUtil.coalesce(path(childName), spath(childName), wspath(childName), epath(childName));
	}

	public PathService path(String childName){
		return paths.get(childName) ;
	}
	
	public SingleLetPathService spath(String childName){
		return spaths.get(childName) ;
	}
	
	public WebSocketPathService wspath(String childName){
		return wspaths.get(childName) ;
	}

	public EventSourcePathService epath(String childName){
		return epaths.get(childName) ;
	}
	

	public Collection<IService> getChildren() {
		List<IService> result = ListUtil.newList() ;
		result.addAll(paths.values()) ;
		result.addAll(spaths.values()) ;
		result.addAll(wspaths.values()) ;
		result.addAll(epaths.values()) ;
		
		return result;
	}
	
	public Collection<PathService> getPathChildren() {
		return paths.values() ;
	}
	
	public IService getParent() {
		return aradon;
	}

	public String getNamePath() {
		return "/" + sconfig.name() + "/";
	}

	public TreeContext getServiceContext() {
		return context;
	}

	public void reload() {
		// TODO Auto-generated method stub

	}

	public String toString() {
		return this.getClass().getCanonicalName() + "[" + sconfig + "]";
	}

	public PathService expectPathService(Request request, Response response) {
		SectionTemplateRoute srouter = (SectionTemplateRoute) router.getNext(request, response);
		return (PathService) srouter.getNext() ;
	}
	
	private Collection<IRadonPathService> getRadonChildren(){
		List<IRadonPathService> result = ListUtil.newList() ;
		result.addAll(spaths.values()) ;
		result.addAll(wspaths.values()) ;
		result.addAll(epaths.values()) ;

		return result;
	}
	

	public void addToRadonBuilder(RadonConfigurationBuilder rbuilder, AradonHandler ahandler) {
		for (PathService pservice : paths.values()) {
			for (String pattern : pservice.getConfig().urlPatterns()) {
				String cpattern = compatibleStartWith(pservice.getConfig().imatchMode(), pattern) ;
				rbuilder.add(cpattern, ahandler) ;
			}
		}
		
		for (IRadonPathService pservice : getRadonChildren()) {
			for (String pattern : pservice.getConfig().urlPatterns()) {
				String cpattern = compatibleStartWith(pservice.getConfig().imatchMode(), pattern) ;
				rbuilder.add(cpattern, pservice.toHttpHandler()) ;
			}
		}
	}

	private String compatibleStartWith(IMatchMode matchMode, String pattern) {
		String newPattern = (StringUtil.isBlank(sconfig.name()) ? "" : ("/" + sconfig.name())) + pattern ;
		if (matchMode == IMatchMode.STARTWITH && (!(pattern.endsWith("*")))){
			newPattern += "*" ;
		}
		return newPattern;
	}



}

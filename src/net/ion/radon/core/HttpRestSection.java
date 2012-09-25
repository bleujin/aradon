package net.ion.radon.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.ListUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.config.AttributeValue;
import net.ion.radon.core.config.EPathConfiguration;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.config.SPathConfiguration;
import net.ion.radon.core.config.SectionConfiguration;
import net.ion.radon.core.config.WSPathConfiguration;
import net.ion.radon.core.filter.IFilterResult;
import net.ion.radon.core.let.EPathService;
import net.ion.radon.core.let.FilterUtil;
import net.ion.radon.core.let.IRadonPathService;
import net.ion.radon.core.let.PathService;
import net.ion.radon.core.let.SPathService;
import net.ion.radon.core.let.WSPathService;
import net.ion.radon.core.routing.SectionRouter;
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
	private Map<String, SPathService> spaths = MapUtil.newMap();
	private Map<String, WSPathService> wspaths = MapUtil.newMap();
	private Map<String, EPathService> epaths = MapUtil.newMap();

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
		for (PathConfiguration pconfig : sconfig.pathConfiguration()) {
			attach(pconfig);
		}

		for (SPathConfiguration pconfig : sconfig.spathConfiguration()) {
			attach(pconfig);
		}


		for (WSPathConfiguration wconfig : sconfig.wspathConfiguration()) {
			attach(wconfig);
		}
		
		for(EPathConfiguration econfig : sconfig.epathConfiguration()){
			attach(econfig) ;
		}
		

		for (Entry<String, AttributeValue> entry : sconfig.attributes().entrySet()) {
			context.putAttribute(entry.getKey(), entry.getValue());
		}
		super.setName(sconfig.name());
		sconfig.attachService(this);
	}

	private String makePathPattern(String urlPattern) {
		return StringUtil.isBlank(sconfig.name()) ? urlPattern.substring(1) : urlPattern;
	}

	public static HttpRestSection create(Aradon aradon, TreeContext context, SectionConfiguration sconfig) {
		return new HttpRestSection(aradon, context, sconfig);
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
				super.handle(request, response);
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

	public HttpRestSection attach(WSPathConfiguration wsconfig) {
		try {
			WSPathService wlet = WSPathService.create(aradon, this, context.createChildContext(), wsconfig) ;
			wspaths.put(wlet.getConfig().name(), wlet) ;
			return this;
		} catch (Throwable ex) {
			throw new IllegalStateException(ex) ;
		}
	}

	public HttpRestSection attach(PathConfiguration pconfig) {
		PathService plet = PathService.create(aradon, this, context.createChildContext(), pconfig);
		for (String url : pconfig.urlPatterns()) {
			router.attach(makePathPattern(url), plet, plet.getConfig().matchMode());
		}
		paths.put(plet.getConfig().name(), plet);

		return this;
	}

	
	public HttpRestSection attach(SPathConfiguration pconfig) {
		SPathService splet = SPathService.create(aradon, this, context.createChildContext(), pconfig);
		spaths.put(splet.getConfig().name(), splet);

		return this;
	}



	public HttpRestSection attach(EPathConfiguration econfig) {
		try {
			EPathService elet = EPathService.create(aradon, this, context.createChildContext(), econfig) ;
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
		return paths.get(childName);
	}

	public PathService path(String childName){
		return paths.get(childName) ;
	}
	
	public SPathService spath(String childName){
		return spaths.get(childName) ;
	}
	
	public WSPathService wspath(String childName){
		return wspaths.get(childName) ;
	}

	public EPathService epath(String childName){
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
	
	public Collection<IRadonPathService> getRadonChildren(){
		List<IRadonPathService> result = ListUtil.newList() ;
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

	public void reload() throws Exception {
		// TODO Auto-generated method stub

	}

	public String toString() {
		return this.getClass().getCanonicalName() + "[" + sconfig + "]";
	}

}

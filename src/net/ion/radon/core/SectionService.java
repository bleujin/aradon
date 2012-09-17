package net.ion.radon.core;

import java.util.Collection;

import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.let.EPathService;
import net.ion.radon.core.let.PathService;
import net.ion.radon.core.let.WSPathService;

import org.restlet.Application;

public abstract class SectionService extends Application implements IService<PathService> {

	protected SectionService(TreeContext scontext) {
		super(scontext);
	}

	public abstract SectionService attach(PathConfiguration pconfig);

	public abstract SectionService detach(String name) throws Exception;

	
	public abstract Collection<WSPathService> getWSChildren() ;
	
	public abstract Collection<EPathService> getEChildren() ;
	
	public abstract PathService path(String childName) ;
	
	public abstract WSPathService wspath(String childName) ;

	public abstract EPathService espath(String childName) ;
	
	public void stop() {
		this.destorySelf();
	}

	public void destorySelf() {
		try {
			super.stop();
		} catch (Exception ignore) {
			ignore.printStackTrace();
		}

		getServiceContext().closeAttribute();
		// for(PathService pservice : getChildren()){
		// pservice.stop() ;
		// }
	}

}

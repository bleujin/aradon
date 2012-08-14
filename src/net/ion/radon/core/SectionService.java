package net.ion.radon.core;

import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.let.PathService;

import org.restlet.Application;

public abstract class SectionService extends Application implements IService<PathService> {

	protected SectionService(TreeContext scontext) {
		super(scontext);
	}

	public abstract SectionService attach(PathConfiguration pconfig);

	public abstract SectionService detach(String name) throws Exception;

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

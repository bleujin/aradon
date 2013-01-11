package net.ion.radon.core;

import java.util.Collection;

import net.ion.radon.core.config.IPathConfiguration;
import net.ion.radon.core.let.EventSourcePathService;
import net.ion.radon.core.let.IRadonPathService;
import net.ion.radon.core.let.PathService;
import net.ion.radon.core.let.SingleLetPathService;
import net.ion.radon.core.let.WebSocketPathService;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;

public abstract class SectionService extends Application implements IService<IService> {

	protected SectionService(TreeContext scontext) {
		super(scontext);
	}

	public abstract SectionService attach(IPathConfiguration pconfig);

	public abstract SectionService detach(String name) throws Exception;

	public abstract Collection<PathService> getPathChildren()  ;

	public abstract Collection<IRadonPathService> getRadonChildren() ;
	
	public abstract PathService path(String childName) ;
	
	public abstract SingleLetPathService spath(String childName) ;
	
	public abstract WebSocketPathService wspath(String childName) ;

	public abstract EventSourcePathService epath(String childName) ;
	
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

	public abstract PathService expectPathService(Request request, Response response) ;


}

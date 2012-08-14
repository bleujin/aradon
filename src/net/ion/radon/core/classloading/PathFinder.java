package net.ion.radon.core.classloading;

import static net.ion.radon.core.RadonAttributeKey.PATH_SERVICE_KEY;

import java.lang.reflect.Constructor;
import java.util.logging.Level;

import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.PathService;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Status;
import org.restlet.resource.ServerResource;

public class PathFinder extends Restlet {

	public PathFinder(Context context) {
		super(context);
	}

	private ServerResource create(Request request, Response response) {
		try {
			Constructor<? extends ServerResource> con = getTargetClass(request).getDeclaredConstructor() ;
			con.setAccessible(true) ;
			return con.newInstance();
		} catch (Exception e) {
			getLogger().log(Level.WARNING, "Exception while instantiating the target server resource.", e);
		}
		return null;
	}

	private ServerResource find(Request request, Response response) {
		return create(request, response);
	}

	private Class<? extends ServerResource> getTargetClass(Request request) {
		InnerRequest ireq = (InnerRequest) request;
		PathService pservice = ireq.getAttribute(PATH_SERVICE_KEY, PathService.class);
		return pservice.getConfig().handlerClz();
	}

	public void handle(Request request, Response response) {
		super.handle(request, response);
		ServerResource targetResource = find(request, response);
		if (targetResource == null) {
			if (getLogger().isLoggable(Level.WARNING))
				getLogger().warning((new StringBuilder()).append("No target resource was defined for this finder: ").append(toString()).toString());
			response.setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		} else {
			targetResource.init(getContext(), request, response);
			if (response == null || response.getStatus().isSuccess())
				targetResource.handle();
			targetResource.release();
		}
	}
}

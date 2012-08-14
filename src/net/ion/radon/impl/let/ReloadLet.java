package net.ion.radon.impl.let;

import net.ion.framework.util.Debug;
import net.ion.radon.core.IService;
import net.ion.radon.core.let.AbstractLet;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class ReloadLet extends AbstractLet{

	@Override
	protected Representation myDelete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Representation myGet() throws Exception {
		
		final IService service =  getSectionService().getParent();
		service.reload() ;
		
		Debug.warn("RELOADED") ;
		return new StringRepresentation("RELOAD");
	}

	@Override
	protected Representation myPost(Representation entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Representation myPut(Representation entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

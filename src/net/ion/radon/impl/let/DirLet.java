package net.ion.radon.impl.let;

import java.io.File;

import net.ion.radon.core.let.AbstractLet;

import org.restlet.data.MediaType;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;

public class DirLet extends AbstractLet{

	@Override
	protected Representation myDelete() throws Exception {
		return notImpl();
	}

	@Override
	protected Representation myGet() throws Exception {
//		Debug.line(getInnerRequest().getPathInfo(), getContext().getParentContext().getAttributes()) ;
//		Debug.line(getContext().getAttributes()) ;
		
		File file = new File(getContext().getAttributeObject("base.dir", "./", String.class) + getTargetPath()) ;
		final FileRepresentation result = new FileRepresentation(file, MediaType.ALL);
		return result;
	}

	@Override
	protected Representation myPost(Representation entity) throws Exception {
		return notImpl(entity);
	}

	@Override
	protected Representation myPut(Representation entity) throws Exception {
		return notImpl(entity);
	}
	
	private String getTargetPath() {
		return getRequest().getResourceRef().getRemainingPart();
	}
}

package net.ion.nradon.handler;

import java.io.File;

import net.ion.framework.util.PathMaker;
import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.data.MediaType;
import org.restlet.representation.FileRepresentation;
import org.restlet.resource.Get;

public class TestDownloadLet extends AbstractServerResource{

	@Get
	public FileRepresentation getFile(){
		String baseDir = "e:/download" ;
		String filename = getInnerRequest().getAttribute("filename") ;
		File file = new File(PathMaker.getFilePath(baseDir, filename)) ;
		
		return new FileRepresentation(file, MediaType.APPLICATION_OCTET_STREAM) ;
	}
}

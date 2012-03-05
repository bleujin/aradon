package net.ion.radon.impl;

import java.io.File;

import net.ion.framework.util.PathMaker;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.representation.FileRepresentation;
import org.restlet.resource.Get;

public class DownloadLet extends AbstractServerResource{

	@Get
	public FileRepresentation getFile(){
		String baseDir = "C:/setup/download" ;
		String filename = getInnerRequest().getAttribute("filename") ;
		File file = new File(PathMaker.getFilePath(baseDir, filename)) ;
		
		return new FileRepresentation(file, getMetadataService().getMediaType(StringUtil.substringAfterLast(filename, "."))) ;
	}
}

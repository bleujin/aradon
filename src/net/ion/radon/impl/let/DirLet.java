package net.ion.radon.impl.let;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.framework.util.PathMaker;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.let.AbstractServerResource;

import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StreamRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;

public class DirLet extends AbstractServerResource{


	@Get
	public Representation getFile() throws Exception {
		String filePath = PathMaker.getFilePath(getContext().getAttributeObject("base.dir", "./", String.class), getRequest().getResourceRef().getPath()) ;
		final File file = new File(filePath) ;

		if (! file.exists()) {
			throw new ResourceException(Status.CLIENT_ERROR_NOT_FOUND, getRequest().getResourceRef().getPath()) ; 
		}

		MediaType mtype = getMetadataService().getMediaType(StringUtil.substringAfterLast(file.getName(), ".")) ;
		if (mtype == null) mtype = MediaType.ALL ; 
		
		mtype = getMetadataService().getMediaType(StringUtil.substringAfter(file.getName(), ".")) ;
		if (mtype == MediaType.AUDIO_MPEG){
			return new StreamRepresentation(MediaType.AUDIO_WAV) {
				@Override
				public void write(OutputStream outputstream) throws IOException {
					IOUtil.copyNClose(getStream(), outputstream) ;
				}
				@Override
				public InputStream getStream() throws IOException {
					return new FileInputStream(file);
				}
			};
		}
			
			
		Debug.line(file, mtype) ;
		final FileRepresentation result = new FileRepresentation(file, mtype);
		return result;
	}

	@Post
	public Representation getFileWithPost(Representation entity) throws Exception {
		return getFile();
	}
}

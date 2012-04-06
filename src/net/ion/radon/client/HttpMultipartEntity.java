package net.ion.radon.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import net.ion.framework.util.ListUtil;

import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;

public class HttpMultipartEntity {

	private MultipartEntity entity = new MultipartEntity() ;

	public HttpMultipartEntity(){
	}
	public HttpMultipartEntity(FormBodyPart[] parts) {
		for (FormBodyPart part : parts) {
			addParameter(part) ;
		}
	}

	
	public void addParameter(String name, String value) throws UnsupportedEncodingException {
		entity.addPart(name, new StringBody(value, Charset.forName("UTF-8")));
	}

	public void addParameter(String name, String value, CharacterSet charset) throws UnsupportedEncodingException {
		addParameter(name, value, charset.toCharset()) ;
	}

	public void addParameter(FormBodyPart part) {
		entity.addPart(part) ;
	}

	public void addParameter(String name, String value, Charset charset) throws UnsupportedEncodingException {
		entity.addPart(name, new StringBody(value, charset));
	}

	public void addParameter(String name, File value) throws FileNotFoundException {
		entity.addPart(name, new FileBody(value));
	}

	public Representation makeRepresentation() {
		return new OutputRepresentation(MediaType.valueOf(entity.getContentType().getValue()), entity.getContentLength()) {
			public void write(OutputStream out) throws IOException {
				entity.writeTo(out) ;
			}
		};
	}
	
	public MultipartEntity getEntity(){
		return entity ;
	}

}

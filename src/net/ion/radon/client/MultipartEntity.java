package net.ion.radon.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import net.ion.framework.util.ListUtil;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;

public class MultipartEntity {

	private List<Part> parts = ListUtil.newList();

	public void addParameter(String name, String value) {
		parts.add(new StringPart(name, value));
	}

	public void addParameter(String name, String value, CharacterSet charset) {
		addParameter(name, value, charset.toCharset()) ;
	}

	public void addParameter(Part part) {
		parts.add(part) ;
	}

	public void addParameter(String name, String value, Charset charset) {
		parts.add(new StringPart(name, value, charset.name()));
	}

	public void addParameter(String name, File value) throws FileNotFoundException {
		parts.add(new FilePart(name, value));
	}

	public Representation makeRepresentation() {
		final MultipartRequestEntity mre = new MultipartRequestEntity(parts.toArray(new Part[0]), new HttpClientParams());

		return new OutputRepresentation(MediaType.valueOf(mre.getContentType()), mre.getContentLength()) {
			public void write(OutputStream out) throws IOException {
				mre.writeRequest(out);
			}
		};
	}

}

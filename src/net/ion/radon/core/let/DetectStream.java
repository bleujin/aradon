package net.ion.radon.core.let;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Locale;

import net.ion.radon.core.RadonAttributeKey;

import org.restlet.Response;
import org.restlet.data.CharacterSet;
import org.restlet.engine.header.ContentType;
import org.restlet.util.Series;

public class DetectStream {

	private DetectEncodingInputStream detectStream = null;

	private Response response ;
	private CharacterSet responseCharset;

	public DetectStream(Response response) throws IOException {
		this.response = response ;
		Series header = (Series) response.getAttributes().get(RadonAttributeKey.ATTRIBUTE_HEADERS) ;
		ContentType ct = new ContentType(header.getFirstValue("Content-Type")) ;
		this.responseCharset = ct.getCharacterSet() ;
	}

	public Charset getResponseCharSet() {

		Charset maybeCharset = null;
		try {
			InputStream bodyStream = getResponseBodyAsStream();
			if (bodyStream == null) throw new IOException(response.getRequest().getResourceRef() + " is null");
			detectStream = new DetectEncodingInputStream(bodyStream, 2048, Locale.getDefault());

			Charset guessCharset = detectStream.getEncoding() ;
			boolean isSupported = (responseCharset != null);
			if (isSupported && guessCharset.equals(responseCharset)) {
				maybeCharset = responseCharset.toCharset();
			} else {
				if (CharacterSet.ISO_8859_1 .equals(responseCharset) || (!isSupported)) {
					maybeCharset = guessCharset;
				} else {
					maybeCharset = guessCharset;
				}
			}
		} catch (IOException e) {
			maybeCharset = Charset.defaultCharset();
		}
		return maybeCharset;
	}

	public Reader getBodyReader() throws IOException {
		Charset encodeCharSet = getResponseCharSet() ;
		InputStream responseStream = getResponseBodyAsStream();
		if (responseStream == null)
			return null;
		return new InputStreamReader(responseStream, encodeCharSet);
	}

	public InputStream getResponseStream() {
		try {
			return getResponseBodyAsStream();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public InputStream getResponseBodyAsStream() throws IOException {
		if (detectStream == null)
			return response.getEntity().getStream();
		return detectStream;
	}
}

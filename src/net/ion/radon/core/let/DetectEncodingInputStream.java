package net.ion.radon.core.let;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Locale;

import org.apache.commons.lang.CharSet;

import net.ion.framework.util.Debug;

public class DetectEncodingInputStream extends InputStream {
	private InputStream is;
	private int bufferLength;
	private Locale locale;

	private byte[] buffer;
	private int counter;
	private Charset charset;
	private int sampleLength ;

	public DetectEncodingInputStream(InputStream input) throws IOException {
		this(input, 4096, Locale.getDefault()) ;
	}

	public DetectEncodingInputStream(InputStream input, int bufferLength, Locale reqLocale) throws IOException {
		this.is = input;
		this.bufferLength = bufferLength;
		this.locale = reqLocale;
		this.buffer = new byte[bufferLength];
		this.charset = detectCharset();
	}

	private Charset detectCharset() throws IOException {
		return Charset.forName(detectEncoding());
	}

	private String detectEncoding() throws IOException {
		this.sampleLength = is.read(buffer, 0, bufferLength) ;

		String charset = new EncodeDetector().detectEncode(buffer, locale) ;
		if (charset == null) charset = Charset.defaultCharset().toString() ;
		return charset;
	}

	@Override
	public int read() throws IOException {
		if (counter < bufferLength && counter < sampleLength){
			return buffer[counter++];
		} else
			return is.read();
	}

	public Reader getReader() {
		return new InputStreamReader(this, this.charset);
	}

	public Charset getEncoding() {
		return this.charset;
	}
	
}

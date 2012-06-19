package net.ion.radon.impl.let;

import java.io.InputStream;
import java.io.OutputStream;

import net.ion.framework.util.IOUtil;
import net.ion.radon.core.let.DefaultLet;

import org.restlet.data.MediaType;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;

public class FaviconLet extends DefaultLet{
	@Override
	protected Representation myGet() throws Exception {
		final InputStream input  = getClass().getResourceAsStream("net.ion.radon.impl.let.favicon.ico");
		return  new OutputRepresentation(MediaType.ALL) {
			public void write(OutputStream output)  {
				try {
					IOUtil.copyNClose(input, output);
				} catch (Throwable e) {
				} 
			}
		};
	}
}

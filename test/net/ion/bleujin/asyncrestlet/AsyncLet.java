package net.ion.bleujin.asyncrestlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.ion.radon.core.let.AbstractLet;

import org.apache.commons.lang.RandomStringUtils;
import org.restlet.data.MediaType;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;

public class AsyncLet extends AbstractLet {

	@Override
	protected Representation myDelete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Representation myGet() throws Exception {
		// MyThread t = new MyThread() ;
		// t.start() ;
		// AsyncInputStream input = new AsyncInputStream(t.getInputStream(), 100) ;
		// final InputRepresentation result = new InputRepresentation(input, MediaType.TEXT_HTML);
		// return result ;

		return new OutputRepresentation(MediaType.TEXT_HTML) {
			public void write(OutputStream output) throws IOException {
				try {
					Writer writer = new OutputStreamWriter(output);
					for (int i = 0; i < 100; i++) {
						writer.write(RandomStringUtils.randomAlphabetic(40) + "<br />");
						Thread.sleep(500);
						writer.flush();
					}
					writer.close();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
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

package net.ion.bleujin;

import java.io.File;
import java.io.FileReader;

import net.ion.radon.core.let.AbstractLet;

import org.apache.commons.io.IOUtils;
import org.restlet.data.CharacterSet;
import org.restlet.data.Language;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class ProxyLet extends AbstractLet{

	@Override
	protected Representation myDelete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Representation myGet() throws Exception {
		File file = new File("imsi/proxy.htm") ;
		
		String body = IOUtils.toString(new FileReader(file)) ;
		return new StringRepresentation(body, MediaType.TEXT_HTML, Language.ALL, CharacterSet.UTF_8);
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

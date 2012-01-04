package net.ion.radon.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

import net.ion.radon.core.let.AbstractServerResource;

public class LongResponseLet extends AbstractServerResource{

	@Get
	public Representation print() throws FileNotFoundException{
		
		try {
			Thread.sleep(3000) ;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		FileInputStream fis = new FileInputStream(new File("aradon_lib/imsi/jmxtools-1.2.jar")) ;
		return new InputRepresentation(fis) ;
	}
}

package net.ion.radon.impl.let;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;

import org.apache.http.impl.cookie.DateUtils;
import org.restlet.Client;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.engine.local.ZipClientHelper;
import org.restlet.engine.local.ZipEntryRepresentation;

public class TestZipRepresentation extends TestCase{

	public void testNew() throws Exception {
		
		ZipFile zipFile = new ZipFile(new File("c:/temp/aradon_src.zip")) ;
		ZipEntry entry = new ZipEntry("imsi/tables.xml") ;
		
		ZipEntryRepresentation rep = new ZipEntryRepresentation(MediaType.TEXT_ALL, zipFile, entry) ;
		
		
		
		Debug.debug(rep.getText()) ;
	}
	
	public void testJar() throws Exception {
		ZipFile zipFile = new ZipFile(new File("c:/temp/crx-2.1.0.20100426-developer.jar")) ;
		ZipEntry entry = new ZipEntry("static/docs/README.txt") ;
		
		ZipEntryRepresentation rep = new ZipEntryRepresentation(MediaType.TEXT_ALL, zipFile, entry) ;
		
		Debug.debug(rep.getText()) ;
	}
	
	public void testZipClient() throws Exception {
		ZipClientHelper h = new ZipClientHelper(new Client(Protocol.HTTP)) ;
		
		
	}
	
	public void testDate() throws Exception {
		String date = "Fri, 19 Nov 2010 07:57:44 GMT" ;
		DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
		
		Date d = DateUtils.parseDate(date) ;
		Debug.debug(d) ;
		
		
		
	}
}

package net.ion.radon.representation;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.util.IOUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.core.representation.JsonObjectRepresentation;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.data.CharacterSet;
import org.restlet.data.Disposition;
import org.restlet.data.Language;
import org.restlet.data.MediaType;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

public class RepresentationSample  {

	@Test
	public void testMediaType() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", ConfirmLet.class).getAradon() ;
		AradonClient ac = AradonClientFactory.create(aradon) ;
		
		Representation getRep = ac.createRequest("/test").get() ;
		assertEquals(MediaType.TEXT_HTML, getRep.getMediaType()) ;
		assertEquals("����", getRep.getText()) ;
		

		Representation postRep = ac.createRequest("/test").post() ;
		assertEquals(MediaType.APPLICATION_JSON, postRep.getMediaType()) ;
		assertEquals("hello", JsonParser.fromString(postRep.getText()).getAsJsonObject().asString("greeting") ) ;
	}
	
	@Test
	public void testFile() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", ConfirmLet.class).getAradon() ;
		AradonClient ac = AradonClientFactory.create(aradon) ;
		
		Representation putRep = ac.createRequest("/test").put() ;
		assertEquals(new File("./resource/docs/submit.docx").length(), putRep.getAvailableSize()) ;
		assertEquals(MediaType.APPLICATION_MSOFFICE_DOCX, putRep.getMediaType()) ;

		Representation delRep = ac.createRequest("/test").delete() ;
		assertEquals(MediaType.APPLICATION_MSOFFICE_DOCX, delRep.getMediaType()) ;
		
		String disposition = new String(delRep.getDisposition().getType().getBytes("latin1"), "UTF-8") ;
		assertEquals("attachment;filename=�̸�.docx", disposition) ;

	}
	
}

class ConfirmLet extends AbstractServerResource {
	
	@Get
	public Representation returnHTML(){
		return new StringRepresentation("����", MediaType.TEXT_HTML, Language.ALL, CharacterSet.UTF_8) ;
	}

	@Post
	public Representation returnJson(){
		JsonObject obj = JsonObject.fromString("{greeting:'hello', year:2001}") ;
		return new JsonObjectRepresentation(obj) ;
	}
	
	@Put
	public Representation readFile(){
		return new FileRepresentation(new File("./resource/docs/submit.docx"), getMetadataService().getMediaType("docx")) ;
	}
	
	@Delete
	public Representation readStream() throws UnsupportedEncodingException {
		final File file = new File("./resource/docs/submit.docx")  ;
		OutputRepresentation result = new OutputRepresentation(getMetadataService().getMediaType("docx")) {
			@Override
			public void write(OutputStream output) throws IOException {
				IOUtil.copyNClose(new FileInputStream(file), output) ;
			}
		};
		result.setCharacterSet(CharacterSet.UTF_8) ;
		if (isExplorer()){
			String encodedName = URLEncoder.encode("�̸�.docx", "UTF-8") ;
			result.setDisposition(new Disposition("attachment;filename=" + encodedName)) ;
		} else {
			result.setDisposition(new Disposition("attachment;filename=" + new String("�̸�.docx".getBytes("UTF-8"), "latin1"))) ;
		}
		
		return result;
	}
}
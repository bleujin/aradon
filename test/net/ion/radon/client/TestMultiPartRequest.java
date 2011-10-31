package net.ion.radon.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.radon.util.AradonTester;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;

/**
 * <p>Title: TestMultiPartRequest.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: I-ON Communications</p>
 * <p>Date : 2011. 10. 12.</p>
 * @author novision
 * @version 1.0
 */
public class TestMultiPartRequest extends TestCase{
	
	
	public void testPost() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", TestMultipartLet.class) ;
		at.getAradon().startServer(9999) ;
		
		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9999") ;
		MultipartEntity rf = new MultipartEntity() ;
		rf.addParameter("name", "bleujin") ;
		rf.addParameter("uploadfile", new File("./build.xml")) ;
		
		Representation result =  ac.createRequest("/hello").multipart(Method.POST, rf.makeRepresentation()) ;
		
		Debug.line(result.getText()) ;
		at.getAradon().stop() ;
	}
	
	


}


package net.ion.radon.client;

import java.io.File;

import net.ion.framework.util.Debug;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.data.Method;
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
public class TestMultiPartRequest {
	
	@Test
	public void post() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", TestMultipartLet.class) ;
		at.getAradon().startServer(9999) ;
		
		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9999") ;
		HttpMultipartEntity rf = new HttpMultipartEntity() ;
		rf.addParameter("name", "bleujin") ;
		rf.addParameter("uploadfile", new File("./build.xml")) ;
		
		Representation result =  ac.createRequest("/hello").multipart(Method.POST, rf.makeRepresentation()) ;
		
		Debug.line(result.getText()) ;
		at.getAradon().stop() ;
	}
	
	


}


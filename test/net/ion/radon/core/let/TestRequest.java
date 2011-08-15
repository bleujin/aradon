package net.ion.radon.core.let;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map.Entry;

import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.impl.section.BasePathInfo;
import net.ion.radon.impl.section.PathInfo;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.OutputRepresentation;

public class TestRequest extends TestAradon{

	
	public void testBeforeHandle() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/hello?abcd=mu&aradon.result.method=POST") ;
		Response response = handle(request) ;
		assertEquals(200, response.getStatus().getCode()) ;
		Debug.line(response.getEntityAsText()) ;
		assertEquals(true, response.getEntityAsText().length() > 0) ;
		Debug.debug(request.getAttributes().get(RadonAttributeKey.ATTRIBUTE_HEADERS)) ;
		
		assertTrue(request.getMethod().equals(Method.POST)) ;
	}
	
	
	public void testAttribute() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/hello/Hi") ;
		Response response = handle(request) ;
		
		Debug.debug(request.getAttributes().get("greeting")) ;
		Debug.debug(response.getRequest().getAttributes().get("greeting")) ;
		
		Debug.debug(request == response.getRequest()) ;
		assertTrue(request == response.getRequest()) ;
	}
	
	
	public void testPathInfo() throws Exception {
		Request request = new Request(Method.GET, "riap://component/another/hello/Hi") ;
		Response response = handle( request) ;
		
		InnerRequest ireq = ((InnerResponse)Response.getCurrent()).getInnerRequest() ;
		BasePathInfo pinfo = ireq.getPathInfo(aradon) ;
		
		assertEquals("hello", pinfo.getName()) ;
	}
	
	
	public void testRequestPath() throws Exception {
		initAradon() ;

		Request request = new Request(Method.GET, "riap://component/another/") ;
		Response response = aradon.handle(request) ;
		
		assertEquals(200, response.getStatus().getCode()) ;
	}
	
	
	public void testGetParameter() throws Exception {
		initAradon() ;
		Request request = new Request(Method.GET, "riap://component/another/hello?abcd=efg&int=2") ;
		
		aradon.handle(request) ;
		
		assertEquals("efg", getInnerRequest().getFormParameter().get("abcd")) ;
		assertEquals(2L, getInnerRequest().getFormParameter().get("int")) ;
	}
	
	public void testPostParameter() throws Exception {
		initAradon() ;
		Request request = new Request(Method.GET, "riap://component/another/hello") ;
		Form form = new Form() ;
		form.add("abcd", "efg") ;
		form.add("int", "2") ;
		
		request.setEntity(form.getWebRepresentation()) ;
		
		aradon.handle(request) ;
		
		assertEquals("efg", getInnerRequest().getFormParameter().get("abcd")) ;
		assertEquals(2L, getInnerRequest().getFormParameter().get("int")) ;
	}
	
	
	public void testMultiValue() throws Exception {
		initAradon() ;
		Request request = new Request(Method.GET, "riap://component/another/hello") ;
		Form form = new Form() ;
		form.add("abcd", "efg") ;
		form.add("abcd", "hij") ;
		
		request.setEntity(form.getWebRepresentation()) ;
		
		aradon.handle(request) ;
		
		assertEquals("efg", getInnerRequest().getFormParameter().get("abcd")) ;
		assertEquals(Arrays.asList("efg", "hij").toString(), getInnerRequest().getFormParameter().getAsList("abcd").toString()) ;

	}
	
	
	public void testMultipart() throws Exception {
		Request request = new Request(Method.POST, "riap://component/another/hello");
		Part[] parts = { new StringPart("from", "bleujin@i-on.net"),
				new StringPart("to", "bleujin@i-on.net"),
				new StringPart("subject", "한글", "UTF-8"),
				new StringPart("content", "안녕하세요.", "UTF-8"),
				new FilePart("attach1", new File("./resource/config/dev-config.xml"))
		} ; 
				// , new FilePart("attach2", "한글.csv", new File("./imsi/한글.csv"), "text/plain", "UTF-8")};
		final MultipartRequestEntity mre = new MultipartRequestEntity(parts, new HttpClientParams());
		OutputRepresentation representation = new OutputRepresentation(MediaType.valueOf(mre.getContentType()), mre.getContentLength()) {
			public void write(OutputStream out) throws IOException {
				mre.writeRequest(out) ;
			}
		};
		request.setEntity(representation) ;
		aradon.handle(request);
		
		for (Entry entry : getInnerRequest().getFormParameter().entrySet()){
			Debug.debug(entry.getKey(), entry.getValue().getClass()) ;
		}
	}
	
}

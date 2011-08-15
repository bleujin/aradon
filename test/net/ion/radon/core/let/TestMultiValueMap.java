package net.ion.radon.core.let;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.restlet.Request;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.OutputRepresentation;

public class TestMultiValueMap extends TestCase{

	public void testSingleValueMap() throws Exception {
		MultiValueMap vmap = new MultiValueMap() ;
		vmap.put("name", "bleujin") ;
		vmap.put("name", "hero") ;
		
		assertEquals("hero", vmap.get("name")) ;
	}

	public void testMultiValue() throws Exception {
		MultiValueMap vmap = new MultiValueMap() ;
		vmap.putParameter("name", new String[]{"bleujin", "hero"}) ;
		
		assertEquals("bleujin", vmap.get("name")) ;
		assertTrue(vmap.getAsList("name") instanceof List) ;
		assertEquals("bleujin", vmap.getAsList("name").get(0)) ;
		assertEquals("hero", vmap.getAsList("name").get(1)) ;
	}

	public void testMultiValue2() throws Exception {
		MultiValueMap vmap = new MultiValueMap() ;
		vmap.putParameter("name", "bleujin") ;
		vmap.putParameter("name", "hero") ;
		
		assertTrue(vmap.getAsList("name") instanceof List) ;
		assertEquals("bleujin", vmap.getAsList("name").get(0)) ;
		assertEquals("hero", vmap.getAsList("name").get(1)) ;
	}
	
	public void testDouble() throws Exception {
		MultiValueMap map = new MultiValueMap() ;
		map.putParameter("jum", ".9") ;
		assertEquals("0.9", String.valueOf(map.get("jum"))) ;
	}
	
	public void testNumeric() throws Exception {
		MultiValueMap map = new MultiValueMap() ;
		map.putParameter("double", "3.3") ;
		map.putParameter("long", "3") ;
		map.putParameter("minus", "-3") ;
		map.putParameter("string", "3a") ;
		
		assertEquals("3.3", String.valueOf(map.get("double"))) ;
		assertEquals("3", String.valueOf(map.get("long"))) ;
		assertEquals("3a", String.valueOf(map.get("string"))) ;
		
		Debug.debug(map.get("minus").getClass()) ;
		assertEquals("-3", String.valueOf(map.get("minus"))) ;
		assertEquals(-3L, map.get("minus")) ;
	}
	
	
	public void testMultipart() throws Exception {

		Request request = new Request(Method.POST, "/");
		Part[] parts = { new StringPart("from", "bleujin@i-on.net"),
				new StringPart("to", "bleujin@i-on.net"),
				new StringPart("subject", "한글", "UTF-8"),
				new StringPart("subject", "한글2", "UTF-8"),
				new StringPart("content", "안녕하세요.", "UTF-8"),
				new FilePart("attach1", new File("./resource/config/dbconfig.json"))
		} ; 
				// , new FilePart("attach2", "한글.csv", new File("./imsi/한글.csv"), "text/plain", "UTF-8")};
		final MultipartRequestEntity mre = new MultipartRequestEntity(parts, new HttpClientParams());
		OutputRepresentation representation = new OutputRepresentation(MediaType.valueOf(mre.getContentType()), mre.getContentLength()) {
			public void write(OutputStream out) throws IOException {
				mre.writeRequest(out) ;
			}
		};
		request.setEntity(representation) ;

		
		InnerRequest ireq = InnerRequest.create(request) ;

		Map params =  ireq.getFormParameter() ;
		for (Entry entry : (Set<Entry>)params.entrySet()) {
			Debug.debug(entry.getKey(), entry.getValue(), entry.getValue().getClass()) ;
		}
	
		
		
	
	}
}

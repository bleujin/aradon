package net.ion.radon.core.let;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.radon.client.MultipartEntity;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang.CharSet;
import org.apache.commons.lang.CharUtils;
import org.restlet.Request;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.OutputRepresentation;

public class TestMultiValueMap extends TestCase {

	public void testSingleValueMap() throws Exception {
		MultiValueMap vmap = new MultiValueMap();
		vmap.put("name", "bleujin");
		vmap.put("name", "hero");

		assertEquals("hero", vmap.get("name"));
	}

	public void testMultiValue() throws Exception {
		MultiValueMap vmap = new MultiValueMap();
		vmap.putParameter("name", new String[] { "bleujin", "hero" });

		assertEquals("bleujin", vmap.get("name"));
		assertTrue(vmap.getAsList("name") instanceof List);
		assertEquals("bleujin", vmap.getAsList("name").get(0));
		assertEquals("hero", vmap.getAsList("name").get(1));
	}

	public void testMultiValue2() throws Exception {
		MultiValueMap vmap = new MultiValueMap();
		vmap.putParameter("name", "bleujin");
		vmap.putParameter("name", "hero");

		assertTrue(vmap.getAsList("name") instanceof List);
		assertEquals("bleujin", vmap.getAsList("name").get(0));
		assertEquals("hero", vmap.getAsList("name").get(1));
	}

	public void testDouble() throws Exception {
		MultiValueMap map = new MultiValueMap();
		map.putParameter("jum", ".9");
		assertEquals("0.9", String.valueOf(map.get("jum")));
	}

	public void testNumeric() throws Exception {
		MultiValueMap map = new MultiValueMap();
		map.putParameter("double", "3.3");
		map.putParameter("long", "3");
		map.putParameter("minus", "-3");
		map.putParameter("string", "3a");

		assertEquals("3.3", String.valueOf(map.get("double")));
		assertEquals("3", String.valueOf(map.get("long")));
		assertEquals("3a", String.valueOf(map.get("string")));

		Debug.debug(map.get("minus").getClass());
		assertEquals("-3", String.valueOf(map.get("minus")));
		assertEquals(-3L, map.get("minus"));
	}

	public void testMultipart() throws Exception {

		Request request = new Request(Method.POST, "/");

		MultipartEntity mpe = new MultipartEntity();
		mpe.addParameter("to", "bleujin@i-on.net");
		mpe.addParameter("subject", "한글", CharacterSet.UTF_8);
		mpe.addParameter("subject", "한글2", CharacterSet.UTF_8);
		mpe.addParameter("content", "안녕하세요", CharacterSet.UTF_8);
		mpe.addParameter("attach1", new File("./build.xml"));
		request.setEntity(mpe.makeRepresentation());

		InnerRequest ireq = InnerRequest.create(request);

		Map params = ireq.getFormParameter();
		for (Entry entry : (Set<Entry>) params.entrySet()) {
			if ("subject".equals(entry.getKey())) {
				assertEquals(ListUtil.toList("한글", "한글2"), entry.getValue());
			}
			// Debug.debug(entry.getKey(), entry.getValue(), entry.getValue().getClass()) ;
		}

		assertEquals("bleujin@i-on.net", params.get("to"));
		assertEquals("build.xml", ((DiskFileItem) params.get("attach1")).getName());
		assertEquals("한글", params.get("subject"));
	}

	public void testSort() throws Exception {
		String[] urls = new String[] { "a", "abcd", "123", "98" };
		Arrays.sort(urls, 0, urls.length, new Comparator<String>() {
			public int compare(String left, String right) {
				return left.length() - right.length();
			}
		});

		assertTrue(Arrays.equals(urls, new String[] { "a", "98", "123", "abcd" }));
	}
}

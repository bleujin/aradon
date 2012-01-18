package net.ion.radon.core.let;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.radon.client.MultipartEntity;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.data.CharacterSet;
import org.restlet.data.Method;

public class TestMultiValueMap {

	@Test
	public void singleValueMap() throws Exception {
		MultiValueMap vmap = new MultiValueMap();
		vmap.put("name", "bleujin");
		vmap.put("name", "hero");

		assertEquals("hero", vmap.get("name"));
	}

	@Test
	public void multiValue() throws Exception {
		MultiValueMap vmap = new MultiValueMap();
		vmap.putParameter("name", new String[] { "bleujin", "hero" });

		assertEquals("bleujin", vmap.get("name"));
		assertTrue(vmap.getAsList("name") instanceof List);
		assertEquals("bleujin", vmap.getAsList("name").get(0));
		assertEquals("hero", vmap.getAsList("name").get(1));
	}

	@Test
	public void multiValue2() throws Exception {
		MultiValueMap vmap = new MultiValueMap();
		vmap.putParameter("name", "bleujin");
		vmap.putParameter("name", "hero");

		assertTrue(vmap.getAsList("name") instanceof List);
		assertEquals("bleujin", vmap.getAsList("name").get(0));
		assertEquals("hero", vmap.getAsList("name").get(1));
	}
	
	@Test
	public void doubleValue() throws Exception {
		MultiValueMap map = new MultiValueMap();
		map.putParameter("jum", "0.9");
		assertEquals("0.9", String.valueOf(map.get("jum")));
	}

	@Test
	public void numeric() throws Exception {
		MultiValueMap map = new MultiValueMap();
		map.putParameter("double", "3.3");
		map.putParameter("long", "3");
		map.putParameter("minus", "-3");
		map.putParameter("string", "3a");

		assertEquals("3.3", String.valueOf(map.get("double")));
		assertEquals("3", String.valueOf(map.get("long")));
		assertEquals("3a", String.valueOf(map.get("string")));

		Debug.debug(map.get("minus").getClass());
		assertEquals("-3", map.get("minus"));
	}

	@Test
	public void multipart() throws Exception {

		Request request = new Request(Method.POST, "/");

		MultipartEntity mpe = new MultipartEntity();
		mpe.addParameter("to", "bleujin@i-on.net");
		mpe.addParameter("subject", "�ѱ�", CharacterSet.UTF_8);
		mpe.addParameter("subject", "�ѱ�2", CharacterSet.UTF_8);
		mpe.addParameter("content", "�ȳ��ϼ���", CharacterSet.UTF_8);
		mpe.addParameter("attach1", new File("./build.xml"));
		request.setEntity(mpe.makeRepresentation());

		InnerRequest ireq = InnerRequest.create(request);

		Map params = ireq.getFormParameter();
		for (Entry entry : (Set<Entry>) params.entrySet()) {
			if ("subject".equals(entry.getKey())) {
				assertEquals(ListUtil.toList("�ѱ�", "�ѱ�2"), entry.getValue());
			}
			// Debug.debug(entry.getKey(), entry.getValue(), entry.getValue().getClass()) ;
		}

		assertEquals("bleujin@i-on.net", params.get("to"));
		assertEquals("build.xml", ((DiskFileItem) params.get("attach1")).getName());
		assertEquals("�ѱ�", params.get("subject"));
	}

	@Test
	public void sort() throws Exception {
		String[] urls = new String[] { "a", "abcd", "123", "98" };
		Arrays.sort(urls, 0, urls.length, new Comparator<String>() {
			public int compare(String left, String right) {
				return left.length() - right.length();
			}
		});

		assertTrue(Arrays.equals(urls, new String[] { "a", "98", "123", "abcd" }));
	}
}

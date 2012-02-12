package net.ion.radon.core.let;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;

import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.util.Debug;
import net.ion.radon.client.MultipartEntity;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.PathService;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.impl.section.BasePathInfo;
import net.ion.radon.util.AradonTester;

import org.junit.Before;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Status;

public class TestRequest {

	private AradonTester at;
	private Aradon aradon;

	@Before
	public void setUp() throws Exception {
		at = AradonTester.create().register("", "/test", GetLet.class);
		aradon = at.getAradon();
	}

	@Test
	public void connect() throws Exception {

		Request request = new Request(Method.GET, "riap://component/test");
		Response response = aradon.handle(request);

		assertEquals(200, response.getStatus().getCode());
	}

	@Test
	public void method() throws Exception {
		Request request = new Request(Method.GET, "riap://component/test");
		Response response = aradon.handle(request);

		assertEquals(Status.SUCCESS_OK, response.getStatus());
		assertEquals(Method.GET, request.getMethod());
	}

	@Test
	public void modMethod() throws Exception {
		Request request = new Request(Method.GET, "riap://component/test?aradon.result.method=POST");
		Response response = aradon.handle(request);

		assertEquals(Status.SUCCESS_OK, response.getStatus());
		assertEquals(Method.POST, request.getMethod());
	}

	@Test
	public void requestAttribute() throws Exception {
		AradonTester at = AradonTester.create().register("another", "/test/{greeting}", GetLet.class);
		Aradon aradon = at.getAradon();

		Request request = new Request(Method.GET, "riap://component/another/test/hi");
		Response response = aradon.handle(request);

		assertEquals("hi", request.getAttributes().get("greeting"));
		assertEquals(true, request.getAttributes().get(RadonAttributeKey.ATTRIBUTE_HEADERS) != null);
		assertEquals(true, request.getAttributes().get(RadonAttributeKey.FORM_ATTRIBUTE_KEY) != null);
		assertEquals(true, request.getAttributes().get(RadonAttributeKey.REQUEST_CONTEXT) != null);

		Debug.debug(request.getAttributes());
	}

	@Test
	public void sameRequest() throws Exception {
		Request request = new Request(Method.GET, "riap://component/test");
		Response response = aradon.handle(request);
		assertTrue(request == response.getRequest());
	}

	@Test
	public void getParameter() throws Exception {
		Request request = new Request(Method.GET, "riap://component/test?abcd=efg&int=2");

		aradon.handle(request);

		MultiValueMap mmap = (MultiValueMap) request.getAttributes().get(RadonAttributeKey.FORM_ATTRIBUTE_KEY);

		assertEquals("efg", mmap.getFirstValue("abcd"));
		assertEquals("2", mmap.getFirstValue("int"));
	}

	@Test
	public void postParameter() throws Exception {
		Request request = new Request(Method.GET, "riap://component/test");
		Form form = new Form();
		form.add("abcd", "efg");
		form.add("int", "2");

		request.setEntity(form.getWebRepresentation());

		aradon.handle(request);
		MultiValueMap mmap = (MultiValueMap) request.getAttributes().get(RadonAttributeKey.FORM_ATTRIBUTE_KEY);

		assertEquals("efg", mmap.getFirstValue("abcd"));
		assertEquals("2", mmap.getFirstValue("int"));
	}

	@Test
	public void multiValue() throws Exception {
		Request request = new Request(Method.GET, "riap://component/test");
		Form form = new Form();
		form.add("abcd", "efg");
		form.add("abcd", "hij");

		request.setEntity(form.getWebRepresentation());

		aradon.handle(request);
		MultiValueMap mmap = (MultiValueMap) request.getAttributes().get(RadonAttributeKey.FORM_ATTRIBUTE_KEY);

		assertEquals("efg", mmap.get("abcd"));
		assertEquals(Arrays.asList("efg", "hij").toString(), mmap.getAsList("abcd").toString());
	}

	@Test
	public void pathInfo() throws Exception {
		Request request = new Request(Method.GET, "riap://component/test");
		aradon.handle(request);

		InnerRequest ireq = ((InnerResponse) Response.getCurrent()).getInnerRequest();
		BasePathInfo pinfo = ireq.getPathInfo(aradon);

		String settedname = aradon.getChildService("").getChildren().toArray(new PathService[0])[0].getName();
		assertEquals(settedname, pinfo.getName());
	}

	@Test
	public void multipart() throws Exception {
		at.register("", "/multipart", MultipartLet.class);

		Request request = new Request(Method.POST, "riap://component/multipart");

		MultipartEntity entity = new MultipartEntity();
		entity.addParameter("to", "bleujin@i-on.net");
		entity.addParameter("subject", "한글", Charset.forName("UTF-8"));
		entity.addParameter("content", "안녕하세요.", Charset.forName("UTF-8"));
		entity.addParameter("attach1", new File("./build.xml"));

		request.setEntity(entity.makeRepresentation());
		Response response = aradon.handle(request);

		JsonObject jso = JsonParser.fromString(response.getEntityAsText()).getAsJsonObject();

		assertEquals("bleujin@i-on.net", jso.asString("to"));
		assertEquals("한글", jso.asString("subject"));
		assertEquals("안녕하세요.", jso.asString("content"));
		assertEquals(true, jso.asString("attach1").length() > 10);
	}

}

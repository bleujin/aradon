package net.ion.radon.core.let;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map.Entry;

import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.radon.client.HttpMultipartEntity;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.config.ConfigurationBuilder;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.representation.JsonObjectRepresentation;

import org.apache.commons.fileupload.FileItem;
import org.junit.Before;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class TestRequest {

	private Aradon aradon;

	@Before
	public void setUp() throws Exception {
		aradon = Aradon.create(ConfigurationBuilder.newBuilder().aradon().sections().restSection("").path("get").handler(GetLet.class).addUrlPattern("/test").build()) ;
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
		Aradon aradon = Aradon.create(ConfigurationBuilder.newBuilder().aradon().sections().restSection("another").path("get").handler(GetLet.class).addUrlPattern("/test/{greeting}").build()) ;

		Request request = new Request(Method.GET, "riap://component/another/test/hi");
		Response response = aradon.handle(request);

		assertEquals("hi", request.getAttributes().get("greeting"));
		assertEquals(true, request.getAttributes().get(RadonAttributeKey.ATTRIBUTE_HEADERS) != null);
		assertEquals(true, request.getAttributes().get(RadonAttributeKey.FORM_ATTRIBUTE_KEY) != null);
		assertEquals(true, request.getAttributes().get(RadonAttributeKey.PATH_SERVICE_KEY) != null);

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
		PathConfiguration pinfo = ireq.getPathConfiguration();

		String settedname = aradon.getChildService("").getChildren().toArray(new PathService[0])[0].getName();
		assertEquals(settedname, pinfo.name());
	}

	@Test
	public void multipart() throws Exception {
		Aradon aradon = Aradon.create(ConfigurationBuilder.newBuilder().aradon().sections().restSection("").path("multipart").handler(MultipartLet.class).addUrlPattern("/multipart").build()) ;

		Request request = new Request(Method.POST, "riap://component/multipart");

		HttpMultipartEntity entity = new HttpMultipartEntity();
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

class MultipartLet extends DefaultLet{

	

	@Override
	protected Representation myPost(Representation entity) throws Exception {
		if (MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(), true)) {
			JsonObject jso = new JsonObject() ;
			for (Entry<String, Object> entry : getInnerRequest().getFormParameter().entrySet()) {
				
				if (entry.getValue() instanceof FileItem) {
					FileItem fitem = (FileItem) entry.getValue();
					InputStream is = fitem.getInputStream();
					String output = IOUtil.toString(is) ;
					jso.addProperty(entry.getKey(), output) ;
				} else {
					jso.addProperty(entry.getKey(), ObjectUtil.toString(entry.getValue())) ;
				}
			}
			return new JsonObjectRepresentation(jso) ;
		}
		return new StringRepresentation("not found uploadfile ")  ;
	}

}


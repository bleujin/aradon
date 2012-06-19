package net.ion.radon.impl.let;

import static org.junit.Assert.assertEquals;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;
import net.ion.radon.util.AradonTester;

import org.junit.Before;
import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.representation.StringRepresentation;

public class TestHelloLet {
	private AradonTester at;

	@Before
	public void setUp() throws Exception {
		at = AradonTester.create().register("", "/hello", HelloWorldResource.class);
	}

	@Test
	public void helloGet() throws Exception {
		Request request = new Request(Method.GET, "riap://component/hello");
		Response response = at.getAradon().handle(request);

		assertEquals(Status.SUCCESS_OK, response.getStatus());
		assertEquals(true, response.getEntityAsText().startsWith("Hello"));
	}

	@Test
	public void put() throws Exception {
		Request request = new Request(Method.PUT, "riap://component/hello");
		request.setEntity(new StringRepresentation("bleujin"));
		Response response = at.getAradon().handle(request);

		InnerRequest ireq = ((InnerResponse) Response.getCurrent()).getInnerRequest();
		assertEquals("bleujin", ireq.getContext().getSelfAttributeObject("context.id", String.class));
	}

	@Test
	public void parameter() throws Exception {
		Request request = new Request(Method.GET, "riap://component/hello?abcd=test");
		Response response = at.getAradon().handle(request);

		InnerRequest ireq = ((InnerResponse) Response.getCurrent()).getInnerRequest();
		assertEquals("test", ireq.getFormParameter().get("abcd"));

	}

}

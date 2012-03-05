package net.ion.radon.let;

import static org.junit.Assert.assertEquals;
import net.ion.framework.util.StringUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

public class FirstServer {

	@Test
	public void getName() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/hello", MyFirstLet.class).getAradon();

		AradonClient ac = AradonClientFactory.create(aradon);

		Response response = ac.createRequest("/hello").handle(Method.GET);
		assertEquals(200, response.getStatus().getCode());
		assertEquals("Hello anony", response.getEntityAsText());
		assertEquals("Hello bleujin", ac.createRequest("/hello?name=bleujin").handle(Method.GET).getEntityAsText());

		assertEquals(404, ac.createRequest("/not_reg").handle(Method.GET).getStatus().getCode());
	}

	@Test
	public void runServer() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/hello", MyFirstLet.class).getAradon();

		aradon.startServer(9000);
		// new InfinityThread().startNJoin() ;
		AradonClient ac = AradonClientFactory.create("http://localhost:9000");

		Response response = ac.createRequest("/hello").handle(Method.GET);
		assertEquals(200, response.getStatus().getCode());
		assertEquals("Hello anony", response.getEntityAsText());
		assertEquals("Hello bleujin", ac.createRequest("/hello?name=bleujin").handle(Method.GET).getEntityAsText());

		assertEquals(404, ac.createRequest("/not_reg").handle(Method.GET).getStatus().getCode());
		aradon.stop();
	}

}

class MyFirstLet extends AbstractServerResource {
 
	@Get
	public Representation hi() {
		String name = StringUtil.defaultIfEmpty(getInnerRequest().getParameter("name"), "anony");

		return new StringRepresentation("Hello " + name);
	}
}

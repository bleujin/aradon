package net.ion.radon.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.core.security.ChallengeAuthenticator;
import net.ion.radon.core.security.SimpleVerifier;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.security.User;

public class TestAradonClient {

	@Test
	public void init() throws Exception {
		AradonTester at = AradonTester.create().register("", "/", HelloWorldLet.class);
		at.startServer(9002);

		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9002");
		IAradonRequest request = ac.createRequest("/");

		assertEquals(MediaType.APPLICATION_XML, request.get().getMediaType());
		ac.stop() ;
		at.getAradon().stop();
	}

	@Test
	public void auth() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class);
		IRadonFilter authfilter = new ChallengeAuthenticator("my", new SimpleVerifier("bleujin", "1234"));
		at.getAradon().getChildService("").getConfig().addPreFilter(authfilter);

		AradonClient ac = AradonClientFactory.create(at.getAradon());

		IAradonRequest frequest = ac.createRequest("/hello?abcd=abcd", "bleujin", "notmatch");
		Response fresponse = frequest.handle(Method.GET);
		assertEquals(Status.CLIENT_ERROR_UNAUTHORIZED, fresponse.getStatus());
		

		IAradonRequest srequest = ac.createRequest("/hello?abcd=abcd", "bleujin", "1234");
		Response rep = srequest.handle(Method.GET);
		assertEquals(Status.SUCCESS_OK, rep.getStatus());
		ac.stop() ;
	}

	@Test
	public void parameter() throws Exception {
		AradonTester at = AradonTester.create().register("", "/req", RequestLet.class);
		AradonClient ac = AradonClientFactory.create(at.getAradon());
		
		IAradonRequest req = ac.createRequest("/req");
		req.addParameter("name", "bleujin");

		assertEquals("bleujin", req.getForm().getFirstValue("name"));

		Representation rep = req.post();
		assertEquals("bleujin", rep.getText());
		ac.stop() ;
	}

	@Test
	public void requestUser() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class);
		IRadonFilter authfilter = new ChallengeAuthenticator("my", new SimpleVerifier("bleujin", "1234"));
		at.getAradon().getChildService("").getConfig().addPreFilter(authfilter);

		AradonClient ac = AradonClientFactory.create(at.getAradon());
		IAradonRequest request = ac.createRequest("/hello?abcd=Han", "bleujin", "redf");

		User user = request.getUser();
		assertEquals("bleujin", user.getIdentifier().toString());
		ac.stop() ;
	}


	@Test
	public void whenNotConnected() throws Exception {
		AradonClient ac = AradonClientFactory.create("http://notfoundaddress.com");
		IAradonRequest ar = ac.createRequest("/other/hello?abcd=Han", "bleujin", "redf");
		Response res = ar.handle(Method.GET);
		assertEquals(1001, res.getStatus().getCode());
		try {
			ar.get();
			fail();
		} catch (ResourceException expect) {
		}
	}


	public void testFirst() throws Exception {
		
	}
	


}

class RequestLet extends AbstractServerResource {

	@Post
	public String getName(Representation rep) {
		return getInnerRequest().getParameter("name");
	}
}

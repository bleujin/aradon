package net.ion.radon.client;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.radon.core.filter.IRadonFilter;
import net.ion.radon.core.security.ChallengeAuthenticator;
import net.ion.radon.core.security.SimpleVerifier;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.security.User;

public class TestAradonClient extends TestCase {

	public void testInit() throws Exception {
		AradonTester at = AradonTester.create().register("", "/", HelloWorldLet.class);
		at.startServer(9002);

		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9002");
		IAradonRequest request = ac.createRequest("/");

		assertEquals(MediaType.APPLICATION_XML, request.get().getMediaType());
		at.getAradon().stop() ;
	}

	public void testAuth() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class);
		IRadonFilter authfilter = new ChallengeAuthenticator("my", new SimpleVerifier("bleujin", "1234"));
		at.getAradon().getChildService("").addPreFilter(authfilter) ;
		
		AradonClient ac = AradonClientFactory.create(at.getAradon());
		
		IAradonRequest frequest = ac.createRequest("/hello?abcd=한글한글", "bleujin", "notmatch");
		Response fresponse = frequest.handle(Method.GET);
		assertEquals(Status.CLIENT_ERROR_UNAUTHORIZED, fresponse.getStatus());
		
		IAradonRequest srequest = ac.createRequest("/hello?abcd=한글한글", "bleujin", "1234");
		Response rep = srequest.handle(Method.GET);
		assertEquals(Status.SUCCESS_OK, rep.getStatus());

	}

	public void testRequestUser() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class);
		IRadonFilter authfilter = new ChallengeAuthenticator("my", new SimpleVerifier("bleujin", "1234"));
		at.getAradon().getChildService("").addPreFilter(authfilter) ;

		AradonClient ac = AradonClientFactory.create(at.getAradon());
		IAradonRequest request = ac.createRequest("/hello?abcd=한글한글", "bleujin", "redf");

		User user = request.getUser();
		assertEquals("bleujin", user.getIdentifier().toString());
	}

	public void testNotFoundAddress() throws Exception {
		AradonClient ac = AradonClientFactory.create("http://61.250.201.157:15444");
		IAradonRequest ar = ac.createRequest("/other/hello?abcd=한글한글", "bleujin", "redf");
		try {
			ar.get();
			fail() ;
		} catch (ResourceException expect) {
		}
	}

}

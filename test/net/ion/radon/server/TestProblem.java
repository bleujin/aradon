package net.ion.radon.server;

import java.io.Serializable;

import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.ISerialRequest;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.ConnectorConfiguration;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.junit.Assert;
import org.junit.Test;
import org.restlet.data.Cookie;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class TestProblem {

	@Test
	public void textRequestOnNetty() throws Exception {
		for (int i = 0; i < 10; i++) {
			Aradon aradon = AradonTester.create().register("", "/hello", MyTextLet.class).getAradon();
			aradon.startServer(ConnectorConfiguration.makeNettyHTTPConfig(9000));
			for (int loop = 0; loop < 10; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				Assert.assertEquals("hello", ac.createRequest("/hello").get().getText()) ;;
				ac.stop();
			}
			aradon.destorySelf();
		}
	}

	@Test
	public void textRequestOnJetty() throws Exception {
		for (int i = 0; i < 10; i++) {
			Aradon aradon = AradonTester.create().register("", "/hello", MyTextLet.class).getAradon();
			aradon.startServer(ConnectorConfiguration.makeJettyHTTPConfig(9000));
			for (int loop = 0; loop < 10; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				Debug.line(ac.createRequest("/hello").get().getText());
				ac.stop();
			}
			aradon.destorySelf();
		}
	}

	


	@Test
	public void serialRequestOnNetty() throws Exception {
		for (int i = 0; i < 10; i++) {
			Aradon aradon = AradonTester.create()
				.register("", "/hello", MySerialLet.class).getAradon();
			aradon.startServer(ConnectorConfiguration.makeNettyHTTPConfig(9000));
			for (int loop = 0; loop < 10; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				ISerialRequest req1 = ac.createSerialRequest("/hello");
				Assert.assertEquals("hello", req1.get(String.class)) ;
				ac.stop();
			}
			aradon.destorySelf();
		}
	}

	@Test
	public void serialOnJetty() throws Exception {
		for (int i = 0; i < 10; i++) {
			Aradon aradon = AradonTester.create().register("", "/hello", MySerialLet.class).getAradon();
			aradon.startServer(ConnectorConfiguration.makeJettyHTTPConfig(9000));
			for (int loop = 0; loop < 10; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				ISerialRequest req1 = ac.createSerialRequest("/hello");
				Assert.assertEquals("hello", req1.get(String.class)) ;
				ac.stop();
			}
			aradon.destorySelf();
		}
	}

}

class MySerialLet extends AbstractServerResource {
	@Get
	public ObjectRepresentation<Serializable> hello() {
		return new ObjectRepresentation<Serializable>("hello");
	}

	@Post
	public Representation sayName() {
		Cookie c = getInnerRequest().getCookies().getFirst("name");
		return new ObjectRepresentation<Serializable>(c.getValue());
	}
}


class MyTextLet extends AbstractServerResource {
	
	@Get
	public String hello(){
		return "hello" ;
	}
	
}

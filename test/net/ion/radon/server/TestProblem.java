package net.ion.radon.server;

import java.io.Serializable;

import net.ion.framework.util.Debug;
import net.ion.nradon.let.IServiceLet;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.config.Configuration;
import net.ion.radon.core.config.ConnectorConfig.EngineType;
import net.ion.radon.core.let.AbstractServerResource;

import org.junit.Assert;
import org.junit.Test;
import org.restlet.data.Cookie;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class TestProblem {

	@Test
	public void onNetty() throws Exception {
		for (int i = 0; i < 100; i++) {
			Aradon aradon = Aradon.create(createConfig(EngineType.Netty)).startServer(9000) ;
			for (int loop = 0; loop < 10; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				Assert.assertEquals("hello", ac.createRequest("/hello").get().getText());
				ac.stop();
			}
			aradon.destorySelf();
		}
	}

	@Test
	public void onSimple() throws Exception {
		for (int i = 0; i < 500; i++) {
			Aradon aradon = Aradon.create(createConfig(EngineType.Simple)).startServer(9000) ;
			for (int loop = 0; loop < 10; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				Assert.assertEquals("hello", ac.createRequest("/hello").get().getText());
				;
				ac.stop();
			}
			aradon.destorySelf();
		}
	}

	@Test
	public void onRest() throws Exception {
		for (int i = 0; i < 100; i++) {
			Aradon aradon = Aradon.create(createConfig(EngineType.Unknown)).startServer(9000) ;
			for (int loop = 0; loop < 10; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				Assert.assertEquals("hello", ac.createRequest("/hello").get().getText());
				ac.stop();
			}
			aradon.destorySelf();
		}
	}


	@Test
	public void onJetty() throws Exception {
		for (int i = 0; i < 100; i++) {
			Aradon aradon = Aradon.create(createConfig(EngineType.Jetty)).startServer(9000) ;
			for (int loop = 0; loop < 10; loop++) {
				AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
				Debug.line(ac.createRequest("/hello").get().getText());
				ac.stop();
			}
			aradon.destorySelf();
		}
	}
	
	private Configuration createConfig(EngineType engine){
		return Configuration.newBuilder()
		.server().connector().engine(engine).port(9000)
		.aradon().sections().restSection("").path("hello").addUrlPattern("/hello").handler(MyTextLet.class)
		.build() ;
	}

	

}

class ResourceLet implements IServiceLet {
	
	
}

class MySerialLet extends AbstractServerResource {
	@Get
	public ObjectRepresentation<Serializable> hello() {
		return new ObjectRepresentation<Serializable>(new MyUser());
	}

	@Post
	public Representation sayName() {
		Cookie c = getInnerRequest().getCookies().getFirst("name");
		return new ObjectRepresentation<Serializable>(c.getValue());
	}
}

class MyUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name = "bleujin" ;
	
	public String getName(){
		return name ;
	}
}

class MyTextLet extends AbstractServerResource {

	@Get
	public String hello() {
		return "hello";
	}

}

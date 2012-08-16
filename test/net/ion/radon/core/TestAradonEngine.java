package net.ion.radon.core;

import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.config.ConnectorConfiguration;
import net.ion.radon.core.config.PathConfiguration;
import net.ion.radon.core.config.SectionConfiguration;
import net.ion.radon.core.server.netty.HttpServerHelper;
import net.ion.radon.impl.let.HelloWorldLet;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Server;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;

public class TestAradonEngine {
	
	
	@Test
	public void registerServer() throws Exception {
		Aradon aradon = Aradon.create();
		aradon.getDefaultHost().attach("/trace", TestEngine.class);


		Engine.getInstance().getRegisteredServers().clear();
		Server server = new Server(Protocol.HTTP, 9999, aradon);
		HttpServerHelper helper = new HttpServerHelper(server);
		Engine.getInstance().getRegisteredServers().add(helper);

		helper.start();

		Request request = new Request(Method.GET, "riap://component/trace");
		Response response = aradon.handle(request);
		Debug.line(response.getEntityAsText());

		helper.stop();
	}
	
	
	@Test
	public void testAradon() throws Exception {
		
		Aradon aradon = Aradon.create() ;
		aradon.attach(SectionConfiguration.createBlank("")).attach(PathConfiguration.create("test", "/test", HelloWorldLet.class)) ;
		aradon.startServer(ConnectorConfiguration.makeJettyHTTPConfig(9000)) ;
		
		for (int i = 0; i < 2; i++) {
			AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
			Debug.line(ac.createRequest("/test").get()) ;
			ac.stop() ;
		}
		aradon.stop() ;
	}
	
	@Test
	public void loop() throws Exception {
		for (int k = 0; k < 10; k++) {
			new TestAradonEngine().testAradon() ;
		}
	}
	
	
}

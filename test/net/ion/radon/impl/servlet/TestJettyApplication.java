package net.ion.radon.impl.servlet;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.TestAradon;
import net.ion.radon.core.TestBaseAradon;

import org.restlet.Request;

public class TestJettyApplication extends TestBaseAradon {

	public void testMakeRequest() throws Exception {
		Aradon aradon = testAradon();

		Request r = null;
	}

	
//	public void testInit() throws Exception {
//		initAradon();
//
//		Server server = new ContainerRunner().buildServer("-port", "9095", "-container", "jetty", "-webappRoot", "D:/java/tomcat55/webapps");
//		server.start();
//
//		AradonClient ac = AradonClientFactory.create("http://bleujin.i-on.net:9095");
//		IAradonRequest r = ac.createRequest("/jsp-examples/jsp2/el/basic-arithmetic.jsp");
//
//		Representation rep = r.get();
//
//		Debug.line(rep.getText());
//
//		server.join();
//	}

}

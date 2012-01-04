package net.ion.radon.impl.servlet;

import net.ion.radon.TestAradon;

import org.restlet.Request;

public class TestJettyApplication extends TestAradon {

	public void testMakeRequest() throws Exception {
		initAradon();

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

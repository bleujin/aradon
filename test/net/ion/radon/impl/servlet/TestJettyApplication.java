package net.ion.radon.impl.servlet;

import net.ion.framework.util.Debug;
import net.ion.radon.TestAradon;
import net.ion.radon.client.AnonyRequest;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.IAradonRequest;

import org.eclipse.jetty.server.Server;
import org.restlet.Request;
import org.restlet.representation.Representation;
import org.testatoo.container.ContainerRunner;


public class TestJettyApplication extends TestAradon {
	
	public void testMakeRequest() throws Exception {
		initAradon() ;
		
		Request r = null ; 
	}

	
	public void testInit() throws Exception {
		initAradon() ;

		Server server = new ContainerRunner().buildServer("-port", "9095",  "-container", "jetty", "-webappRoot", "D:/java/tomcat55/webapps") ;
		server.start() ;

		AradonClient ac = AradonClientFactory.create("http://bleujin.i-on.net:9095") ;
		IAradonRequest r = ac.createRequest("/jsp-examples/jsp2/el/basic-arithmetic.jsp") ;
		
		Representation rep = r.get() ;
		
		Debug.line(rep.getText());
		
		server.join() ;
	}

}

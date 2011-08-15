package net.ion.bleujin;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

public class ServletServer extends Application {


	public static void main(String[] args) throws Exception {
		Component component = new Component();
		component.getClients().add(Protocol.FILE);
		component.getServers().add(Protocol.HTTP, 8080);
		component.getDefaultHost().attach(new ServletServer());
		component.start();
	}

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		getConnectorService().getClientProtocols().add(Protocol.FILE);
		// getConnectorService().getClientProtocols().add(Protocol.WAR);
		


		

		Directory dir = new Directory(getContext(), "resource/war/");
		router.attach("test", dir);
		return router;
	}
}

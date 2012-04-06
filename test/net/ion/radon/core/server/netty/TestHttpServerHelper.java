package net.ion.radon.core.server.netty;

import org.junit.Test;
import org.restlet.Server;
import org.restlet.data.Protocol;

public class TestHttpServerHelper {

	@Test
	public void stop() throws Exception{
		Server server = new Server(Protocol.HTTP, 9000);
		HttpServerHelper s = new HttpServerHelper(server) ;
		s.start() ;
		s.stop() ;
		server.stop() ;
	}
}

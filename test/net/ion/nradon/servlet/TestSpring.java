package net.ion.nradon.servlet;

import java.io.File;

import net.ion.nradon.HttpHandler;
import net.ion.nradon.WebServer;
import net.ion.nradon.WebServers;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Ignore;
import org.junit.Test;

public class TestSpring {

	public void runSpring() {
		WebServer server = WebServers.createWebServer(8000) ;
		
		HttpHandler handler = null ;
		server.add(handler) ;
	}
	
	
	@Ignore("jetty run")
	@Test
	public void testRun() throws Exception {

		Server server = new Server();
		SelectChannelConnector connector = new SelectChannelConnector();
		connector.setPort(9000);
		connector.setReuseAddress(false);
		server.addConnector(connector);
		

		ContextHandlerCollection col = new ContextHandlerCollection();
		ContextHandlerCollection context = new ContextHandlerCollection();

		col.addHandler(context);
		col.addHandler(new DefaultHandler());
		col.addHandler(new RequestLogHandler());
		server.setHandler(col);

		// XmlConfiguration configuration = new XmlConfiguration(new File(PathMaker.getFilePath(configBase, JETTY_XML)).toURL());
		// configuration.configure(server);

		WebAppContext wac = new WebAppContext();
		wac.setWar((new File("./resource/servlet/webapps")).getCanonicalPath());
		wac.setContextPath("/my");
		wac.setParentLoaderPriority(true);
		wac.setMaxFormContentSize(5000000);
		server.setHandler(wac);

		System.setProperty("org.eclipse.equinox.http.jetty.autostart", "false");

		server.start();
		server.join();
	}
	
	
}

package net.ion.radon;

import net.ion.radon.core.Aradon;
import net.ion.radon.core.AradonServer;

import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.engine.adapter.HttpServerHelper;

public class LocalServer {

	LocalServer() {
	}

	Aradon start(String configFileName, int port) throws Exception {
		Aradon aradon = Aradon.create(configFileName) ;
		
		aradon.getStatusService().setContactEmail("bleujin@i-on.net") ;
		aradon.getStatusService().setHomeRef(new Reference("http://localhost/")) ;
		
		aradon.getServers().add(Protocol.HTTP, 9002);
		aradon.getClients().add(Protocol.FILE);

		// Now, let's start the component!
		// Note that the HTTP server connector is also automatically started.
		Server jserver = new Server(aradon.getContext(), Protocol.HTTP, port, aradon);
		HttpServerHelper serverHelper = new HttpServerHelper(jserver);
		serverHelper.start();
		
		// create embedding AJP Server
		// Server embedingJettyAJPServer = new Server(aradon.getContext(), Protocol.HTTPS, port+1, aradon);
		// AjpServerHelper ajpServerHelper = new AjpServerHelper(embedingJettyAJPServer);
		// ajpServerHelper.start();
		return aradon ;
	}


	public static void main(final String[] args) throws Exception {
		try {
			/*Options options = new Options(args) ;
			
			String configFileName = options.getString("config", "resource/config/aradon-config.xml") ;
			int port = options.getInt("port", 9002) ;
			
			Aradon aradon = new LocalServer().start(configFileName, port);
			LocalServer.shutdownHook(aradon) ;
			Debug.debug("Aradon Server Start") ;*/
			
			String[] opts = new String[]{"-port:9002", "-config:resource/config/aradon-config.xml"} ;
			AradonServer as = new AradonServer(new Options(opts)) ;
			as.start() ;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}

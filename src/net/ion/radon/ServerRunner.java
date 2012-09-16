package net.ion.radon;

import java.io.File;

import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.AradonServer;

import org.restlet.Response;
import org.restlet.data.Method;

public class ServerRunner {

	private ServerRunner() {
	}

	public static void main(String[] args) throws Exception {
		Options option = new Options(args);
		String configPath = option.getString("config", "./resource/config/aradon-config.xml");
		String action = option.getString("action", "restart");

		File configFile = new File(configPath);
		if (!configFile.exists()) {
			configFile = new File(configPath + ".tpl");
			if (!configFile.exists()) {
				throw new IllegalStateException("config file not found : " + configPath);
			}
		}
		
		AradonServer server = new AradonServer(option) ;
		int port = server.getPort() ;
		Debug.line("configPath", configPath, "action", action, "port", port);

		if ("stop".equals(action)) {
			stopServer(port);
			System.exit(0);
		}

		if ("restart".equals(action)) {
			stopServer(port);
		}

		new AradonServer(option).start(port) ;
		// new InfinityThread().startNJoin();
	}

	private static void stopServer(int port) {
		try {
			AradonClient ac = AradonClientFactory.create("http://127.0.0.1:" + port);
			Response response = ac.createRequest("/shutdown?timeout=100").handle(Method.DELETE);
			if (response.getStatus().isSuccess()) Debug.line(response.getEntityAsText());
			Thread.sleep(1000);
		} catch (Throwable ignore) {
			Debug.line(ignore);
		}
	}
}

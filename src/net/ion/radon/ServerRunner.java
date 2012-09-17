package net.ion.radon;

import java.io.File;
import java.io.InputStream;

import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.radon.core.AradonServer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

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

		server.start(port) ;
		// new InfinityThread().startNJoin();
	}

	private static void stopServer(int port) {
		HttpClient client = new DefaultHttpClient() ;
		InputStream input = null;
		try {
			HttpDelete deleteMethod = new HttpDelete("http://127.0.0.1:" + port + "/shutdown?timeout=100") ;
			HttpResponse response = client.execute(deleteMethod);
			input = response.getEntity().getContent();
			Debug.line(IOUtil.toString(input)) ;
			
//			AradonClient ac = AradonClientFactory.create("http://127.0.0.1:" + port);
//			Response response = ac.createRequest("/shutdown?timeout=100").handle(Method.DELETE);
//			if (response.getStatus().isSuccess()) Debug.line(response.getEntityAsText());
//			Thread.sleep(1000);
		} catch (Throwable ignore) {
			Debug.line("stopping aradon. but " + ignore.getMessage());
		} finally {
			IOUtil.closeQuietly(input) ;
			client.getConnectionManager().shutdown() ;
		}
	}
}

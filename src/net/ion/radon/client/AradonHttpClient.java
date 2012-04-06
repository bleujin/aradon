package net.ion.radon.client;

import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.engine.connector.HttpClientHelper;
import org.restlet.ext.ssl.HttpsClientHelper;
import org.restlet.ext.ssl.SslContextFactory;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.util.Series;

public class AradonHttpClient implements AradonClient {
	private String host;
	private HttpClientHelper client;

	private AradonHttpClient(String host) {
		this.host = host;
		this.client = new HttpClientHelper(new Client(ListUtil.toList(Protocol.HTTP, Protocol.HTTPS)));
	}

	public final static AradonHttpClient create(String hostAddress) {
		return new AradonHttpClient(hostAddress);
	}

	public BasicRequest createRequest(String path) {
		return BasicRequest.create(this, path, "anony", "");
	}

	public BasicRequest createRequest(String path, String id, String pwd) {
		return BasicRequest.create(this, path, id, pwd);
	}

	public BasicSerialRequest createSerialRequest(String path) {
		return BasicSerialRequest.create(this, path, "anony", "");
	}

	public BasicSerialRequest createSerialRequest(String path, String id, String pwd) {
		return BasicSerialRequest.create(this, path, id, pwd);
	}

	public IJsonRequest createJsonRequest(String path) {
		return BasicJsonRequest.create(this, path, "anony", "");
	}

	public IJsonRequest createJsonRequest(String path, String id, String pwd) {
		return BasicJsonRequest.create(this, path, id, pwd);
	}

	
	public void stop() throws Exception {
		client.stop();
	}

	public String getHostAddress() {
		return host;
	}

	Representation handleRequest(Request request) {
		Response response = handle(request);

		if (!response.getStatus().isSuccess()) {
			throw new ResourceException(response.getStatus());
		}

		return response.getEntity();
	}

	Response handle(Request request) {
		Response response = client.getHelped().handle(request);
		return response;
		// Response response = new Response(request) ;
		// client.handle(request, response) ;
		// return response ;
	}

	Client getClient() {
		return client.getHelped();
	}

	public String toString() {
		return host;
	}

	void setReliable() {
		final SSLContext sslClientContext = getCustomSSLContext();

		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new javax.net.ssl.HostnameVerifier() {
			public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
				return true;
			}
		});

		if (client.getContext() == null)
			client.getHelped().setContext(new Context());
		
		client.getContext().getAttributes().put("sslContextFactory", new SslContextFactory() {
			@Override
			public void init(Series<Parameter> param) {
			}

			@Override
			public SSLContext createSslContext() throws Exception {
				return sslClientContext;
			}
		});
	}

	private SSLContext getCustomSSLContext() {
		SSLContext sc = null;
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
				// return TrustManager[] ;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {

			}
		} };
		// Install the all-trusting trust manager
		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sc;
	}

}

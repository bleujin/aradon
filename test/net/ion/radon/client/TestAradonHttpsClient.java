package net.ion.radon.client;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.framework.util.MapUtil;
import net.ion.radon.core.config.ConnectorConfiguration;
import net.ion.radon.core.config.ConnectorConfig.EngineType;
import net.ion.radon.impl.let.HelloWorldLet;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

public class TestAradonHttpsClient {

	@Test
	public void get() throws Exception{
		AradonClient ac = AradonClientFactory.create("https://www.google.co.kr");
		Response res = ac.createRequest("/").handle(Method.GET);

		assertEquals(200, res.getStatus().getCode());
		ac.stop() ;
	}

	@Test
	public void callReliableHTTPS() throws Exception {
		AradonClient ac = AradonClientFactory.create("https://61.250.201.157:9000", true) ;
		
		Response response = ac.createRequest("/hello").handle(Method.GET) ;
		Debug.line(response.getStatus(), response.getEntityAsText());
	}


	@Test
	public void testHttps() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello", HelloWorldLet.class);
		Map<String, String> properties = MapUtil.<String>chainKeyMap()
			.put("keystorePath", "./resource/keystore/keystore")
			.put("keystorePassword", "password")
			.put("keystoreType", "JKS")
			.put("keyPassword", "password")
			.toMap();
		at.getAradon().startServer(ConnectorConfiguration.create(EngineType.Simple, Protocol.HTTPS, 9000, properties));

		new InfinityThread().startNJoin();
	}

//	@Test
//	public void getAuth() throws IOException {
//
//		// HttpURLConnection.setFollowRedirects(true) ;
//
//		// Engine.getInstance().getRegisteredClients().clear();
//		// Engine.getInstance().getRegisteredClients().add(new HttpClientHelper(null));
//		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//
//			public boolean verify(String arg0, SSLSession arg1) {
//				return true;
//			}
//		});
//
//		Client inner = new Client(Protocol.HTTPS);
//		if (inner.getContext() == null)
//			inner.setContext(new Context());
//		Context context = inner.getContext();
//		Series<Parameter> parameters = context.getParameters();
//		// parameters.add("truststorePath", new File("./resource/imsi/tipns_appservice.conf").getCanonicalPath());
//		parameters.add("sslContextFactory", "org.restlet.ext.ssl.DefaultSslContextFactory");
//		parameters.add("truststorePassword", "");
//		parameters.add("tracing", "true");
//
//		HttpsClientHelper client = new HttpsClientHelper(inner);
//		Request request = new Request(Method.GET, "https://61.250.201.157:9000/hello");
//		Response response = new Response(request) ;
//		client.handle(request, response);
//		Debug.line(response.getEntityAsText());
//
//		assertEquals(200, response.getStatus().getCode());
//	}

//	@Test
//	public void apacheCall() throws Exception {
//		SSLContext sslClientContext = getCustomSSLContext();
//
//		HttpClient httpClient = new HttpClient();
//		SslContextedSecureProtocolSocketFactory secureProtocolSocketFactory = new SslContextedSecureProtocolSocketFactory(sslClientContext);
//		secureProtocolSocketFactory.setHostnameVerification(false);
//		org.apache.commons.httpclient.protocol.Protocol.registerProtocol("https", new org.apache.commons.httpclient.protocol.Protocol("https", (ProtocolSocketFactory) secureProtocolSocketFactory, 443));
//
//		PostMethod method = new PostMethod("https://61.250.201.157:9000/hello");
//		int statusCode = httpClient.executeMethod(method);
//		Debug.line(statusCode, method.getResponseBodyAsString());
//		
//	}
}

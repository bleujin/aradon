package net.ion.radon.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.framework.util.ListUtil;

import org.restlet.Client;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Cookie;
import org.restlet.data.CookieSetting;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.ext.net.HttpClientHelper;
import org.restlet.ext.ssl.SslContextFactory;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.service.ConverterService;
import org.restlet.util.Series;

public class AradonHttpClient implements AradonClient, Closeable {
	private String host;
	private HttpClientHelper client;
	private ExecutorService es;

	private ConverterService cs = new ConverterService();
	private Series<CookieSetting> cookies;

	private AradonHttpClient(String host) {
		this.host = host;
		this.client = new HttpClientHelper(new Client(ListUtil.toList(Protocol.HTTP, Protocol.HTTPS)));

		// http://www.restlet.org/documentation/2.0/jee/ext/org/restlet/ext/httpclient/HttpClientHelper.html
		
		if (client.getHelped().getContext() == null){
			Context context = new Context();
			context.getParameters().add(new Parameter("socketTimeout","4000")) ;
			this.client.getHelped().setContext(context);
		}

		this.es = Executors.newCachedThreadPool();
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
		AradonClientFactory.remove(this);
		client.getHelped().stop();
		client.stop() ;
		// client.getHelped().stop();
		es.shutdown();
		// es.awaitTermination(1, TimeUnit.SECONDS);
		// es.shutdownNow();
	}

	public String getHostAddress() {
		return host;
	}

	Representation handleRequest(Request request) {
		Response response = syncHandle(request);
		if (response.getStatus().isConnectorError())
			throw new ResourceException(response.getStatus().getCode());
		return response.getEntity();
	}

	<T> Future<T> asyncHandle(final Request request, final AsyncHttpHandler<T> ahandler) {
		final AradonHttpClient c = this;
		return es.submit(new Callable<T>() {
			public T call() throws Exception {
				Response response = c.innerHandle(request);
				try {
					if (response.getStatus().isConnectorError() || response.getStatus().isServerError() || response.getStatus().isClientError()) {
						ahandler.onError(request, response);
						return null;
					} else
						return ahandler.onCompleted(request, response);
				} finally {
					Representation entity = response.getEntity();
					if (entity != null)
						entity.release();
//					response.release();
				}
			}
		});
	}

	Response syncHandle(Request request) {
		Response response = innerHandle(request);
		InputStream input = null;
		Representation oldEntity = null;
		try {

			if (response.getStatus().isConnectorError() || response.getStatus().isServerError() || response.getStatus().isClientError()) {
				Debug.warn(response) ;
				return response;
			}
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			oldEntity = response.getEntity();
			input = oldEntity.getStream();
			IOUtil.copyNClose(input, output);
			InputRepresentation copyEntity = new InputRepresentation(new ByteArrayInputStream(output.toByteArray()));
			copyEntity.setMediaType(oldEntity.getMediaType());
			response.setEntity(copyEntity);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		} finally {
			IOUtil.closeQuietly(input);
			if (oldEntity != null)
				oldEntity.release();
			//response.commit() ;
			//request.getEntity().release() ;
		}
		return response;
	}

	private Response innerHandle(Request request) {
		try {
			if (!client.getHelped().isStarted()) {
				client.getHelped().start() ;
			}
			
			
			if (cookies != null) {
				for (CookieSetting cs : cookies) {
					Cookie c = new Cookie(cs.getVersion(), cs.getName(), cs.getValue(), cs.getPath(), cs.getDomain());
					request.getCookies().add(c);
				}
			}
			
			Response response = client.getHelped().handle(request);
			
//			Response response = new Response(request) ;
//			client.handle(request, response);

			
//			Response response ;
//			if (request.getProtocol().equals(Protocol.CLAP)) {
//				response = new Response(request) ;
//				client.handle(request, response);
//			} else {
//				response = client.getHelped().handle(request);
//			}
			
			cookies = response.getCookieSettings();

			return response;
		} catch (Throwable ex) {
			return null;
		}
		// Response response = new Response(request) ;
		// client.handle(request, response) ;
		// return response ;
	}

	<T> Representation toRepresentation(T arg) {
		Representation result = null;
		if (arg != null) {
			result = getConverterService().toRepresentation(arg);
		}
		return result;
	}

	private ConverterService getConverterService() {
		return cs;
	}

	public String toString() {
		return host;
	}

	void setReliable()  {

		final SSLContext sslClientContext = getCustomSSLContext();

		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		if (client.getHelped().getContext() == null){
			client.getHelped().setContext(new Context());
		}

		// new SSLSocketFactory(sslClientContext.getInstance("TLS"), SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER) ;
		client.getHelped().getContext().getAttributes().put("sslContextFactory", new SslContextFactory() {
			@Override
			public void init(Series<Parameter> param) {
			}

			@Override
			public SSLContext createSslContext() throws Exception {
				return sslClientContext;
			}
		});
		client.getHelped().getContext().getAttributes().put("hostnameVerifier", org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER) ;
		
		
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

	public void close() throws IOException {
		try {
			stop();
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

}

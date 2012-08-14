package net.ion.radon.client;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.ion.radon.core.Aradon;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Cookie;
import org.restlet.data.CookieSetting;
import org.restlet.service.ConverterService;
import org.restlet.util.Series;

public class AradonInnerClient implements AradonClient, Closeable{

	private Aradon aradon;
	private ExecutorService es ;
	private Series<CookieSetting> cookies;
	
	private AradonInnerClient(Aradon aradon) {
		this.aradon = aradon;
		this.es = Executors.newCachedThreadPool() ;
	} 
	
	public static AradonInnerClient create(Aradon aradon){
		return new AradonInnerClient(aradon);
	}
	
	public IAradonRequest createRequest(String path) {
		return createRequest(path, "anony", "");
	}

	public IAradonRequest createRequest(String path, String id, String pwd) {
		return AradonRequest.create(this, es, path, id, pwd);
	}

	public ISerialRequest createSerialRequest(String path) {
		return AradonSerialRequest.create(this, path, "anony", "");
	}

	public ISerialRequest createSerialRequest(String path, String id, String pwd) {
		return AradonSerialRequest.create(this, path, id, pwd);
	}
	
	public IJsonRequest createJsonRequest(String path) {
		return AradonJsonRequest.create(this, path, "anony", "");
	}

	public IJsonRequest createJsonRequest(String path, String id, String pwd) {
		return AradonJsonRequest.create(this, path, id, pwd);
	}
	
	
	public Response handle(Request request){
		if (cookies != null) {
			for (CookieSetting cs : cookies) {
				Cookie c = new Cookie(cs.getVersion(), cs.getName(), cs.getValue(), cs.getPath(), cs.getDomain()) ;
				request.getCookies().add(c);
			}
		}
		
		Response response = aradon.handle(request);
		cookies = response.getCookieSettings(); 
		return response ;
	}
	
	public String getHostAddress() {
		return "riap://component";
	}

	public void stop() throws Exception {
		AradonClientFactory.remove(this);
//		es.awaitTermination(1, TimeUnit.SECONDS) ;
		es.shutdown() ;
	}

	public ConverterService getConverterService() {
		return aradon.getConverterService();
	}
	
	public void close() throws IOException {
		try {
			stop() ;
		} catch (Exception e) {
			throw new IOException(e.getMessage()) ;
		}
	}

}

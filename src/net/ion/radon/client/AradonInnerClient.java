package net.ion.radon.client;

import java.util.concurrent.ExecutorService;

import net.ion.radon.core.Aradon;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Cookie;
import org.restlet.data.CookieSetting;
import org.restlet.service.ConverterService;
import org.restlet.util.Series;

public class AradonInnerClient implements AradonClient{

	private Aradon aradon;
	private ExecutorService es ;
	private Series<CookieSetting> cookies;
	
	private AradonInnerClient(Aradon aradon, ExecutorService es) {
		this.aradon = aradon;
		this.es = es ;
	} 
	
	public static AradonInnerClient create(Aradon aradon, ExecutorService es){
		return new AradonInnerClient(aradon, es);
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
		AradonClientFactory.shutdownNow(es) ;
	}

	public ConverterService getConverterService() {
		return aradon.getConverterService();
	}
	

}

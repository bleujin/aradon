package net.ion.radon.client;

import java.util.concurrent.ExecutorService;

import net.ion.radon.core.Aradon;

public class AradonInnerClient implements AradonClient{

	private Aradon aradon;
	private ExecutorService es ;
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
		return AradonRequest.create(aradon, es, path, id, pwd);
	}

	public ISerialRequest createSerialRequest(String path) {
		return AradonSerialRequest.create(aradon, path, "anony", "");
	}

	public ISerialRequest createSerialRequest(String path, String id, String pwd) {
		return AradonSerialRequest.create(aradon, path, id, pwd);
	}
	
	public IJsonRequest createJsonRequest(String path) {
		return AradonJsonRequest.create(aradon, path, "anony", "");
	}

	public IJsonRequest createJsonRequest(String path, String id, String pwd) {
		return AradonJsonRequest.create(aradon, path, id, pwd);
	}
	
	
	public String getHostAddress() {
		return "riap://component";
	}

	public void stop() throws Exception {
		AradonClientFactory.shutdownNow(es) ;
	}
	

}

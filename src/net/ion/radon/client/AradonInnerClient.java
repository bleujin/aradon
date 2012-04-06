package net.ion.radon.client;

import net.ion.radon.core.Aradon;

public class AradonInnerClient implements AradonClient{

	private Aradon aradon;

	private AradonInnerClient(Aradon aradon) {
		this.aradon = aradon;
	} 
	
	public static AradonInnerClient create(Aradon aradon){
		return new AradonInnerClient(aradon);
	}
	
	public IAradonRequest createRequest(String path) {
		return createRequest(path, "anony", "");
	}

	public IAradonRequest createRequest(String path, String id, String pwd) {
		return AradonRequest.create(aradon, path, id, pwd);
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
	}
	

}

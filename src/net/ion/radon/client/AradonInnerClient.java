package net.ion.radon.client;

import net.ion.radon.core.Aradon;

public class AradonInnerClient implements AradonClient{

	
	private Aradon aradon;
	
	
	public AradonInnerClient(){
		
	}
	
	public void setAradon(Aradon aradon){
		this.aradon = aradon ;
	}
	
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
		return AradonInnerRequest.create(aradon, path, id, pwd);
	}

	public String getHostAddress() {
		return null;
	}

	public void stop() throws Exception {
	}

}

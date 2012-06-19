package net.ion.radon.client;


public interface AradonClient {

	public IAradonRequest createRequest(String path) ;
	public IAradonRequest createRequest(String path, String id, String pwd) ;

	public ISerialRequest createSerialRequest(String path) ;
	public ISerialRequest createSerialRequest(String path, String id, String pwd) ;

	public IJsonRequest createJsonRequest(String path);
	public IJsonRequest createJsonRequest(String path, String id, String pwd) ;


	public void stop() throws Exception  ;
	
	public String getHostAddress() ;
//
//	Representation handle(Request request) ;

}

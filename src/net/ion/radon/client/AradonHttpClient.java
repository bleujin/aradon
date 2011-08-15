package net.ion.radon.client;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;

public class AradonHttpClient implements AradonClient{
	private String host;
	private Client client;

	private AradonHttpClient(String host) {
		this.host = host;
		this.client = new Client(Protocol.HTTP);
	}

	public final static AradonHttpClient create(String hostAddress) {
		return new AradonHttpClient(hostAddress);
	}

	public AnonyRequest createRequest(String path) {
		return AnonyRequest.create(this, path, "anony", "");
	}

	public BasicRequest createRequest(String path, String id, String pwd) {
		return BasicRequest.create(this, path, id, pwd);
	}

	public void stop() throws Exception {
		client.stop();
	}

	public String getHostAddress() {
		return host;
	}

	Representation handle(Request request) {
		return client.handle(request).getEntity();
	}
	
	Client getClient(){
		return client ;
	}
}

package net.ion.radon.client;

import net.ion.framework.util.ListUtil;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;

public class AradonHttpClient implements AradonClient{
	private String host;
	private Client client;

	private AradonHttpClient(String host) {
		this.host = host;
		this.client = new Client(ListUtil.toList(Protocol.HTTP, Protocol.HTTPS));
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
	
	public void stop() throws Exception {
		client.stop();
	}

	public String getHostAddress() {
		return host;
	}

	Representation handle(Request request) {
		Response response = client.handle(request);
		return response.getEntity();
	}
	
	Client getClient(){
		return client ;
	}
	
	public String toString(){
		return host ;
	}
}

package net.ion.radon.client;

import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

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

	Representation handleRequest(Request request) {
		Response response = handle(request);
		
		if (! response.getStatus().isSuccess()){
			throw new ResourceException(response.getStatus()) ;
		}
		
		return response.getEntity();
	}
	
	Response handle(Request request){
		return client.handle(request);
	}
	
	
	Client getClient(){
		return client ;
	}
	
	public String toString(){
		return host ;
	}
}

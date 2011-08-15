package net.ion.radon.client;

import org.restlet.Client;
import org.restlet.data.Protocol;
import org.restlet.engine.application.Decoder;
import org.restlet.resource.ClientResource;

public class AradonRequestUtil {

	public final static AradonClientResource createClientResource(String uri, Client client){
		AradonClientResource resource =  new AradonClientResource(uri);
		Decoder decoder = new Decoder(resource.getContext(), false, true);
		decoder.setNext(client);
		resource.setNext(decoder);
		return resource ;
	} 
}

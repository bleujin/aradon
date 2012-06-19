package net.ion.radon.param.request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.parse.gson.JsonParser;
import net.ion.radon.core.let.DefaultLet;
import net.ion.radon.core.let.LetResponse;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;

public class AradonRequest {

	private Map<String, SingleRequest> srs ; 
	private AradonRequest() {
		this.srs = new HashMap<String, SingleRequest>() ;
	}

	static AradonRequest create() {
		return new AradonRequest();
	}
	
	public SingleRequest getRequest(String name){
		return srs.get(name) ;
	}

	void append(SingleRequest sr) {
		srs.put(sr.getName(), sr) ;
	}

	private Map<String, LetResponse> result = new HashMap<String, LetResponse>() ;
	
	
	LetResponse getRequestResult(String name, DefaultLet radonLet) throws IOException{
		if (result.containsKey(name)) {
			return result.get(name) ;
		} else {
			final LetResponse repr = srs.get(name).handle(this, radonLet);
			result.put(name, repr) ;
			return getRequestResult(name, radonLet) ;
		}
	}
	
	public Representation handle(DefaultLet radonLet) throws IOException{
		for (Entry<String, SingleRequest> entry : srs.entrySet()) {
			String name = entry.getKey()  ;
			if (result.containsKey(name)){
				continue ;
			}
			
			LetResponse repr = entry.getValue().handle(this, radonLet) ;
			result.put(name, repr) ;
		}
		
		JsonObject letResults = new JsonObject() ;
		for (Entry<String, LetResponse> entry : result.entrySet()) {
			letResults.add(entry.getKey(), JsonParser.fromString(entry.getValue().getRepresentation().getText())) ;
		}
		
		JsonObject results = new JsonObject() ;
		results.add("results", letResults) ;
		
		return new StringRepresentation(results.toString(), MediaType.APPLICATION_JSON) ;
		// return new StringRepresentation(result.toString());
	}

	

}

package net.ion.radon.param.request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.radon.core.let.DefaultLet;
import net.ion.radon.core.let.LetResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

public class ARadonRequest {

	private Map<String, SingleRequest> srs ; 
	private ARadonRequest() {
		this.srs = new HashMap<String, SingleRequest>() ;
	}

	static ARadonRequest create() {
		return new ARadonRequest();
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
	
	public Representation handle(DefaultLet radonLet) throws IOException, JSONException {
		for (Entry<String, SingleRequest> entry : srs.entrySet()) {
			String name = entry.getKey()  ;
			if (result.containsKey(name)){
				continue ;
			}
			
			LetResponse repr = entry.getValue().handle(this, radonLet) ;
			result.put(name, repr) ;
		}
		
		JSONObject letResults = new JSONObject() ;
		for (Entry<String, LetResponse> entry : result.entrySet()) {
			letResults.put(entry.getKey(), new JSONObject(entry.getValue().getRepresentation().getText())) ;
		}
		
		JSONObject results = new JSONObject() ;
		results.put("results", letResults) ;
		
		return new JsonRepresentation(results) ;
		// return new StringRepresentation(result.toString());
	}

	

}

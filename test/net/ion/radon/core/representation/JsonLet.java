package net.ion.radon.core.representation;

import org.restlet.resource.Get;
import org.restlet.resource.Post;

import net.ion.framework.parse.gson.JsonElement;
import net.ion.framework.parse.gson.JsonParser;
import net.ion.radon.core.let.AbstractServerResource;

public class JsonLet extends AbstractServerResource{

	
	@Get
	public JsonObjectRepresentation print(){
		JsonElement jsonElement = JsonParser.fromString("{greeting:'hello', address:{city:'seoul'}, age:20, color:['red', 'blue']}");
		
		return new JsonObjectRepresentation(jsonElement).setIndenting(true) ;
		
	}

	@Post
	public JsonElement printPost(){
		JsonElement jsonElement = JsonParser.fromString("{greeting:'hello', address:{city:'seoul'}, age:20, color:['red', 'blue']}");
		
		return jsonElement;
		
	}

}

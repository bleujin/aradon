package net.ion.radon.core.representation;

import junit.framework.TestCase;
import net.ion.framework.parse.gson.JsonElement;
import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.util.Debug;
import net.ion.framework.util.InfinityThread;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.restlet.Response;
import org.restlet.data.Language;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class TestRepresentation extends TestCase {
	
	public void testCreate() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", JsonLet.class).getAradon() ;
		
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		Representation rep = ac.createRequest("/test").get() ;
		
		Debug.line(rep.getText()); 
	}
	
	public void testToObject() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", JsonLet.class).getAradon() ;
		aradon.getEngine().getRegisteredConverters().add(new JsonObjectConverter()) ;
		
		AradonClient ac = AradonClientFactory.create(aradon) ;
		Representation rep = ac.createRequest("/test").post() ;
		Response res = ac.createRequest("/test").handle(Method.POST) ;
		assertEquals(true, rep != null); 
	}
	
	public void xtestXmlOnSafari() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/test", XMLLet.class).getAradon() ;
		
		aradon.startServer(9000) ;
		
		new InfinityThread().startNJoin() ;
		
	}
}

class XMLLet extends AbstractServerResource {

	@Get
	public Representation myGet() throws Exception {
		
		String text = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n\n<result><message>Hello World</message></result>" ;
		
		StringRepresentation sr = new StringRepresentation(text, MediaType.APPLICATION_XML, Language.valueOf("UTF-8")) ;
		getInnerResponse().getHeaders().add("Content-Type", "text/xml") ;
		
		return sr ;
	}
}

class JsonLet extends AbstractServerResource{

	
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

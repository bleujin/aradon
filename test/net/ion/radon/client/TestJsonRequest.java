package net.ion.radon.client;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.parse.gson.JsonSyntaxException;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.core.representation.BeanToJsonFilter;
import net.ion.radon.core.representation.PlainObjectConverter;
import net.ion.radon.util.AradonTester;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class TestJsonRequest {

	
	private Aradon aradon ;
	
	@Before
	public void setUp() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", JsonLet.class) ;
		this.aradon = at.getAradon();
		
		aradon.getEngine().getRegisteredConverters().add(new PlainObjectConverter()) ;
		aradon.getConfig().addAfterFilter(BeanToJsonFilter.create()) ;
		
	}
	
	@After
	public void tearDown() {
		aradon.destorySelf() ;
	}
	
	@Test
	public void basicGet() throws Exception {
		aradon.startServer(9000) ;
		
		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9000");
		IJsonRequest request = ac.createJsonRequest("/test");
		
		NoSerialUser muser = request.get(NoSerialUser.class) ;
		assertEquals("jin", muser.getName()) ;
		assertEquals("seoul", muser.getAddress().getCity()) ;
		ac.stop() ;
	}
	
	@Test
	public void basicPost() throws Exception {
		aradon.startServer(9250) ;

		AradonClient ac = AradonClientFactory.create("http://127.0.0.1:9250");
		IJsonRequest request = ac.createJsonRequest("/test");
		
		NoSerialUser muser = request.post(new NoSerialUser("hero", 20), NoSerialUser.class) ;
		assertEquals("hero", muser.getName()) ;
		assertEquals("seoul", muser.getAddress().getCity()) ;
		assertEquals(21, muser.getAge()) ;
		ac.stop() ;
	}

}

class JsonLet extends AbstractServerResource {
	
	@Get
	public NoSerialUser modUser(){
		return new NoSerialUser("jin", 20) ;
	}
	
	@Post
	public NoSerialUser postUser(Representation entity) throws JsonSyntaxException, IOException{
		NoSerialUser user = JsonParser.fromString(entity.getText()).getAsJsonObject().getAsObject(NoSerialUser.class);
		return user.oneYearAfter() ;
	}
	
}

class NoSerialUser {
	private String name ;
	private int age ;
	private Address address = new Address() ;
	public NoSerialUser(String name, int age){
		this.name = name ;
		this.age = age ;
	}
	
	public String getName(){
		return name ;
	}
	
	public int getAge(){
		return age ;
	}
	
	public Address getAddress(){
		return address;
	}
	
	public NoSerialUser oneYearAfter(){
		this.age++ ;
		return this ; 
	}
}

class Address {
	private String city = "seoul" ;
	public String getCity(){
		return city ;
	}
}



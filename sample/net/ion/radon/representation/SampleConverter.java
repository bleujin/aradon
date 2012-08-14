package net.ion.radon.representation;

import java.io.IOException;

import junit.framework.TestCase;
import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.parse.gson.JsonSyntaxException;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.EnumClass.ILocation;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.core.representation.BeanToJsonFilter;
import net.ion.radon.core.representation.PlainObjectConverter;
import net.ion.radon.util.AradonTester;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.restlet.data.Method;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class SampleConverter extends TestCase{

	
	public void testWhenNotRegistered() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/view", MyLet.class).getAradon() ;
//		aradon.getEngine().getRegisteredConverters().add(new PlainObjectConverter()) ;
	
		AradonClient ac = AradonClientFactory.create(aradon) ;
		User user = ac.createSerialRequest("/view").get(User.class) ;
		assertEquals(true, user == null) ;
	}
	
	public void testWhenRegistered() throws Exception {
		Aradon aradon = AradonTester.create().mergeSection("")
			.addLet("/view", "mylet", MyLet.class)
			.addFilter(ILocation.AFTER, BeanToJsonFilter.create())
			.getAradon() ;
		
		aradon.getEngine().getRegisteredConverters().add(new PlainObjectConverter()) ;
		aradon.startServer(9000) ;
	
		AradonClient ac = AradonClientFactory.create("http://localhost:9000") ;
		User user = ac.createJsonRequest("/view").get(User.class) ;
		
		assertEquals("bleujin", user.name) ;
		assertEquals(20, user.age) ;
		assertEquals("seoul", user.address.getCity()) ;
		
		ac.stop() ;
		aradon.stop() ;
	}
	
	public void testPost() throws Exception {
		Aradon aradon = AradonTester.create().mergeSection("")
			.addLet("/view", "mylet", MyLet.class)
			.addFilter(ILocation.AFTER, BeanToJsonFilter.create())
			.getAradon() ;
		aradon.getEngine().getRegisteredConverters().add(new PlainObjectConverter()) ;
		aradon.startServer(9050) ;
	
		AradonClient ac = AradonClientFactory.create("http://localhost:9050") ;
		User newUser = new User("bleujin", 20) ;
		User user = ac.createJsonRequest("/view").handle(Method.POST, newUser, User.class) ;
		
		assertEquals(21, user.age) ;
		assertEquals(20, newUser.age) ;
		ac.stop() ;
		aradon.stop() ;
	}
}


class MyLet extends AbstractServerResource {
	
	@Get
	public User viewUser(){
		return new User("bleujin", 20) ;
	}
	
	@Post
	public User addAge(Representation entity) throws JsonSyntaxException, IOException{
		User user = JsonParser.fromString(entity.getText()).getAsJsonObject().getAsObject(User.class);
		user.age = user.age + 1 ;
		return user ;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this) ;
	}
}

class User {
	String name ;
	int age ;
	Address address = new Address("seoul") ;
	
	public User(String name, int age){
		this.name = name ;
		this.age  = age ;
	}
}

class Address {
	private String city ;
	
	public Address(String city){
		this.city = city ;
	}

	public String getCity() {
		return city;
	}
	
} 
package net.ion.radon.representation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.restlet.Response;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import net.ion.framework.parse.gson.annotations.SerializedName;
import net.ion.framework.util.Debug;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.core.representation.PlainObjectConverter;
import net.ion.radon.util.AradonTester;
import junit.framework.TestCase;

public class SampleConverter extends TestCase{

	
	public void testWhenNotRegistered() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/view", MyLet.class).getAradon() ;
//		aradon.getEngine().getRegisteredConverters().add(new PlainObjectConverter()) ;
	
		AradonClient ac = AradonClientFactory.create(aradon) ;
		User user = ac.createSerialRequest("/view").get(User.class) ;
		assertEquals(true, user == null) ;
	}
	
	public void testWhenRegistered() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/view", MyLet.class).getAradon() ;
		aradon.getEngine().getRegisteredConverters().add(new PlainObjectConverter()) ;
		aradon.startServer(9000) ;
	
		AradonClient ac = AradonClientFactory.create("http://localhost:9000") ;
		User user = ac.createSerialRequest("/view").get(User.class) ;
		
		assertEquals("bleujin", user.name) ;
		assertEquals(20, user.age) ;
		assertEquals("seoul", user.address.getCity()) ;
		aradon.stop() ;

		ac = AradonClientFactory.create(aradon) ;
		user = ac.createSerialRequest("/view").get(User.class) ;
		
		assertEquals("bleujin", user.name) ;
		assertEquals(20, user.age) ;
		assertEquals("seoul", user.address.getCity()) ;
		aradon.stop() ;

	}
	
	public void testPost() throws Exception {
		Aradon aradon = AradonTester.create().register("", "/view", MyLet.class).getAradon() ;
		aradon.getEngine().getRegisteredConverters().add(new PlainObjectConverter()) ;
		aradon.startServer(9050) ;
	
		AradonClient ac = AradonClientFactory.create("http://localhost:9050") ;
		User newUser = new User("bleujin", 20) ;
		User user = ac.createSerialRequest("/view").post(newUser, User.class) ;
		
		assertEquals(21, user.age) ;
		assertEquals(20, newUser.age) ;
		aradon.stop() ;
	}
}


class MyLet extends AbstractServerResource {
	
	@Get
	public User viewUser(){
		return new User("bleujin", 20) ;
	}
	
	@Post
	public User addAge(User user){
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
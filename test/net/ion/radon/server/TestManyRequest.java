package net.ion.radon.server;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Future;

import junit.framework.Assert;
import net.ion.framework.util.Debug;
import net.ion.framework.util.ListUtil;
import net.ion.framework.util.RandomUtil;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.client.AsyncHttpHandler;
import net.ion.radon.client.IAradonRequest;
import net.ion.radon.client.IJsonRequest;
import net.ion.radon.client.ISerialRequest;
import net.ion.radon.core.config.ConnectorConfig;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.core.representation.BeanToJsonFilter;
import net.ion.radon.core.representation.PlainObjectConverter;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class TestManyRequest {
	
	
	@Test
	public void testManyRequest() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello/{num}", DummyLet.class) ;
		at.getAradon().startServer(ConnectorConfig.makeJettyHTTPConfig(9005)) ;
		
		AradonClient client = AradonClientFactory.create("http://127.0.0.1:9005");
		for (int i : ListUtil.rangeNum(50000)) {
			IAradonRequest request = client.createRequest("/hello/" + i);
			Response res = request.handle(Method.GET) ;
			if (res.getStatus().getCode() != 200){
				Debug.line(res.getEntityAsText(), res.getStatus()) ;
				break ;
			}
			
		}
		client.stop() ;
		at.getAradon().stop() ;
	} // 168 sec
	
	@Test
	public void testManyAsyncRequest() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello/{num}", DummyLet.class) ;
		at.getAradon().startServer(ConnectorConfig.makeJettyHTTPConfig(9005)) ;
		
		AradonClient client = AradonClientFactory.create("http://127.0.0.1:9005");
		List<Future> store = ListUtil.newList();
		for (int i : ListUtil.rangeNum(50000)) {
			IAradonRequest request = client.createRequest("/hello/" + i);
			Future<Integer> futureStatus = request.asyncHandle(Method.GET, new AsyncHttpHandler<Integer>() {
				public Integer onCompleted(Request request, Response response) {
					return response.getStatus().getCode();
				}

				public void onError(Request request, Response response) {
					Debug.line(response.getEntityAsText()) ;
				}
			}) ;
			store.add(futureStatus) ;
		}
		for (Future f : store) {
			f.get() ;
		}
		client.stop() ;
		at.getAradon().stop() ;
		
	}
	
	@Test
	public void testManySerialAsyncRequest() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello/{num}", SerialLet.class) ;
		at.getAradon().startServer(ConnectorConfig.makeJettyHTTPConfig(9005)) ;
		
		AradonClient client = AradonClientFactory.create("http://127.0.0.1:9005");
		for (int i : ListUtil.rangeNum(50000)) {
			ISerialRequest request = client.createSerialRequest("/hello/" + i);
			Future<User> future = request.asyncHandle(Method.GET, null, User.class) ;
			future.get() ;
		}


		client.stop() ;
		at.getAradon().stop() ;
	}

	
	@Test
	public void testManySerialRequest() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello/{num}", SerialLet.class) ;
		at.getAradon().startServer(ConnectorConfig.makeJettyHTTPConfig(9005)) ;
		
		AradonClient client = AradonClientFactory.create("http://127.0.0.1:9005");
		for (int i : ListUtil.rangeNum(50000)) {
			ISerialRequest request = client.createSerialRequest("/hello/" + i);
			User u = request.handle(Method.GET, null, User.class) ;
			Assert.assertEquals(true, u.eq(i + "")) ;
		}

		client.stop() ;
		at.getAradon().stop() ;
	}
	
	@Test
	public void testManyJsonRequest() throws Exception {
		AradonTester at = AradonTester.create().register("", "/hello/{num}", SerialLet.class) ;
		at.getAradon().getEngine().getRegisteredConverters().add(new PlainObjectConverter()) ;
		at.getAradon().addAfterFilter(BeanToJsonFilter.create()) ;
		at.getAradon().startServer(ConnectorConfig.makeJettyHTTPConfig(9005)) ;
		
		AradonClient client = AradonClientFactory.create("http://127.0.0.1:9005");
		for (int i : ListUtil.rangeNum(50000)) {
			IJsonRequest request = client.createJsonRequest("/hello/" + i);
			NotSerialUser u = request.handle(Method.POST, null, NotSerialUser.class) ;
			Assert.assertEquals(true, u.eq(i + "")) ;
		}
		client.stop() ;
		at.getAradon().stop() ;
	}

}

class DummyLet extends AbstractServerResource {
	
	@Get
	public Representation hello(){
		
		return new StringRepresentation("hello " + getInnerRequest().getAttribute("num") + "\n" + RandomUtil.nextRandomString(1000)) ;
	}
}

class SerialLet extends AbstractServerResource {
	
	@Get
	public User getUser(){
		return new User(getInnerRequest().getAttribute("num")) ;
	}
	
	@Post
	public NotSerialUser returnUser(){
		return new NotSerialUser(getInnerRequest().getAttribute("num")) ;
	}
}

class User implements Serializable {
	
	private static final long serialVersionUID = -1177631699246667199L;
	private String name ;
	public User(String name){
		this.name = name ;
	}
	public boolean eq(String name){
		return this.name.equals(name) ;
	}
}

class NotSerialUser {
	private String name ;
	public NotSerialUser(String name){
		this.name = name ;
	}
	public boolean eq(String name){
		return this.name.equals(name) ;
	}
}

package net.ion.radon.client;

import static org.junit.Assert.assertEquals;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import net.ion.framework.db.Rows;
import net.ion.radon.util.AradonTester;

import org.junit.Ignore;
import org.junit.Test;

public class TestSerialRequest {

	@Test
	public void basicGet() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class) ;
		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test?name=bleujin") ;
		
		MyUser muser = request.get(MyUser.class) ;
		assertEquals("bleujin", muser.getName()) ;
	}

	@Test
	public void opps() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class);
		at.startServer(9000);

		IAradonRequest pr = AradonClientFactory.create("http://127.0.0.1:9000").createRequest("/test");
		pr.addParameter("name", "hero");

		ObjectInputStream ois = new ObjectInputStream(pr.get().getStream());
		MyUser m = (MyUser) ois.readObject();
		assertEquals("hero", m.getName());
		at.getAradon().stop() ;
	}
	
	@Ignore
	@Test
	public void xtestPostSelect() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class) ;
		
		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test") ;
		Rows rows = request.post(new String[]{"select 1 from dual", "select 2 from dual"}, Rows.class) ;
		
		assertEquals(1, rows.firstRow().getInt(1)) ;
		assertEquals(2, rows.getNextRows().firstRow().getInt(1)) ;
		at.getAradon().stop() ;
	}

	@Test
	public void basicPut() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class) ;
		
		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test?name=bleujin") ;
		List<String> names = request.put("bleujin,hero", ArrayList.class) ;
		
		assertEquals(3, names.size()) ;
		assertEquals("bleujin", names.get(0)) ;
		at.getAradon().stop() ;
	}
	
	@Test
	public void aradonGet() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class) ;

		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test?name=bleujin") ;
		MyUser muser = request.get(MyUser.class) ;
		assertEquals("bleujin", muser.getName()) ;
	}
	@Test
	public void aradonPut() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class) ;

		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test") ;
		List<String> names = request.put("bleujin,hero", ArrayList.class) ;
		
		assertEquals(2, names.size()) ;
		assertEquals("bleujin", names.get(0)) ;
	}

	
	public void aradonPost() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", ParameterTestLet.class) ;
		
		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test") ;
		Rows rows = request.post(new String[]{"select 1 from dual", "select 2 from dual"}, Rows.class) ;
		
		assertEquals(1, rows.firstRow().getInt(1)) ;
		assertEquals(2, rows.getNextRows().firstRow().getInt(1)) ;
	}

	@Test
	public void basicHeader() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test2", HeaderTestLet.class) ;
		
		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test2") ;
		request.addHeader("name", "bleujin") ;
		
		
		Integer ivalue = request.put(null, Integer.class) ;
		assertEquals("bleujin".length(), ivalue.intValue()) ;
		at.getAradon().stop() ;
	}

	@Test
	public void aradonHeader() throws Exception {
		AradonTester at = AradonTester.create().register("", "/test", HeaderTestLet.class) ;
		ISerialRequest request = AradonClientFactory.create(at.getAradon()).createSerialRequest("/test") ;
		request.addHeader("name", "bleujin") ;
		
		
		Integer ivalue = request.put(null, Integer.class) ;
		assertEquals("bleujin".length(), ivalue.intValue()) ;
	}
	
	
}

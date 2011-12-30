package net.ion.nradon.handler.aradon;

import static org.junit.Assert.*;

import java.util.Date;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.nradon.handler.aradon.JSONMessagePacket;

import org.json.JSONObject;
import org.junit.Test;


public class TestJSONMessagePacket  {

	@Test
	public void init() throws Exception {
		JSONMessagePacket mp = JSONMessagePacket.create().inner("head").put("requestTime", new Date().getTime()).put("requestId", "bleujin") ;
	}
	
	@Test
	public void parent() throws Exception {
		JSONMessagePacket mp = makeExample() ;
		assertEquals(42222, mp.get("body/room/roomid")) ;
	}

	private JSONMessagePacket makeExample() {
		return JSONMessagePacket.create()
			.inner("head").put("requestTime", new Date().getTime()).put("requestId", "bleujin").toParent()
			.inner("body").put("greeting", "hello") 
				.inner("room").put("roomid", 42222).toRoot();
	}
	
	@Test
	public void load() throws Exception {
		JSONMessagePacket source = makeExample();
		
		String msg = source.getFullString() ;
		
		JSONMessagePacket mp = JSONMessagePacket.load(msg) ;
		assertEquals(source.get("head/requestTime"), mp.get("Head/RequestTime")) ;
	}

	@Test
	public void empty() throws Exception {
		JSONMessagePacket source = makeExample();
		
		assertEquals("", source.getString("not/exist")) ;
		assertEquals("not", source.getString("not/exist", "not")) ;
	}
	
	@Test
	public void has() throws Exception {
		JSONMessagePacket msg = makeExample() ;
		assertEquals(true, msg.has("head.requesttime")) ;
		assertEquals(true, msg.has("head.requestTime")) ;
		assertEquals(false, msg.has("content.requesttime")) ;
	}
	
	@Test
	public void getDefaultString() throws Exception {
		JSONMessagePacket msg = makeExample() ;
		assertEquals("bleujin", msg.getString("head.requestid", "abcd")) ;
		assertEquals("bleujin", msg.getString("head.requestID", "abcd")) ;
		assertEquals("abcd", msg.getString("head.sender", "abcd")) ;
	}
	
	@Test
	public void inner() throws Exception {
		JSONMessagePacket mp = JSONMessagePacket.create().inner("name").put("first", "bleu").put("last", "jin").toRoot() ;
		
		assertEquals("bleu", mp.getString("name.first")) ;
		assertEquals("jin", mp.getString("name.last")) ;
	}

	@Test
	public void arrray() throws Exception {
		JSONMessagePacket msg = JSONMessagePacket.create().inner("peoples") ;
		for (int i = 0; i < 3; i++) {
			JSONObject jso = new JSONObject("{result:'success', color:[1,2,3]}") ;
			jso.put("index", i) ;
			msg.toRoot().inner("peoples").append("people", jso) ;
		}
		// msg.child("peoples.people") ;
		
		Debug.debug(msg.toRoot().child("peoples.people")) ;
	}
	
	@Test
	public void array2() throws Exception {
		JSONMessagePacket msg = JSONMessagePacket.create().array("color", new String[]{"red", "blue"}) ;
		assertEquals("{\"color\":[\"red\",\"blue\"]}", msg.getFullString()) ;
	}
	
	@Test
	public void toMap() throws Exception {
		JSONMessagePacket msg = JSONMessagePacket.create().inner("params").put("name", "bleujin").put("age", 20).toParent() ;
		
		assertEquals("bleujin", msg.inner("params").toMap().get("name")) ;
		assertEquals(20, msg.inner("params").toMap().get("age")) ;
	}

	
	@Test
	public void arrayLoad() throws Exception {
		String str = "{path:'/test', params:{name:'bleujin', age:20}, method:'GET'}" ;
		JSONMessagePacket msg = JSONMessagePacket.load(str) ;
		
		msg.inner("params") ;
		
	}
	
}

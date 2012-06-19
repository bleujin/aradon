package net.ion.radon.param;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestParamterKey {

	private String path = "a.b.c" ;
	
	@Test
	public void testKeyName() throws Exception {
		MyParameterKey key = MyParameterKey.create(path) ;
		assertEquals("a", key.getName()) ;
	}

	@Test
	public void testNextKeyName() throws Exception {
		MyParameterKey key = MyParameterKey.create(path) ;
		assertEquals("b", key.getNext().getName()) ;
	}
	
	@Test
	public void testIsLast() throws Exception {
		MyParameterKey key = MyParameterKey.create(path) ;
		String buffer = new String() ;
		while(true){
			buffer = buffer + (key.isFirst() ? "" : ".") + key.getName() ;
			if (key.isLast()) break ;
			key = key.getNext() ;
		} 
		
		
		assertEquals(path, buffer) ;
	}

	
	
	
}

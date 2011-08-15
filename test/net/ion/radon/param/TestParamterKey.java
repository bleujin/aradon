package net.ion.radon.param;

import junit.framework.TestCase;

public class TestParamterKey extends TestCase{

	String path = "a.b.c" ;
	public void testKeyName() throws Exception {
		MyParameterKey key = MyParameterKey.create(path) ;
		assertEquals("a", key.getName()) ;
	}

	public void testNextKeyName() throws Exception {
		MyParameterKey key = MyParameterKey.create(path) ;
		assertEquals("b", key.getNext().getName()) ;
	}
	
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

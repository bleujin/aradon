package net.ion.framework.util;

import junit.framework.TestCase;

public class TestCircularList extends TestCase{

	public void testInit() throws Exception {
		CircularList<String> list = CircularList.create("e", 5) ;
		for (int i = 0; i < 20 ; i++) {
			Debug.debug(list.getNext()) ;
		}
	}
}

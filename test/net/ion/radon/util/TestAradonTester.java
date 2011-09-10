package net.ion.radon.util;

import junit.framework.TestCase;
import net.ion.radon.impl.let.HelloWorldLet;

public class TestAradonTester extends TestCase{

	public void testRegister() throws Exception {
		AradonTester at = AradonTester.create().register("s1", "/shutdown/{name}", HelloWorldLet.class) ;
		
		
		assertEquals(1, at.getAradon().getChildren().size()) ; 
		assertEquals(1, at.getAradon().getChildService("s1").getChildren().size()) ;
	}

}


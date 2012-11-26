package net.ion.bleujin.seminar.object;

import java.io.Serializable;

public class TestSerial {

	
	public void testSerial() throws Exception {
		
	}
	
}


class Person implements Serializable {
	
	private static final long serialVersionUID = -4711334344897932450L;
	private String name ;
	private int age ;
	
	public Person(String name, int age){
		this.age = age ;
		this.name = name ;
	}
	
}
package net.ion.radon.client;

import java.io.Serializable;

public class MyUser implements Serializable {

	private String name;
	private int age ;

	public MyUser(String name) {
		this.name = name;
		this.age = 20 ;
	}
	
	public MyUser oneYearLater(){
		this.age ++ ;
		return this ;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "name:" + name;
	}

	public int getAge() {
		return age;
	}

}

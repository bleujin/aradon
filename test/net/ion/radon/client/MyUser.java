package net.ion.radon.client;

import java.io.Serializable;

public class MyUser implements Serializable {

	private String name;

	MyUser(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "name:" + name;
	}

}

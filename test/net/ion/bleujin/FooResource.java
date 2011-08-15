package net.ion.bleujin;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class FooResource extends ServerResource {
	@Get
	public String toString() {
		return "hello, world";
	}
}
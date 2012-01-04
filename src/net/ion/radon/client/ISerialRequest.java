package net.ion.radon.client;

import org.restlet.resource.ResourceException;

public interface ISerialRequest {

	public <V> V get(Class<? extends V> clz) throws ResourceException;
	public <T, V> V put(T arg, Class<? extends V> clz);
	public <T, V> V post(T arg, Class<? extends V> clz)  ;
	public <V> V delete(Class<? extends V> clz)  ;
	public ISerialRequest addHeader(String string, String string2);

}

package net.ion.radon.client;

import java.util.concurrent.Future;

import org.restlet.data.Method;
import org.restlet.resource.ResourceException;

public interface ISerialRequest {

	public <V> V get(Class<? extends V> clz) throws ResourceException;
	public <T, V> V put(T arg, Class<? extends V> clz);
	public <T, V> V post(T arg, Class<? extends V> clz)  ;
	public <V> V delete(Class<? extends V> clz)  ;
	public ISerialRequest addHeader(String string, String string2);

	public <T, V> V handle(Method method, T arg, Class<? extends V> resultClass) ;
	public <T, V> Future<V> asyncHandle(Method method, T arg, Class<? extends V> resultClass) ;
}

package net.ion.radon.client;

import java.util.List;
import java.util.concurrent.Future;

import org.restlet.data.Method;
import org.restlet.resource.ResourceException;

public interface IJsonRequest {
	
	public <V> V get(Class<V> clz) throws ResourceException;
	public <V> List<V> list(Class<V> clz) throws ResourceException;
	public <T, V> V put(T arg, Class<V> clz);
	public <T, V> V post(T arg, Class<V> clz)  ;
	public <V> V delete(Class<V> clz)  ;
	public IJsonRequest addHeader(String string, String string2);

	public <T, V> V handle(Method method, T arg, Class<V> resultClass) ;
	public <T, V> Future<V> asyncHandle(Method method, T arg, Class<V> resultClass) ;
}

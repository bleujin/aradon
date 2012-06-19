package net.ion.radon.client;

import java.io.Serializable;

import org.restlet.data.Method;
import org.restlet.representation.ObjectRepresentation;

public class SubRequest<T extends Serializable> extends ObjectRepresentation implements Serializable{

	public enum SMethod {
		LIST, INFO, SEARCH
	}
	
	private SMethod sm ;
	private T obj ;
	
	private SubRequest(SMethod sm, T obj) {
		super(obj) ;
		this.obj = obj ;
		this.sm = sm ;
	}

	public static <T extends Serializable> SubRequest<T> list(T obj) {
		return new SubRequest<T>(SMethod.LIST, obj);
	}

	public static <T extends Serializable> SubRequest<T> info(T obj) {
		return new SubRequest<T>(SMethod.INFO, obj);
	}

	public static <T extends Serializable> SubRequest<T> search(T obj) {
		return new SubRequest<T>(SMethod.SEARCH, obj);
	}

	public T getResult() {
		return obj;
	}

	public void setMethod(Method search) {
		// TODO Auto-generated method stub
		
	}

	
	
}

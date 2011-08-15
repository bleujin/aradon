package net.ion.radon.core.security;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.security.MapVerifier;
import org.restlet.security.Verifier;

public class SimpleVerifier implements Verifier{

	private Verifier inner ;
	
	public SimpleVerifier(){
		this("bleujin", "redf"); 
	}
	
	public SimpleVerifier(String userid, String password){
		ConcurrentMap<String, char[]> users = new ConcurrentHashMap<String, char[]>();
		users.put(userid, password.toCharArray());
		this.inner = new MapVerifier(users);
	}
	
	public int verify(Request request, Response response) {
		return inner.verify(request, response);
	}

}

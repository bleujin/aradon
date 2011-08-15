package net.ion.bleujin.asyncrestlet;

import java.io.InputStream;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;

import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

public class TestAsync extends TestCase{
	
	public void testNormal() throws Exception {
		Request request = new Request(Method.GET, "http://localhost:9002/async/hello");
		
		Client client = new Client(Protocol.HTTP);
		Response res = client.handle(request) ;
		
		InputStream input = res.getEntity().getStream() ;
		byte[] buffer = new byte[100] ;
		int n = 0 ;
		int count = 0 ;
		while(( n = input.read(buffer)) != -1) {
			Debug.debug(new String(buffer), count) ;
			count += n ;
		}
		input.close() ;
	}
	
	public void testAsyncInputStream() throws Exception {
		MyThread t = new MyThread() ;
		t.start() ;
		AsyncInputStream input = new AsyncInputStream(t.getInputStream(), 100) ;
		// BufferedReader new BufferedReader(new BufferedInputStream(input), "UTF-8"); 
		byte[] buffer = new byte[50] ;
		int n = 0 ;
		while(( n = input.read(buffer)) != -1) {
			Debug.debug(new String(buffer)) ;
		}
		input.close() ;
		
	}
	
	
	public void testPipe() throws Exception {
		
		MyThread t = new MyThread() ;
		t.start() ;
		
	}
}

package net.ion.nradon.rest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import junit.framework.TestCase;
import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;


@Path("/hello")
public class TestRestFirst extends TestCase {

	@GET
	@Path("{name}")
	@Produces("text/plain")
	public String hello(@PathParam("name") String name){
		return "hello " +  name;
	}
	
	
	public void testHello() throws Exception {
		Radon radon = RadonConfiguration.newBuilder(9000).add(new Rest311Handler(getClass())).start().get() ;
		
		Socket client = new Socket(InetAddress.getLocalHost(), 9000) ;
		OutputStream output = client.getOutputStream() ;
		output.write(("GET /hello/bleujin HTTP/1.0\r\n" + "host: www.radon.com\r\n\r\n").getBytes("UTF-8"));
		output.flush(); 
		
		InputStream input = client.getInputStream() ;
		String result = IOUtil.toStringWithClose(input) ;
		
		Debug.line(result);
		client.close(); 
		radon.stop().get() ;
	}
	
}

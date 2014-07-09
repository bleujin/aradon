package net.ion.nradon.rest;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import net.ion.framework.util.Debug;
import net.ion.nradon.stub.StubHttpResponse;

import org.jboss.resteasy.spi.HttpResponse;
import org.junit.Test;

@Path("/output")
public class TestOutputLet extends TestBaseRest{

	@GET
	public String delay(@Context HttpResponse response) throws UnsupportedEncodingException, IOException{

		OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8") ;
		writer.write("hello");
		writer.flush(); 
//		writer.close(); 
		
//		final OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8") ;
//
//		writer.write("Hi...");
//		writer.flush(); 
//
//		new Thread(){
//			public void run(){
//				try {
//					int i = 0 ;
//					while(i++ < 10) {
//						Debug.line("hello " + i + "\n" + writer);
//						writer.write("hello " + i + "\n");
//						writer.flush();
//						Thread.sleep(1000);
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}.start(); 
		
		return "";
	}
	
	@Test
	public void outputLet() throws Exception{
    	handler = new Rest311Handler(new TestOutputLet()); 
    	StubHttpResponse response = handle(request("/output").method("GET")) ;
    	
    	Debug.line(response.contentsString()) ;
	}
	
	
}

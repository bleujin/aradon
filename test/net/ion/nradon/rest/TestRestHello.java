package net.ion.nradon.rest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import net.ion.nradon.stub.StubHttpResponse;

import org.junit.Test;

@Path("/hello")
public class TestRestHello extends TestBaseRest{

	@GET
	public String hello(){
		return "Hello" ;
	}

    @Test
    public void helloGet() throws Exception {
    	handler = new Rest311Handler(new TestRestHello()); 

    	StubHttpResponse response = handle(request("/hello").method("GET")) ;
    	assertEquals("Hello", response.contentsString());
    }
    
}




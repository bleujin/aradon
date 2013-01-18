package net.ion.nradon.ajax;

import java.io.File;
import java.nio.charset.Charset;

import net.ion.framework.util.Debug;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.nradon.Radon;
import net.ion.nradon.config.RadonConfiguration;
import net.ion.nradon.handler.AbstractHttpHandler;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.AbstractServerResource;
import net.ion.radon.util.AradonTester;

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.representation.FileRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;

public class TestStaticFileHandler {

	@Test
	public void continiusView() throws Exception {
		Radon radon = RadonConfiguration.newBuilder(9000)
			.add(new ContiniusHttpHandler(MediaType.TEXT_ALL.toString())).startRadon() ;

		System.out.println("Server running at " + radon.getConfig().publicUri());

		AradonClient ac = AradonClientFactory.create("http://localhost:9000") ;
		Response res = ac.createRequest("/").handle(Method.GET) ;
		Debug.line(res.getEntityAsText(), res.getAttributes()) ;
		ac.stop() ;
		
//		nc.close() ;
		
		radon.stop() ;
	}
	
	@Test
	public void aradonRepresentation() throws Exception {
		
		final Aradon aradon = AradonTester.create().register("aradon", "/", LongFileLet.class).getAradon();
		Radon radon = aradon.toRadon(9000);
		radon.getConfig().add(new ContiniusHttpHandler(MediaType.TEXT_ALL.toString())) ;
		radon.start().get() ;
		
		AradonClient ac = AradonClientFactory.create("http://localhost:9000") ;
		Response res = ac.createRequest("/aradon/").handle(Method.GET) ;
		Debug.line(res.getEntityAsText()) ;
		
		ac.stop() ;
		radon.stop() ;

	}
	
}

class LongFileLet extends AbstractServerResource {
	
	@Get
	public Representation viewFile(){
		return new FileRepresentation(new File("resource/imsi/aradon.common.server.js"), MediaType.TEXT_ALL) ;
	}
	
}



class ContiniusHttpHandler extends AbstractHttpHandler {

	private final String contentType;
	private final Charset charset;

	public ContiniusHttpHandler(String contentType) {
		this(contentType, Charset.forName("UTF-8"));
	}

	public ContiniusHttpHandler(String contentType, Charset charset) {
		this.contentType = contentType;
		this.charset = charset;
	}

	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) {
		String body = "hello server" ;
		response.charset(charset).header("Content-Type", contentType + "; charset=" + charset.name()).content(body).content(body).end(); //header("Content-Length", -1).
	}

}
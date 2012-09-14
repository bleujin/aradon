package net.ion.radon.server;

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

import org.junit.Test;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.data.Method;

public class TestContinusResponse {

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
		response.charset(charset).header("Content-Type", contentType + "; charset=" + charset.name()).header("Content-Length", -1).content(body).content(body).end(); //
	}

}
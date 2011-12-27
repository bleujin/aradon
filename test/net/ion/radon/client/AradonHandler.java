package net.ion.radon.client;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpCookie;
import java.nio.ByteBuffer;
import java.util.Map.Entry;

import net.ion.framework.util.Debug;
import net.ion.framework.util.IOUtil;
import net.ion.framework.util.MapUtil;
import net.ion.nradon.HttpControl;
import net.ion.nradon.HttpHandler;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.radon.client.AradonClient;
import net.ion.radon.client.AradonClientFactory;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.RadonAttributeKey;
import net.ion.radon.core.let.InnerRequest;
import net.ion.radon.core.let.InnerResponse;

import org.apache.commons.io.IOUtils;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.CharacterSet;
import org.restlet.data.Cookie;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.util.Series;


public class AradonHandler implements HttpHandler {

	private AradonClient ac ;
	private Aradon aradon ;
	public AradonHandler(Aradon aradon) {
		this.aradon = aradon ;
		this.ac = AradonClientFactory.create(aradon) ;
	}

	@Override
	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		Request req = toRestRequest(request) ;
		Response res = aradon.handle(req) ;
		toHttpResponse((InnerResponse)Response.getCurrent(), response).end() ;
	}

	private HttpResponse toHttpResponse(InnerResponse res, HttpResponse response) throws IOException {
		
		Form headers = res.getHeaders() ;
		for (String name : headers.getNames()) {
			response.header(name, headers.getValues(name)) ;
		}
		response.status(res.getStatus().getCode()) ;
		CharacterSet characterSet = res.getContentType().getCharacterSet();
		if (characterSet != null) response.charset(characterSet.toCharset()) ;
		
		Representation representation = res.getEntity() ;
		ByteBuffer bytebuffer = ByteBuffer.wrap(IOUtil.toByteArray(representation.getStream())) ;
		response.content(bytebuffer) ;
		return response ;
	}

	private Request toRestRequest(HttpRequest hreq) {
		InputRepresentation re = new InputRepresentation(new ByteArrayInputStream(hreq.bodyAsBytes())) ;
		Request request = new Request(Method.valueOf(hreq.method()), "riap://component" + hreq.uri()) ;
		InnerRequest ireq = InnerRequest.create(request) ;

		Series<Cookie> cookies = ireq.getCookies()  ;
		for (HttpCookie hc : hreq.cookies()){
			cookies.add(new Cookie(hc.getVersion(), hc.getName(), hc.getValue(), hc.getPath(), hc.getDomain())) ;
		}
		
		Form headers = ireq.getHeaders() ;
		for( Entry<String, String> entry : hreq.allHeaders()){
			headers.add(entry.getKey(), entry.getValue()) ;
		}

		return request;
	}

}

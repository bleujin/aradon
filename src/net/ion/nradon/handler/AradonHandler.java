package net.ion.nradon.handler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpCookie;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.db.AradonConnection;
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

import org.eclipse.jetty.http.HttpHeaders;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.CharacterSet;
import org.restlet.data.ClientInfo;
import org.restlet.data.Cookie;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.engine.http.header.ExpectationReader;
import org.restlet.engine.http.header.HeaderConstants;
import org.restlet.engine.http.header.PreferenceReader;
import org.restlet.representation.InputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.util.Series;

public class AradonHandler implements HttpHandler {

	private AradonClient ac;
	private Aradon aradon;

	public AradonHandler(Aradon aradon) {
		this.aradon = aradon;
		this.ac = AradonClientFactory.create(aradon);
	}

	@Override
	public void handleHttpRequest(HttpRequest request, HttpResponse response, HttpControl control) throws Exception {
		Request req = toAradonRequest(request);
		Response res = aradon.handle(req);
		toHttpResponse(res, response).end();
	}

	private HttpResponse toHttpResponse(Response res, HttpResponse response) throws IOException {

		Form headers = (Form) res.getAttributes().get(RadonAttributeKey.ATTRIBUTE_HEADERS) ;
		if (headers == null) headers = new Form() ;
		for (String name : headers.getNames()) {
			response.header(name, headers.getValues(name));
		}
		response.status(res.getStatus().getCode());
		Representation entity = res.getEntity();
		if (entity != null && entity.getCharacterSet() != null)
			response.charset(entity.getCharacterSet().toCharset());

		Representation representation = entity;
		if (representation == null) {
			return response;
		}
		ByteBuffer bytebuffer = ByteBuffer.wrap(IOUtil.toByteArray(representation.getStream()));
		response.content(bytebuffer);
		return response;
	}

	private Request toAradonRequest(HttpRequest hreq) {
		Request request = new Request(Method.valueOf(hreq.method()), "http://" + hreq.header(HttpHeaders.HOST) + hreq.uri());
		
		request.setDate(GregorianCalendar.getInstance().getTime()) ;
		InnerRequest ireq = InnerRequest.create(request);
		
		ireq.setClientInfo(getClientInfo(hreq));

		InputRepresentation re = new InputRepresentation(new ByteArrayInputStream(hreq.bodyAsBytes()));
		ireq.setEntity(re);

		Series<Cookie> cookies = ireq.getCookies();
		for (HttpCookie hc : hreq.cookies()) {
			cookies.add(new Cookie(hc.getVersion(), hc.getName(), hc.getValue(), hc.getPath(), hc.getDomain()));
		}

		Form headers = ireq.getHeaders();
		for (Entry<String, String> entry : hreq.allHeaders()) {
			headers.add(entry.getKey(), entry.getValue());
		}

		return ireq;
	}

	private ClientInfo getClientInfo(HttpRequest hreq) {
		final ClientInfo result = new ClientInfo();

		Headers headers = Headers.create(hreq.allHeaders()) ;
		
		// Extract the header values
		String acceptMediaType = headers.getValues(HeaderConstants.HEADER_ACCEPT);
		String acceptCharset = headers.getValues(HeaderConstants.HEADER_ACCEPT_CHARSET);
		String acceptEncoding = headers.getValues(HeaderConstants.HEADER_ACCEPT_ENCODING);
		String acceptLanguage = headers.getValues(HeaderConstants.HEADER_ACCEPT_LANGUAGE);
		String expect = headers.getValues(HeaderConstants.HEADER_EXPECT);

		// Parse the headers and update the call preferences
		// Parse the Accept* headers. If an error occurs during the parsing of each header, the error is traced and we keep on with the other headers.
		try {
			PreferenceReader.addCharacterSets(acceptCharset, result);
		} catch (Exception e) {
		}

		try {
			PreferenceReader.addEncodings(acceptEncoding, result);
		} catch (Exception e) {
		}

		try {
			PreferenceReader.addLanguages(acceptLanguage, result);
		} catch (Exception e) {
		}

		try {
			PreferenceReader.addMediaTypes(acceptMediaType, result);
		} catch (Exception e) {
		}

		try {
			ExpectationReader.addValues(expect, result);
		} catch (Exception e) {
		}

		// Set other properties
		result.setAgent(headers.getValues(HeaderConstants.HEADER_USER_AGENT));
		result.setFrom(headers.getValues(HeaderConstants.HEADER_FROM));
		
		SocketAddress _caddress =  hreq.remoteAddress() ;
		if (_caddress instanceof InetSocketAddress){
			InetSocketAddress caddress = (InetSocketAddress) _caddress ;
			result.setAddress( caddress.getAddress().getHostAddress());
			result.setPort(caddress.getPort());
		}
		
//
//		if (getHttpCall().getUserPrincipal() != null) {
//			result.getPrincipals().add(getHttpCall().getUserPrincipal());
//		}

		return result;
	}

}

class Headers {

	private Map<String, String> inners ;
	private Headers(List<Entry<String, String>> allHeaders) {
		this.inners = MapUtil.newMap() ;
		for (Entry<String, String> entry : allHeaders) {
			inners.put(entry.getKey(), entry.getValue()) ;
		}
	}

	public String getValues(String headerKey) {
		return inners.get(headerKey);
	}

	public static Headers create(List<Entry<String, String>> allHeaders) {
		return new Headers(allHeaders);
	}
	
	
}

package net.ion.nradon.handler.aradon;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.MapUtil;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.radon.core.Aradon;
import net.ion.radon.core.let.InnerRequest;

import org.eclipse.jetty.http.HttpHeaders;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.ClientInfo;
import org.restlet.data.Cookie;
import org.restlet.data.Method;
import org.restlet.engine.header.ExpectationReader;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.engine.header.PreferenceReader;
import org.restlet.representation.InputRepresentation;
import org.restlet.security.User;
import org.restlet.util.Series;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class NradonClient {

	private Aradon aradon ;
	private NradonClient(Aradon aradon) {
		this.aradon = aradon ;
	}

	public final static NradonClient create(Aradon aradon){
		return new NradonClient(aradon) ;
	}

	public void handle(HttpRequest request, HttpResponse response) throws IOException {
		Request req = AradonUtil.toAradonRequest(request);
		
		Response res = aradon.handle(req) ;
		AradonUtil.copyResponse(res, response).end();
	}

	public Response handle(Request request) {
		return aradon.handle(request);
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

class AradonUtil {

	final static Request toAradonRequest(HttpRequest hreq) {
		Request request = new Request(Method.valueOf(hreq.method()), "http://" + hreq.header(HttpHeaders.HOST) + hreq.uri());

		request.setDate(GregorianCalendar.getInstance().getTime());
		InnerRequest ireq = InnerRequest.create(request);

		ireq.setClientInfo(getClientInfo(hreq));

		InputRepresentation re = new InputRepresentation(new ByteArrayInputStream(hreq.bodyAsBytes()));
		ireq.setEntity(re);

		Series<Cookie> cookies = ireq.getCookies();
		for (Cookie hc : hreq.cookies()) {
			cookies.add(hc);
		}

		Series headers = ireq.getHeaders();
		for (Entry<String, String> entry : hreq.allHeaders()) {
			headers.add(entry.getKey(), entry.getValue());
		}

		setChallenge(request, headers.getFirstValue("Authorization"));
		
		return ireq;
	}

	private static void setChallenge(Request request, String authValue) {
		if (StringUtil.isNotBlank(authValue)){
			String txtSchema = StringUtil.substringBefore(authValue, " ") ;
			ChallengeScheme schema = ChallengeScheme.valueOf(txtSchema) ;
			String txtCipher = StringUtil.substringAfter(authValue, " ") ;
			String txtPlain = new String(Base64.decode(txtCipher)) ;
			User user = new User(StringUtil.substringBefore(txtPlain, ":"), StringUtil.substringAfter(txtPlain, ":")) ;
			request.setChallengeResponse(new ChallengeResponse(schema, user.getIdentifier(), user.getSecret())) ;
		}
	}

	private static ClientInfo getClientInfo(HttpRequest hreq) {
		final ClientInfo result = new ClientInfo();

		Headers headers = Headers.create(hreq.allHeaders());

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

		SocketAddress _caddress = hreq.remoteAddress();
		if (_caddress instanceof InetSocketAddress) {
			InetSocketAddress caddress = (InetSocketAddress) _caddress;
			result.setAddress(caddress.getAddress().getHostAddress());
			result.setPort(caddress.getPort());
		}

		//
		// if (getHttpCall().getUserPrincipal() != null) {
		// result.getPrincipals().add(getHttpCall().getUserPrincipal());
		// }

		return result;
	}

	static HttpResponse copyResponse(Response res, final HttpResponse response) throws IOException {

		if (res == null || res.getEntity() == null) {
			return response;
		}
		response.write(res);

		return response;
	}

}




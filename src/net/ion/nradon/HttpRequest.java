package net.ion.nradon;

import java.io.IOException;
import java.net.SocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.restlet.data.Cookie;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;

public interface HttpRequest extends DataHolder {

	String COOKIE_HEADER = "Cookie";

	String uri();

	HttpRequest uri(String uri);

	String header(String name);

	List<String> headers(String name);

	boolean hasHeader(String name);

	List<Cookie> cookies();

	Cookie cookie(String name);

	String queryParam(String key);

	List<String> queryParams(String key);

	Set<String> queryParamKeys();

	String postParam(String key);

	List<String> postParams(String key);

	Set<String> postParamKeys();

	String cookieValue(String name);

	List<Map.Entry<String, String>> allHeaders();

	String method();

	String body();

	byte[] bodyAsBytes();
	
	Representation bodyAsRepresentation(MediaType mtype) throws IOException ;

	HttpRequest data(String key, Object value); // Override DataHolder to provide more specific return type.

	SocketAddress remoteAddress();

	Object id();

	long timestamp();

}

package net.ion.nradon.handler.aradon;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.restlet.Request;
import org.restlet.Response;

import net.ion.framework.util.MapUtil;
import net.ion.nradon.HttpRequest;
import net.ion.nradon.HttpResponse;
import net.ion.radon.core.Aradon;

public class NradonClient {

	private Aradon aradon ;
	private NradonClient(Aradon aradon) {
		this.aradon = aradon ;
	}

	public final static NradonClient create(Aradon aradon){
		return new NradonClient(aradon) ;
	}

	public HttpResponse handle(HttpRequest request, HttpResponse response) throws IOException {
		Request req = AradonUtil.toAradonRequest(request);
		
		Response res = aradon.handle(req) ;
		return AradonUtil.toHttpResponse(res, response);
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




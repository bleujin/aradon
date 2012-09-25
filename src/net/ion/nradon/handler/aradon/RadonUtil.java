package net.ion.nradon.handler.aradon;

import net.ion.nradon.HttpRequest;

import org.restlet.Request;

public class RadonUtil {

	public final static Request toRequest(HttpRequest hreq){
		return AradonUtil.toAradonRequest(hreq) ;
	}
}

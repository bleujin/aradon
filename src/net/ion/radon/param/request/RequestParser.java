package net.ion.radon.param.request;

import net.ion.radon.param.MyParameter;
import net.sf.json.JSONObject;

public class RequestParser {

	private MyParameter param ;
	private RequestParser(MyParameter param) {
		this.param = param ;
	}

	public RequestParser(String pname, Object value) {
		// TODO Auto-generated constructor stub
	}

	public static RequestParser create(MyParameter param) {
		return new RequestParser(param) ;
	}

	public final static ARadonRequest parse(String param){
		return parse(MyParameter.create(param)) ;
	}
	
	
	public final static ARadonRequest parse(MyParameter param) {
		Object[] reqs = param.getParams("aradon") ;
		ARadonRequest arequest = ARadonRequest.create() ; 

		for (int i=0 ; i < reqs.length ; i++) {
			arequest.append(SingleRequest.create((JSONObject)reqs[i])) ;
		}
		return arequest ;
	}

}

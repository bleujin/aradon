package net.ion.radon.param.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.parse.gson.JsonElement;
import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.util.StringUtil;
import net.ion.radon.core.SectionService;
import net.ion.radon.core.let.DefaultLet;
import net.ion.radon.core.let.InboundLet;
import net.ion.radon.core.let.LetResponse;
import net.ion.radon.param.MyParameter;

import org.restlet.data.Form;
import org.restlet.data.Method;


public class SingleRequest {
	private MyParameter myparam ; 
	private SingleRequest(MyParameter param) {
		this.myparam = param ;
	}

	static SingleRequest create(Map json) {
		return new SingleRequest(MyParameter.create(json));
	}
	
	public String getName(){
		return getValueAsString("name") ;
	}
	
	String getSectionName(){
		return getValueAsString("section") ;
	}
	
	String getPath(){
		return getValueAsString("path") ;
	}

	List<RequestParam> getParams() {
		List<RequestParam> result = new ArrayList<RequestParam>() ;
		Map<String, ? extends Object> params = myparam.getMap("param") ;
		for (Entry<String, ? extends Object> entry: params.entrySet()) {
			result.add(RequestParam.create(entry.getKey(), entry.getValue())) ;
		}
		return result ;
	}
	
	RequestParam getParam(String pname){
		return RequestParam.create(pname, myparam.getParam("param." + pname)) ;
	}
	
	String getValueAsString(String path){
		return StringUtil.toString(myparam.getParam(path)) ;
	}

	String getValueAsString(String path, String defaultValue){
		return StringUtil.toString(myparam.getParam(path), defaultValue) ;
	}


	public Object getValue(String path) {
		return myparam.getParam(path);
	}

	Method getMethod() {
		return Method.valueOf(getValueAsString("param." + DefaultLet.ARADON_HTTP_METHOD)) ;
	}

	public LetResponse handle(AradonRequest aRadonRequest, DefaultLet radonLet) throws IOException {
		InboundLet let = InboundLet.create(radonLet.getInnerRequest(), (SectionService)radonLet.getApplication(), getPath()) ;
		
		Map<String, Object> params = myparam.getMap("param") ;
		params.put(DefaultLet.ARADON_RESULT_FORMAT, params.get(DefaultLet.ARADON_RESULT_FORMAT) ) ;
		params.put(DefaultLet.ARADON_HTTP_METHOD,   params.get(DefaultLet.ARADON_HTTP_METHOD) ) ;
		
		Form cparams = new Form();
		for (Entry<String, Object> entry: params.entrySet()) {
			String paramValue = StringUtil.toString(entry.getValue());
			if (StringUtil.isBetween(paramValue, "${", "}")) {
				String expression = StringUtil.substringBetween(paramValue, "${", "}") ;
				String name = StringUtil.substringBefore(expression, ".") ;
				String path = StringUtil.substringAfter(expression, ".") ;
				LetResponse res = aRadonRequest.getRequestResult(name, radonLet) ;
				
				paramValue = StringUtil.toString(res.getObject(path)) ; 
			}
			
			RequestParam.create(entry.getKey(), paramValue) ;
			cparams.add(entry.getKey(), paramValue) ;

		}
		
		return let.handle(getMethod(), cparams) ;
	}

}

package net.ion.radon.param.request;

import net.ion.framework.util.StringUtil;

public class RequestParam {

	private String pname ;
	private Object value ;
	
	private RequestParam(String pname, Object value) {
		this.pname = pname ;
		this.value = value ;
	}

	static RequestParam create(String pname, Object value) {
		return new RequestParam(pname, value);
	}

	
	public Object getValue(){
		if (value instanceof String && StringUtil.startsWith((String)value, "${") && StringUtil.endsWith((String)value, "}")) {
			return "----" ;
		} else {
			return value ;
		}
	}
	
	public String toString(){
		return "{name:" + pname + ", value:" + getValue() + "}" ;
	}

}

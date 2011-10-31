package net.ion.radon.param;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.ArrayUtils;

public class MyParameter {
	
	private JSONObject json ;
	private MyParameter(JSONObject json) {
		this.json = json ;
	}

	public static MyParameter create(Object param) {
		if (param instanceof JSONObject) {
			return new MyParameter((JSONObject)param) ;
		} else if (param instanceof String) {
			return create((String)param) ;
		} else if (param instanceof Map) {
			return create((Map<String, Object>)param) ;
		}
		return create(JSONObject.fromObject(param));
	}

	public final static MyParameter create(String parameter) {
		if (StringUtil.isBlank(parameter)) {
			return new MyParameter(new JSONObject()) ;
		}
		
		final JSONObject json = JSONObject.fromObject(parameter);
		// Map<String, Object> paramMap = new CaseInsensitiveHashMap<String, Object>() ;
		// recursive(null, paramMap, json) ;
		return new MyParameter(json) ;
	}

	public static MyParameter create(Map<String, Object> params) {
		StringBuilder sb = new StringBuilder() ;
		sb.append("{") ;
		
		for (Entry<String, Object> entry : params.entrySet()) {
			
			sb.append(entry.getKey()).append(":") ;
			sb.append(getValueExpression(entry.getValue())) ;
			sb.append(",") ;
		}
		if (params.entrySet().size() > 0) sb.setLength(sb.length() -1) ;
		sb.append("}") ;
		return create(sb.toString()) ;
	}

	private static Object getValueExpression(Object value) {
		if (value instanceof String) {
			String stringValue = ((String)value).trim();
			if ( stringValue.startsWith("{") && stringValue.endsWith("}")){
				return stringValue ;
			} else if ( stringValue.startsWith("[") && stringValue.endsWith("]")){
				return stringValue ;
			} else {
				return "\"" + stringValue + "\"" ;
			}
		}

		return value;
	}

	
	public boolean isContains(String path){
		return getParam(path) != null;
	}
	
	public boolean isArray(String key){
		return (json.get(key) instanceof JSONArray) || (json.get(key) instanceof JSONObject);
	}

	public Object getParam(String path) {
		MyParameterKey pkey = getParameterKey(path) ;
		
		return pkey.get(json) ;
	}


	public String getParamAsString(String path) {
		return StringUtil.toString(getParam(path)) ;
	}

	
	public Map<String, Object> getMap() {
		return getParameterKey("").getAsMap(json) ;
	}
	
	public Map<String, Object> getMap(String path) {
		return getParameterKey(path).getAsMap(json) ;
	}
	
	public MyParameter childParameter(String path) {
		return new MyParameter(getParameterKey(path).getAsJSON(json)) ;
	}

	private MyParameterKey getParameterKey(String path) {
		return MyParameterKey.create(path);
	}

	public Object[] getParams(String path) {
		Object obj = getParameterKey(path).get(json);
		if (obj == null) {
			return new Object[0] ;
		} else if (obj instanceof List){
			return ((List)obj).toArray(new Object[0]) ;
		} else if (obj instanceof Map){
			return ((Map)obj).values().toArray(new Object[0]) ;
		} else {
			return new Object[]{obj} ;
		}
	}

	public Object toBean(Class clz) {
		final Object bean = JSONObject.toBean(this.json, clz);
		return bean ;
	}

	public void addParam(String path, Object value) {
		accumulate(this.json, path, value) ;
	}

	private void accumulate(JSONObject that, String path, Object value) {
		String[] names = StringUtil.split(path, "./") ;
		String firstPath = names[0];
		if (names.length == 1){
			that.accumulate(firstPath, value) ;
		} else {
			if (that.containsKey(firstPath)){
				String subPath = StringUtil.join(ArrayUtils.subarray(names, 1, names.length), '.') ;
				accumulate(that.getJSONObject(firstPath), subPath, value) ;
			} else {
				JSONObject newChild = new JSONObject();
				that.accumulate(firstPath, newChild) ;
				String subPath = StringUtil.join(ArrayUtils.subarray(names, 1, names.length), '.') ;
				accumulate(that.getJSONObject(firstPath), subPath, value) ;
			}
		}
	}

	public int size() {
		return json.size();
	}
	
	public String toString(){
		return (json== null) ? null : json.toString() ;
	}

	
	public JSONObject getJSON(){
		return json ;
	}

}

package net.ion.radon.param;

import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.parse.gson.JsonElement;
import net.ion.framework.parse.gson.JsonNull;
import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.parse.gson.JsonUtil;
import net.ion.framework.parse.gson.NotFoundJsonElement;
import net.ion.framework.util.ArrayUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;

import org.apache.commons.lang.ArrayUtils;

import com.sun.corba.se.impl.io.ValueUtility;

public class MyParameter {
	
	private JsonObject json ;
	private MyParameter(JsonObject json) {
		this.json = json ;
	}

	public static MyParameter create(Object param) {
		if (param instanceof JsonObject) {
			return new MyParameter((JsonObject)param) ;
		} else if (param instanceof String) {
			return new MyParameter(JsonUtil.arrangeKey(JsonParser.fromString((String)param).getAsJsonObject())) ;
		} else if (param instanceof Map) {
			return new MyParameter(JsonParser.fromMap((Map)param)) ;
		} else {
			return new MyParameter(JsonParser.fromObject(param).getAsJsonObject());
		}
	}

	
	public boolean isContains(String path){
		return JsonUtil.hasElement(json, path);
	}
	
	public boolean isArray(String path){
		return JsonUtil.findElement(json, path).isJsonArray() ;
	}

	public Object getParam(String path) {
		return JsonUtil.findSimpleObject(json, path) ;
	}

	public String getParamAsString(String path) {
		return ObjectUtil.toString(getParam(path)) ;
	}

	
	public Map<String, Object> getMap() {
		return json.toMap();
	}
	
	public Map<String, Object> getMap(String path) {
		return JsonUtil.findElement(json, path).getAsJsonObject().toMap() ;
	}
	
	public MyParameter childParameter(String path) {
		JsonElement found = JsonUtil.findElement(json, path);
		if (found == null) return new MyParameter(new JsonObject()) ;
		return new MyParameter(found.getAsJsonObject()) ;
	}


	public Object[] getParams(String path) {
		JsonElement found = JsonUtil.findElement(json, path) ;
		if (found == null || found == JsonNull.INSTANCE) return new Object[0] ;
		if (found.isJsonArray()) return found.getAsJsonArray().toObjectArray() ;
		if (found.isJsonObject()) found.getAsJsonObject().toMap().values().toArray(new Object[0]) ;
		if (found.isJsonPrimitive()) return new Object[]{JsonUtil.toSimpleObject(found)} ;
		return new Object[0] ;
	}

	public <T> T toBean(Class<T> clz) {
		return json.getAsObject(clz) ;
	}

	public void addParam(String path, Object value) {
		accumulate(this.json, path, value) ;
	}

	private void accumulate(JsonObject that, String path, Object value) {
		String[] names = StringUtil.split(path, "./") ;
		String firstPath = names[0];
		if (names.length == 1){
			that.accumulate(firstPath, value) ;
		} else {
			if (that.has(firstPath)){
				String subPath = StringUtil.join(ArrayUtils.subarray(names, 1, names.length), '.') ;
				accumulate(that.asJsonObject(firstPath), subPath, value) ;
			} else {
				JsonObject newChild = new JsonObject();
				that.accumulate(firstPath, newChild) ;
				String subPath = StringUtil.join(ArrayUtils.subarray(names, 1, names.length), '.') ;
				accumulate(that.asJsonObject(firstPath), subPath, value) ;
			}
		}
	}

	public int size() {
		return json.childSize();
	}
	
	public String toString(){
		return json.toString() ;
	}

	public boolean has(String path) {
		return JsonUtil.hasElement(json, path) ;
	}

	public JsonObject getJson(){
		return json ;
	}

}

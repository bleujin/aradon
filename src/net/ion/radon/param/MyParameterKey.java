package net.ion.radon.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.util.CaseInsensitiveHashMap;
import net.ion.framework.util.StringUtil;

public class MyParameterKey {

	private String keyName;
	private MyParameterKey nextKey;
	private boolean isFirst = false;

	
	
	private MyParameterKey(String keyName) {
		this.keyName = keyName;
	}

	public MyParameterKey() {
		this.isFirst = true ;
	}

	final static MyParameterKey create(String path) {
		if (StringUtil.isBlank(path)) return new MyParameterKey() ;
		
		String[] keys = StringUtil.split(path, "./");
		List<MyParameterKey> keyStore = new ArrayList<MyParameterKey>();
		for (String key : keys) {
			keyStore.add(new MyParameterKey(key));
		}

		if (keyStore.size() <= 1)
			return keyStore.get(0);
		for (int i = 0; i < keyStore.size() - 1; i++) {
			keyStore.get(i).setNextKey(keyStore.get(i + 1));
		}

		MyParameterKey startKey = keyStore.get(0);
		startKey.isFirst = true;
		return startKey;
	}

	private void setNextKey(MyParameterKey nextKey) {
		this.nextKey = nextKey;
	}

	public String getName() {
		return keyName;
	}

	public boolean isRequireArray() {
		return getName().endsWith("]") && getName().indexOf("[") > -1;
	}

	public MyParameterKey getNext() {
		return nextKey;
	}

	public boolean isLast() {
		return nextKey == null;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public Object get(JsonObject json) {
		if (StringUtil.isBlank(keyName)) return json ;

		Object result = null ;
		if (json == null) {
			return null ;
		} else if (isRequireArray()) {
			String oname = StringUtil.substringBefore(getName(), "[") ;
			int index = Integer.parseInt(StringUtil.substringBetween(getName(), "[", "]")) ;
			result = json.asJsonArray(oname).get(index) ;
		} else {
			result = json.get(getName()) ;
			
			if (result == null) {
				return json.get(flatPath()) ;	
			}
		}
		
		if (isLast()) {
			return result;
		} else {
			return getNext().get((JsonObject)result);
		}
	}

	public JsonObject getAsJSON(JsonObject json) {
		return (JsonObject) get(json);
	}

	public Map<String, Object> getAsMap(JsonObject json) {
		JsonObject jobj = getAsJSON(json);
		Map<String, ? extends Object> map = jobj.toMap();

		Map<String, Object> result = new CaseInsensitiveHashMap<Object>();
		for (Entry<String, ? extends Object> entry : map.entrySet()) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	private String flatPath(){
		return toString() ;
	}
	
	public String toString() {

		MyParameterKey current = this;
		List<String> keyLink = new ArrayList<String>();
		do {
			keyLink.add(current.keyName);
			current = current.nextKey;
		} while (current != null);

		return StringUtil.join(keyLink.toArray(new String[0]), '.');
	}

}

package net.ion.nradon.stub;

import java.util.Map;
import java.util.Set;

import net.ion.framework.util.MapUtil;
import net.ion.framework.util.StringUtil;
import net.ion.nradon.DataHolder;

public class StubDataHolder implements DataHolder {

	private Map<String, Object> data = MapUtil.newMap() ;

	public Map<String, Object> data() {
		return data;
	}

	public Object data(String key) {
		return data.get(key);
	}

	public DataHolder data(String key, Object value) {
		data.put(key, value);
		return this;
	}

	public Set<String> dataKeys() {
		return data.keySet();
	}
	
	public String getString(String key){
		return StringUtil.toString(data(key)) ;
	}
}

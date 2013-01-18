package net.ion.nradon.handler.aradon;

import java.util.Map;

import net.ion.framework.parse.gson.JsonObject;
import net.ion.framework.parse.gson.JsonParser;
import net.ion.framework.parse.gson.JsonUtil;
import net.ion.framework.util.MapUtil;
import net.ion.framework.util.ObjectUtil;
import net.ion.framework.util.StringUtil;

public class JSONMessagePacket {

	public static final JSONMessagePacket EMPTY = JSONMessagePacket.load("{}");
	public static final JSONMessagePacket PING = JSONMessagePacket.load("{head:{command:'ping'},body:{}}");

	private final JsonObject root;
	private final String currentPath;
	private final JsonObject current;

	private Map<String, JSONMessagePacket> childMap ;
	
	private JSONMessagePacket(JsonObject root) {
		this.root = root;
		this.currentPath = "";
		this.current = root;
		this.childMap = MapUtil.newCaseInsensitiveMap() ;
		childMap.put("", this) ;
	}

	private JSONMessagePacket(JsonObject root, String currentPath, JsonObject current, Map<String, JSONMessagePacket> childMap) {
		this.root = root;
		this.currentPath = currentPath;
		this.current = current;
		this.childMap = childMap ;
	}

	public final static JSONMessagePacket load(String message) {
		return new JSONMessagePacket(JsonParser.fromString(message).getAsJsonObject());
	}

	public static JSONMessagePacket create() {
		return new JSONMessagePacket(new JsonObject());
	}

	public static JSONMessagePacket load(JsonObject jsonObject) {
		return new JSONMessagePacket(jsonObject);
	}

	public JSONMessagePacket inner(String _inname) {
		String inname = _inname.toLowerCase() ;
		if (!current.has(inname)) {
			current.add(inname, new JsonObject());
		}
		
		String newPath = (isRoot() ? "" : currentPath + ".") + inname;
		if (! childMap.containsKey(newPath)){
			JsonObject newCurrent = current.asJsonObject(inname);
			childMap.put(newPath, new JSONMessagePacket(root, newPath, newCurrent, this.childMap)) ;
		}  

		return childMap.get(newPath) ;
	}

	public Object get(String _path) {
		return get(this.current, _path) ;
	}
	
	private Object get(JsonObject json, String path) {
		return JsonUtil.findSimpleObject(json, path) ;
	}

	public String getString(String path) {
		return ObjectUtil.toString(get(this.current, path));
	}

	public String getString(String path, String dftValue) {
		String result = getString(path);
		if (StringUtil.isBlank(result))
			return dftValue;
		return result;
	}

	public JSONMessagePacket toParent() {
		String parentPath = "";
		if (StringUtil.contains(currentPath, ".")) {
			parentPath = StringUtil.substringBeforeLast(currentPath, ".");
		}
		
		return childMap.get(parentPath) ;
	}

	public JSONMessagePacket toRoot() {
		return childMap.get("");
	}

	public String getFullString() {
		return root.toString();
	}

	public boolean has(String path) {
		return get(path) != null;
	}

	public JSONMessagePacket put(String key, Object value) {
		current.put(key.toLowerCase(), value);

		return this;
	}

	public JSONMessagePacket array(String key, Object[] values) {
		return put(key, values);
	}


	public Map<String, ? extends Object> toMap() {
		return current.toMap();
	}

	private boolean isRoot() {
		return StringUtil.isBlank(this.currentPath);
	}

	public String toString() {
		return getFullString();
	}

}
